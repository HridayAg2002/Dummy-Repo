# Microsoft Entra ID and Microsoft Entra Identity Governance Connector Release Notes

  Check out the latest release information on this page

  

  ## Version 2026.05.12
  **Version:** 2026.05.12  
  **Release Date:** May 12, 2026

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | UserRolesForSingleUser (Privilege Access) now pre-creates the `DemoUserRolesTable` SQLite table before the SELECT, guarded by `CREATE TABLE IF NOT EXISTS` inside a CDATA block | Prevents `no such table: DemoUserRolesTable` SQLite error during single user refresh when the user has no directory role assignments | Run single user refresh for an Entra user with zero directory/privileged roles assigned and verify the operation completes successfully with an empty role list |

  ---

  ## Version 2026.01.29
  **Version:** 2026.01.29  
  **Release Date:** January 29, 2026

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | assignLicenseWithDisabledPlans operation failing on connector | — | — |

  ---
  
  ## Version 2026.01.13
  **Version:** 2026.01.13  
  **Release Date:** January 13, 2026

  ### New Features
  | Description | Benefits |
  | --- | --- |
  | Get ChangedUsers functionality | — |

  ---

  ## Version 2025.12.2
  **Version:** 2025.12.2  
  **Release Date:** December 11, 2025

  ### New Features
  | Description | Benefits |
  | --- | --- |
  | Create Guest User | — |


  ---

  ## Version 2025.12.1 (December 12, 2025)
  **Version:** 2025.12.1  
  **Release Date:** December 12, 2025

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | All User Roles List operation failing when there is no access package in Entra IG | — | — |

  ---

  ## Version 2025.12.1 (December 09, 2025)
  **Version:** 2025.12.1  
  **Release Date:** December 9, 2025

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Role not visible in Report Designer | — | — |

  ---

  ## Version 2025.11.2
  **Version:** 2025.11.2  
  **Release Date:** November 27, 2025

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | BUG #48830: Custom operation createAccessPackage failing to publish Pathlock business role to Entra IG | — | — |

  ---

  ## Version 2025.11.1
  **Version:** 2025.11.1  
  **Release Date:** November 24, 2025

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Custom operation assignLicenseWithDisabledPlans resolved | — | — |

  ---

  ## Version 2025.09.1
  **Version:** 2025.09.1  
  **Release Date:** October 3, 2025

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Publishing Business role with technical roles from different systems using the same technical role name | — | — |
  | BUG #43870 (Pathlock internal) | — | — |

  ---

  ## Version 2025.08.1
  **Version:** 2025.08.1  
  **Release Date:** August 15, 2025

  ### New Features
  | Description | Benefits |
  | --- | --- |
  | Support for Application Access Management added (List Service Principals operation and custom operations to read/remove Service Principal permissions) | — |

  ---

  ## Version 2025.07.2
  **Version:** 2025.07.2  
  **Release Date:** July 30, 2025

  ### New Features
  | Description | Benefits |
  | --- | --- |
  | Support for Privilege Access Management added (Read/Assign/De-assign Directory Roles, including privileged roles) | — |
  | Improved operations: Read Role list, Get User Roles, Attach Role, Remove Role (user objects only) | — |

  ### Enhancements
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | UserRolesForSingleUser now uses User ID to handle special characters in usernames | Rebuild Users Catalog sync no longer fails for usernames with special characters | — |

  ---

  ## Version 2025.07.1
  **Version:** 2025.07.1  
  **Release Date:** July 1, 2025

  ### Enhancements
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Special character handling in usernames and group names | Handles special characters across usernames and group names | — |

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | RoleTypes showing invalid data in Technical Roles (fixed group type retrieval and mapping to CustomRoleTypes) | — | — |

  ---

  ## Version 2025.06.2
  **Version:** 2025.06.2  
  **Release Date:** June 27, 2025

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Sync Users job repeating and crashing due to nested groups (API object type corrected to microsoft.graph.user) | — | — |
  | Create User failing due to URL encoding (encoding removed) | — | — |

  ---

  ## Version 2025.06.1
  **Version:** 2025.06.1  
  **Release Date:** June 9, 2025

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Bug [34577]: Access Package created without resource roles in MS Entra ID Governance after publishing a Business Role with new technical roles | — | — |
  | Bug [30971]: Role sync job failure due to Catalog name longer than 20 chars | — | — |

  ---

  ## Version 2025.05.1
  **Version:** 2025.05.1  
  **Release Date:** May 15, 2025

  ### Enhancements
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Support added for usernames with special characters (apostrophe and hashtag) | Usernames with special characters now supported | — |

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | UserRoles sync jobs failing due to auth token expiration (token regeneration configured) | — | — |

  ---

  ## Version 2025.04.1
  **Version:** 2025.04.1  
  **Release Date:** April 3, 2025

  ### Enhancements
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Removed Add Distribution Group Member and Remove Distribution Group Member operations (moved to Microsoft Exchange Online connector) | — | — |

  ---

  ## Version 2025.02.1
  **Version:** 2025.02.1  
  **Release Date:** February 21, 2025

  ### New Features
  | Description | Benefits |
  | --- | --- |
  | Add Distribution Group Member custom operation | — |
  | Remove Distribution Group Member custom operation | — |

  ---

  ## Version 2025.01.2
  **Version:** 2025.01.2  
  **Release Date:** January 13, 2025

  ### New Features
  | Description | Benefits |
  | --- | --- |
  | Get License Details custom operation (read license and plan IDs) | — |

  ### Resolved Issues
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Errors resolved with locking and unlocking users | — | — |

  ---

  ## Version 2025.01.1
  **Version:** 2025.01.1  
  **Release Date:** January 7, 2025

  ### Enhancements
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Added role types for Entra Groups (Security, Microsoft 365, Distribution) and prevented assigning/removing Microsoft 365 and Distribution group types | — | — |

  ---

  ## Version 2024.12.1
  **Version:** 2024.12.1  
  **Release Date:** December 5, 2024

  ### New Features
  | Description | Benefits |
  | --- | --- |
  | Get Group Owners custom operation | — |

  ---

  ## Version 2024.11.1
  **Version:** 2024.11.1  
  **Release Date:** November 20, 2024

  ### New Features
  | Description | Benefits |
  | --- | --- |
  | Microsoft Entra Identity Governance integration | — |

  ### Enhancements
  | Description | Impact | How to Validate |
  | --- | --- | --- |
  | Converted the Pathlock Entra ID connector to the Pathlock Open Connector Framework | — | — |