# GitHub Actions workflows

## `sync-main-release-pr.yml` — Synchronize Main-Release Branch PRs

Mirrors connector changes from a PR targeting a `release-*` branch into a
dedicated, automatically maintained PR against `main`, on a per-connector basis
driven by each connector's `metadata.json` `ConnectorVersion`.

The goal is to keep `main` in sync with whatever has been bumped on a release
branch without ever requiring a manual cherry-pick, while ensuring that a
connector that has **not** been bumped is never moved sideways.

---

### Trigger

```yaml
on:
  pull_request:
    branches:
      - 'release-*'
    types: [opened, synchronize, reopened]
```

The workflow runs when a PR whose **base branch** matches `release-*` is:

- **opened** — first push of a release PR.
- **synchronize** — any new commit pushed to the release PR's head.
- **reopened** — a previously closed release PR is reopened.

It does **not** run on:

- PRs targeting `main` directly.
- PRs from forks (`GITHUB_TOKEN` cannot push from forks); the job is guarded by
  `if: github.event.pull_request.head.repo.full_name == github.repository`.

#### Concurrency

```yaml
concurrency:
  group: sync-main-release-pr-${{ github.event.pull_request.number }}
  cancel-in-progress: true
```

Runs are serialized **per release PR**. If two commits land in quick succession,
the older run is cancelled so it cannot force-push a stale snapshot over a
newer one.

#### Permissions

```yaml
permissions:
  contents: write       # push the sync branch
  pull-requests: write  # create / edit the main PR and comment on the release PR
```

---

### What it does (high level)

For each commit on a release PR:

1. Diff the release PR head against its base (`release-*`) to get the list of
   changed files.
2. Walk each changed file upward to its **nearest ancestor `metadata.json`** —
   that ancestor directory is treated as a "connector".
3. For each affected connector, compare the PR's `metadata.json`
   `ConnectorVersion` against the version on `main`. Only connectors whose
   PR version is **strictly greater** than the `main` version are eligible.
4. Take a snapshot of each eligible connector directory from the PR head.
5. Reset (or create) a sync branch named `auto-sync/release-pr-<N>` off
   `origin/main` and apply the snapshots wholesale, replacing each eligible
   connector directory in place.
6. Open or update a PR from that sync branch into `main`. On first creation,
   post an informational comment on the source release PR linking to the new
   main PR (only once, guarded by an HTML-comment marker).

The same sync branch and main PR are reused for the life of the release PR
(one-to-one mapping by release PR number).

---

### Connector model

A "connector" is any directory containing a `metadata.json` file with a
`ConnectorVersion` field. Connectors can be nested (a connector directory may
itself contain sub-connectors that also have their own `metadata.json`).

`ConnectorVersion` **must** match the regex `^(20[0-9]{2})\.(0[1-9]|1[0-2])\.(0|[1-9][0-9]*)$`:

- `YYYY` — 4-digit year, ≥ 2000.
- `MM` — 2-digit, zero-padded month, `01`–`12`.
- `BUILD` — non-negative integer build counter, no leading zeros except the
  literal `0`.

Examples: `2026.01.0`, `2026.05.3`, `2026.12.42`.

Versions are compared **numerically** field-by-field (year, then month, then
build). Lexical comparison is never used, so `2026.05.10 > 2026.05.9` as
expected.

---

### Eligibility rules (per connector)

For each connector resolved from changed files in the release PR:

| Situation on PR side                                                | Situation on `main`                                  | Result                                                          |
| ------------------------------------------------------------------- | ---------------------------------------------------- | --------------------------------------------------------------- |
| `metadata.json` missing in PR                                       | n/a                                                  | **Skip** (logged, not an error).                                |
| `ConnectorVersion` missing in PR `metadata.json`                    | n/a                                                  | **Workflow fails** with a file-level error annotation.          |
| `ConnectorVersion` does not match `YYYY.MM.BUILD` regex             | n/a                                                  | **Workflow fails** with a file-level error annotation.          |
| Valid PR version                                                    | `metadata.json` does not exist on `main`             | **Eligible** — connector is new on `main`.                      |
| Valid PR version                                                    | `ConnectorVersion` missing on `main`                 | **Eligible** — PR is treated as authoritative.                  |
| Valid PR version                                                    | `ConnectorVersion` on `main` does not match regex    | **Eligible**, with a warning — PR overwrites malformed `main`.  |
| Valid PR version > valid `main` version                             | —                                                    | **Eligible**.                                                   |
| Valid PR version ≤ valid `main` version                             | —                                                    | **Skip**.                                                       |

If any connector triggers a hard error (missing or malformed PR
`ConnectorVersion`), the workflow fails **after evaluating every connector**,
so all problems are reported in a single run.

---

### Snapshot & apply behavior

Eligible connectors are processed in alphabetical order (the output of
`sort -u`) so that **parent connectors are always staged and applied before
their children**.

Each eligible connector directory is:

1. Copied verbatim from the PR head to `/tmp/connector-snapshot/<connector>/`.
2. On the sync branch (which is freshly reset to `origin/main`), the existing
   tracked directory is removed (`git rm -rf`, falling back to `rm -rf`), then
   the snapshot is copied back in.
3. `git add -A <connector>` stages both additions and deletions so files
   removed in the PR also disappear on the sync branch.

Both copy steps use **`cp -aT`** (with an explicit `rm -rf` of the snapshot
destination beforehand) so the source is always written **as** the destination,
never **inside** it.

> #### Why `cp -aT` matters — the nested-directory bug
>
> Plain `cp -a SRC DEST` has GNU semantics: if `DEST` already exists as a
> directory, `SRC` is copied *into* `DEST` (creating `DEST/<basename SRC>`)
> instead of overwriting it.
>
> When a parent connector and one of its child connectors are both eligible
> in the same run (for example
> `Microsoft Entra/Connector Files` and
> `Microsoft Entra/Connector Files/layer_200`), the parent is processed first
> and recursively copies the child as part of its own tree. The subsequent
> `cp -a` for the child then sees an already-existing destination and nests
> the child inside itself, producing paths like
> `Microsoft Entra/Connector Files/layer_200/layer_200/...`. That nesting
> propagates from the staging area into the sync branch and onto the main PR.
>
> Using `cp -aT` (after an explicit `rm -rf` of the destination during
> staging) forces the destination to be treated as the target itself, so the
> child overwrites the parent's copy of itself cleanly without nesting.

---

### Sync branch lifecycle

Branch name: `auto-sync/release-pr-<RELEASE_PR_NUMBER>`.

The sync branch is **always rebuilt off `origin/main`** at the start of each
run (`git checkout -B <sync-branch> origin/main`). This guarantees the diff
shown in the main PR reflects only the current state of the release PR —
nothing accumulates across runs.

The branch is **force-pushed** after each successful run, for the same reason.

#### Auto-close cases

If a later commit on the release PR removes the version bump (rolls back, or
deletes the change), the main PR should not stay open with stale content.
Two safety nets handle this:

- **No eligible connectors at all** (`eligible.count == '0'`): the workflow
  resets the existing sync branch (if any) back to `origin/main`. The open
  main PR becomes empty and GitHub auto-closes it.
- **Eligible connectors exist, but the snapshot produces no diff vs `main`**
  (e.g. the PR's bumped contents are byte-identical to what's already on
  `main`): same handling — the sync branch is reset to `origin/main` and the
  open PR auto-closes.

In both cases nothing further is committed and no main PR is created or
updated.

---

### Main PR lifecycle

- **Title:** `Auto-sync: release PR #<N> → main`
- **Body:** Lists every connector directory that was replaced wholesale,
  records the source release PR number, the source base ref, and the source
  head SHA.
- **Lookup:** The workflow finds an existing open PR by matching
  `--head <sync-branch> --base main`.
- **Update:** If an existing PR is found, its title and body are refreshed
  via `gh pr edit`. The branch's new commit (force-pushed earlier) becomes
  the new PR head automatically.
- **Create:** Otherwise, a new PR is opened via `gh pr create`.

#### Notification on the release PR

On the **first** creation of the main PR for a given release PR, the workflow
posts an `[!IMPORTANT]` comment on the release PR linking to the new main PR.
The comment is tagged with an HTML-comment marker
(`<!-- auto-sync-main-pr-link -->`); the workflow refuses to post again if a
comment containing that marker already exists, so reopening or pushing more
commits will not spam the release PR.

---

### Edge cases & guarantees summarized

- **PR from a fork** → workflow no-ops (cannot push using `GH_TOKEN`).
- **No changed files** → no connectors detected; if a previous sync branch
  exists, it's reset to `main` to auto-close the main PR.
- **Changed file not under any connector** (no ancestor `metadata.json`
  found) → silently ignored.
- **Connector has no `metadata.json` in the PR head** (e.g. deleted) →
  skipped.
- **Connector `metadata.json` missing or has malformed `ConnectorVersion`** →
  hard error, workflow fails (but only after evaluating every connector).
- **Connector version equal to `main`** → skipped; the connector is **not**
  touched on `main`.
- **Connector new on `main`** → eligible (full directory is added on the
  sync branch).
- **Connector with malformed version on `main`** → eligible with a warning;
  the PR's contents overwrite `main`.
- **Multiple commits in rapid succession** → older runs are cancelled by the
  concurrency group, preventing stale force-pushes.
- **Parent + child connectors both eligible** → handled correctly by
  `cp -aT`; no nested directories are produced.
- **Version rolled back / change reverted on a later commit** → sync branch
  is reset to `main` so the open main PR auto-closes.
- **Sync branch already exists from a previous run** → always rebuilt off
  `origin/main` and force-pushed; no drift between runs.
- **Release PR comment** → posted only once per release PR, guarded by an
  HTML-comment marker.

---

### Required setup

- A branch named `main` (or update the `MAIN_BRANCH` env var).
- Release branches named `release-*`.
- Every connector directory contains a `metadata.json` with a
  `ConnectorVersion` field formatted as `YYYY.MM.BUILD`.
- A repository or organization secret named `GH_TOKEN` is available to the
  workflow with `contents: write` and `pull-requests: write` permissions on
  this repository (a fine-grained PAT or a GitHub App installation token
  works). The default `GITHUB_TOKEN` is not used.
- The runner is `ubuntu-latest`; `jq` is installed on demand if missing, and
  `cp -aT` requires GNU coreutils (present by default on Ubuntu runners).
