# Pathlock Microsoft Dynamics 365 CRM Connector

## Overview

This connector integrates with Microsoft Dynamics 365 Customer Engagement applications (Sales, Customer Service, Field Service, Marketing) built on the Microsoft Dataverse / Power Platform. It reads and manages users, security roles, teams, and field security profiles via the Dataverse Web API and Microsoft Graph API.

## Configuration

| Parameter             | Description                                                                                                         |
| --------------------- | ------------------------------------------------------------------------------------------------------------------- |
| **System Type**       | `Http Connector`                                                                                                    |
| **DataSource**        | Base URL of your Dynamics 365 instance (e.g. `https://xxx.crm4.dynamics.com`). Do **not** include a trailing slash. |
| **Oauth Client ID**   | Application (client) ID from the Azure AD registration.                                                             |
| **Oauth Secret**      | Client secret generated for the Azure AD app.                                                                       |
| **Azure Tenant ID**   | Directory (tenant) ID of the environment that hosts Dynamics 365.                                                   |
| **Graph API version** | Keep `v1.0` unless Microsoft directs otherwise.                                                                     |

---

## Security Model Mapping

- **Users:** System Users
- **Roles:**
  - Security Roles
  - Teams (see Team Types below)
  - Field Security Profiles
- **Child Roles:**
  - Teams (Owner & Access) grouped per Business Unit
    - Security Roles
    - Field Security Profiles
- **Activities:**
  - Privileges (from Security Roles)
  - Field Permissions (from Field Security Profiles)

### Capability Matrix

| Level   | Description                                              | Read / SOD         | Provision          | Usage Log |
| ------- | -------------------------------------------------------- | ------------------ | ------------------ | --------- |
| Level 1 | Security Roles & Field Security Profiles (direct assign) | :white_check_mark: | :white_check_mark: |           |
| Level 2 | Teams (group-aware membership)                           | :white_check_mark: | :white_check_mark: |           |

### Team Types

| Type           | Description                                                                                  | Provisionable      |
| -------------- | -------------------------------------------------------------------------------------------- | ------------------ |
| Owner Teams (user-created)    | Carry privileges, can own records. Appear in role/child-role outputs.                        | :white_check_mark: |
| Default Business Unit Owner Teams | System-managed Owner Team that exists for every Business Unit and shares the Business Unit's name (e.g. `Team:Ivory Coast__Ivory Coast`). Membership is derived from the user's Business Unit assignment. |                    |
| Access Teams   | Provide record-level sharing; captured for completeness even though they do not own records. |                    |
| Office Teams   | Collaboration-focused; inherit permissions through Business Units and linked roles.          |                    |
| Security Teams | Used for scoped security scenarios; typically mirror high-privilege role assignments.        |                    |

> **Note:** Attach/Remove Role for Teams uses the Dataverse `AddMembersTeam` / `RemoveMembersTeam` actions, which support only **user-created Owner Teams**. Office and Security teams are Azure AD group-backed — their membership must be managed through Microsoft Entra ID. The system-managed default Business Unit teams cannot be modified via `AddMembersTeam` / `RemoveMembersTeam` (or via plug-ins / workflows) per Microsoft's documented behavior; their membership is changed only by moving the user to a different Business Unit using the `UpdateUserBusinessUnit` custom operation.

### Business Units

Business Units are organizational units in D365 CRM that scope user access and team ownership. They are not read as a separate entity — instead, they are embedded in the role/team naming convention (`{BusinessUnit}__{RoleName}`) and exposed as a user attribute (mapped to the Department field). Each user belongs to exactly one Business Unit, and roles/teams are unique within their respective Business Unit.

### Naming Conventions

> - Roles and Teams are represented as `{BusinessUnit}__{RoleName/TeamName}` in the connector.
> - Activities under Field Security Profiles are displayed as `{EntityName}-{AttributeName}`.

### Assumptions

- **One active account per user** – Each user has exactly one active account with a single Domain Name. Users with multiple occurrences under different Domain Names are considered invalid and may produce unexpected results.
- **Unique Field Security Profile names** – All Field Security Profiles must have unique names within the D365 CRM instance.

---

## Supported Operations

| Operation                    | Supported          |
| ---------------------------- | ------------------ |
| Read the list of users       | :white_check_mark: |
| Read the list of roles       | :white_check_mark: |
| Read the assignment of roles | :white_check_mark: |
| Read role content            | :white_check_mark: |
| Read activities list         | :white_check_mark: |
| Attach role to user          | :white_check_mark: |
| Remove role from user        | :white_check_mark: |
| Lock user                    | :white_check_mark: |
| Unlock user                  | :white_check_mark: |
| Set User Properties          | :white_check_mark: |
| Set user password            | :white_check_mark: |
| Create user accounts         |                    |
| Read detailed change logs    |                    |
| Read usage data              |                    |

### Custom Operations

| Operation              | Supported          |
| ---------------------- | ------------------ |
| UpdateUserBusinessUnit | :white_check_mark: |

---

## Authentication and Access Setup

### 1. Register a client application

1. Sign in to https://portal.azure.com/ or https://entra.microsoft.com/ with an administrator account.
2. Browse to **Azure Active Directory > App registrations** and select **New registration**.
3. Enter a display name, choose the supported account types that match your tenancy, and keep the Redirect URI empty or set any valid HTTPS URL (it is not used by the connector).
4. Select **Register**. Copy the **Application (client) ID** and **Directory (tenant) ID** — these become the connector system parameters.
5. Navigate to **API permissions**:
   - Add **Dynamics CRM** > **user_impersonation** (delegated).
   - Add the following **Application** permissions:
     - `User.ReadWrite.All`
     - `User-PasswordProfile.ReadWrite.All`
6. Grant **Admin Consent** to all added permissions.

### 2. Create a client secret

1. Open **Certificates & secrets > Client secrets**.
2. Select **New client secret**, provide a description, and choose an expiry window.
3. Click **Add** and copy the secret value immediately — this becomes the **Oauth Secret** system parameter.

### 3. Create an application user for Dynamics CRM

1. Go to https://admin.powerplatform.microsoft.com/environments and sign in as an administrator.
2. Select the environment, open **Settings**, expand **Users + permissions**, and choose **Application users**.
3. Click **New app user**, then **Add an app**, search for the Azure AD app created earlier, and select **Add**.

> Reference: https://learn.microsoft.com/en-us/power-platform/admin/manage-application-users#view-or-edit-the-details-of-an-application-user

### 4. Provide access to the application user

Create a Security Role with at least the following permissions and assign it to the application user:

**Business Management:**

| Entity                 | Permissions                                    |
| ---------------------- | ---------------------------------------------- |
| Business Unit          | Read                                           |
| Mailbox                | Read, Write                                    |
| Field Security Profile | Create, Read, Write, Delete, Append, Append To |
| Security Role          | Full access                                    |
| Team                   | Full access                                    |
| User                   | Full access                                    |
| User Settings          | Full access                                    |

**Customization:**

| Entity                      | Permissions |
| --------------------------- | ----------- |
| Solution                    | Read        |
| System Application Metadata | Read        |

Additionally, create a **Field Security Profile** with the following field permissions and assign it to the application user:

| Field                     | Entity                          | Permissions          |
| ------------------------- | ------------------------------- | -------------------- |
| `msdyn_securemsappsecret` | Bot Channel Registration Secret | Read, Update, Create |
| `msdyn_clientsecret`      | Integrated Search Provider      | Read, Update, Create |

---

## Connection Test

| Test Operation          | Description                                        | Critical           |
| ----------------------- | -------------------------------------------------- | ------------------ |
| Test_Verify_Credentials | Verifies authentication credentials                | :white_check_mark: |
| Test_Read_Users_List    | Fetches a few users to validate the operation      | :white_check_mark: |
| Test_Read_Role_List     | Fetches a few roles to validate the operation      | :white_check_mark: |
| Test_Read_User_Roles    | Fetches user roles to validate the operation       | :white_check_mark: |
| Test_Read_Activity_List | Fetches a few activities to validate the operation | :white_check_mark: |
| Test_Update_User        | Tests the Set User Properties operation            | :white_check_mark: |

If all **critical** tests pass, the connector is considered correctly configured and working.

---

## Operation Details

> **Business Unit constraint:** For **Security Roles**, Dataverse requires the user and the role to be in the same Business Unit. If they differ, the API will reject the assignment. This constraint does **not** apply to Teams or Field Security Profiles.

### Attach Role

Assigns a role to a user. Supports all three role types:

- **Security Roles** — associated via `systemuserroles_association`
- **Teams** — added via `AddMembersTeam` (user-created Owner Teams only; system-managed default Business Unit teams are not supported)
- **Field Security Profiles** — associated via `systemuserprofiles_association`

The `rolename` parameter must use the exact name as returned by the Read Roles List operation.

| Parameter  | Required | Description                    |
| ---------- | -------- | ------------------------------ |
| `username` | Yes      | The user's Domain Name         |
| `rolename` | Yes      | Role name from Read Roles List |

### Remove Role

Removes a role from a user. Supports all three role types:

- **Security Roles** — disassociated via `systemuserroles_association`
- **Teams** — removed via `RemoveMembersTeam` (user-created Owner Teams only; system-managed default Business Unit teams are not supported)
- **Field Security Profiles** — disassociated via `systemuserprofiles_association`

The `rolename` parameter must use the exact name as returned by the Read Roles List operation.

| Parameter  | Required | Description                    |
| ---------- | -------- | ------------------------------ |
| `username` | Yes      | The user's Domain Name         |
| `rolename` | Yes      | Role name from Read Roles List |

### Set User Password

Sets a new password for the user and forces a password change on next sign-in.

| Parameter  | Required | Description            |
| ---------- | -------- | ---------------------- |
| `username` | Yes      | The user's Domain Name |
| `password` | Yes      | The new password       |

### Set User Properties

Updates user profile properties.

| Parameter     | Required | Description            |
| ------------- | -------- | ---------------------- |
| `username`    | Yes      | The user's Domain Name |
| `firstname`   | No       | First name             |
| `lastname`    | No       | Last name              |
| `email`       | No       | Email address          |
| `city`        | No       | City                   |
| `country`     | No       | Country                |

### Lock User

Disables the user account in Dynamics 365 CRM (sets status to Disabled).

| Parameter  | Required | Description            |
| ---------- | -------- | ---------------------- |
| `username` | Yes      | The user's Domain Name |

### Unlock User

Enables the user account in Dynamics 365 CRM (sets status to Enabled).

| Parameter  | Required | Description            |
| ---------- | -------- | ---------------------- |
| `username` | Yes      | The user's Domain Name |

### UpdateUserBusinessUnit (Custom)

Moves a user to a different Business Unit.

| Parameter          | Required | Description               |
| ------------------ | -------- | ------------------------- |
| `UserName`         | Yes      | The user's Domain Name    |
| `BusinessUnitName` | Yes      | Target Business Unit name |

---

## Field Mapping

### Users

**API Endpoint:** `/api/data/v9.1/systemusers`

| Pathlock Cloud Field | API Field              | Notes                                 |
| -------------------- | ---------------------- | ------------------------------------- |
| ApiIdentifier        | `systemuserid`         |                                       |
| UserName             | `domainname`           | Falls back to `systemuserid` if empty |
| EMail                | `internalemailaddress` |                                       |
| FullName             | `fullname`             | First + Last name combined            |
| LockStatus           | `isdisabled`           | `0` = Active, `64` = Disabled         |
| Department           | `businessunitid_name`  | User's Business Unit                  |
| EmployeeId           | `employeeid`           |                                       |
| Country              | `address1_country`     |                                       |
| City                 | `address1_city`        |                                       |

### Roles (Security Roles)

**API Endpoint:** `/api/data/v9.1/roles`

| Pathlock Cloud Field | API Field                  | Notes                      |
| -------------------- | -------------------------- | -------------------------- |
| RoleName             | `name`                     | Role name                  |
| RoleId               | `roleid`                   | Unique role ID             |
| RoleAttribute2       | `businessunitid_name`      | Business Unit of the role  |
| RoleAttribute1       | `solution_role_uniquename` | Solution-level unique name |
