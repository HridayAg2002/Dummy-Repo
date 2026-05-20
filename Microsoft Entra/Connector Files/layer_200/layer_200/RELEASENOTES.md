# Microsoft Entra ID Connector and Microsoft Entra Identity Governance Release Notes
Check out the latest release information on this page


**Version:** 2026.05.1
**Release Date:** May 4, 2026

---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | **Entra Id Governance**: Added wait time after creation of new Application before further API calls to fetch application-based resources | Prevents race condition errors when creating new applications | Publish a business role with a new application and verify all steps complete successfully |
---

**Version:** 2026.03.2
**Release Date:** March 21, 2026

---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Replaced PTD SQL queries with Pathlock Custom Reports API | Improves integration architecture and maintainability | Verify integration operations function correctly with the new Custom Reports API |
---

**Version:** 2026.03.1
**Release Date:** March 17, 2026

---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | UserRolesForSingleUser now handles users with no groups assigned | Prevents errors when refreshing users who have no group memberships | Test single user refresh for a user with no group assignments |
---

**Version:** 2026.02.2
**Release Date:** February 23, 2026

---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Changed Graph API beta version to v1.0 for the createAccessPackage operation | Improves stability by using the stable Graph API endpoint | Verify createAccessPackage operation functions correctly |
---

**Version:** 2026.01.1
**Release Date:** January 29, 2026

---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Fixed assignLicenseWithDisabledPlans operation failure | Operation was failing on the connector; now works correctly | Run the assignLicenseWithDisabledPlans operation and verify it completes successfully |

---

**Version:** 2026.01.1
**Release Date:** January 13, 2026

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Get ChangedUsers functionality | Enables detection of recently changed users for incremental sync |
---

**Version:** 2025.12.2
**Release Date:** December 11, 2025

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Create Guest User operation | Enables provisioning of guest user accounts in Microsoft Entra ID |
---

**Version:** 2025.12.1
**Release Date:** December 12, 2025

---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Resolved bug in All User Roles List operation failing if there is no access package in Entra IG (BUG #49475) | Operation no longer fails when no access packages exist | Run UserRoles sync on a tenant with no access packages |

---

**Version:** 2025.12.1
**Release Date:** December 09, 2025

---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Role is not visible in Report Designer | Roles now appear correctly in Report Designer | Verify roles are visible in Report Designer after sync |

---

**Version:** 2025.11.2
**Release Date:** November 27, 2025

---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Resolved bug in custom operation createAccessPackage used to publish Pathlock business role to Entra IG (BUG #48830) | Access package creation now works correctly | Publish a business role to Entra IG and verify the access package is created |

---

**Version:** 2025.11.1
**Release Date:** November 24, 2025

---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Resolved bug in custom operation assignLicenseWithDisabledPlans | License assignment with disabled plans now works correctly | Run the assignLicenseWithDisabledPlans operation and verify completion |

---

**Version:** 2025.09.1
**Release Date:** October 03, 2025

---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Resolved issue in publishing Business Role having technical roles of different system with same technical role name (BUG #43870) | Business roles with duplicate technical role names across systems now publish correctly | Publish a business role with technical roles from different systems sharing the same name |

---

**Version:** 2025.08.1
**Release Date:** August 15, 2025

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Support for Application Access Management added: List Service Principals operation reads Application, Managed Identities, and Legacy type Service Principals; custom operations to read and remove Service Principal permissions (Application and Delegated) | Enables governance of application-level permissions and service principal access |
---

**Version:** 2025.07.2
**Release Date:** July 30, 2025

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Support for Privileged Access Management: read, assign, and de-assign Directory Roles (including privileged roles) for User type objects | Enables governance of Entra ID directory role assignments |
---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | UserRolesForSingleUser now uses User ID to handle special characters in Username | Resolves Rebuild Users Catalog sync job failures for users with special characters | Run single user refresh for a user with special characters in username |
---

**Version:** 2025.07.1
**Release Date:** July 01, 2025

---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Special character handling in usernames and group names improved to cover all possible cases | All special characters are now handled correctly in both group names and usernames | Test operations with usernames and group names containing special characters |
---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | RoleTypes were showing invalid data in Technical Roles | Fixed retrieval of correct group types and mapping to CustomRoleTypes field | Verify role types display correctly after role sync |

---

**Version:** 2025.06.2
**Release Date:** June 27, 2025

---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Resolved issues with repeating and unexpected crashing of Sync Users job caused by nested groups | Fixed returned object type in APIs to microsoft.graph.user to handle nested group members | Run Sync Users job on tenants with nested group memberships |
| 2 | Create User was failing due to URL encoding issues | Removed encoding formula to fix the operation | Run CreateUser operation and verify success |

---

**Version:** 2025.06.1
**Release Date:** June 09, 2025

---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Access Package created without resource roles in MS Entra ID Governance after publishing a Business Role with technical roles never used before (BUG #34577) | Access packages now correctly include resource roles | Publish a business role with new technical roles and verify resource roles are attached |
| 2 | Role sync job failure due to Catalog name longer than 20 characters (BUG #30971) | Catalog names longer than 20 characters no longer cause sync failures | Create a catalog with a name longer than 20 characters and run role sync |

---

**Version:** 2025.05.1
**Release Date:** May 15, 2025

---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Support added for usernames with special characters (apostrophe and hashtag) | Users with special characters in usernames can now be managed | Test operations with usernames containing apostrophe or hashtag characters |
---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Sync jobs for UserRoles failing due to expiration of auth token | Token regeneration configured for UserRoles operation | Run a full UserRoles sync and verify it completes without token expiration errors |

---

**Version:** 2025.04.1
**Release Date:** April 03, 2025

---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Removed Add Distribution Group Member and Remove Distribution Group Member operations (moved to Microsoft Exchange Online connector) | Consolidates distribution group management into the Exchange Online connector | Verify distribution group operations work in the Exchange Online connector |
---

**Version:** 2025.02.1
**Release Date:** February 21, 2025

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Implemented custom operation Add Distribution Group Member | Allows adding a user to a Distribution Group/List |
| 2 | Implemented custom operation Remove Distribution Group Member | Allows removing a user from a Distribution Group/List |
---

**Version:** 2025.01.2
**Release Date:** January 13, 2025

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Implemented custom operation Get License Details for reading all license and plan IDs | Enables easier lookup when assigning or removing licenses |
---
## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Errors resolved with locking and unlocking users | Lock and unlock operations now complete successfully | Run LockUser and UnLockUser operations |

---

**Version:** 2025.01.1
**Release Date:** January 07, 2025

---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Added role type for Entra Groups (Security, Microsoft 365, Distribution); Pathlock will not attempt to assign or remove Microsoft 365 and Distribution group types as this is not supported by the MS Graph API | Prevents failed provisioning attempts for unsupported group types | Verify that assign/remove operations correctly skip Microsoft 365 and Distribution group types |
---

**Version:** 2024.12.1
**Release Date:** December 05, 2024

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Implemented custom operation Get Group Owners | Enables administrators to retrieve and manage group ownership for enhanced governance and efficiency |
---

**Version:** 2024.11.1
**Release Date:** November 20, 2024

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Implementation of Microsoft Entra Identity Governance integration | Improves access control and policy enforcement |
---
## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Conversion of the Pathlock Entra ID connector to the Pathlock Open Connector Framework | Improves scalability and future compatibility | Verify all operations function correctly after framework migration |
---