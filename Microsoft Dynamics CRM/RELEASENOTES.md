# Microsoft Dynamics 365 CRM Connector Release Notes

Check out the latest release information on this page


**Version:** 2026.05.1
**Release Date:** May 04, 2026

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Added Attach Role and Remove Role provisioning operations supporting Security Roles, Teams (Owner Teams), and Field Security Profiles. | Enables automated role assignment and removal across all three role types directly from Pathlock Cloud. |
| 2 | Added Lock User and Unlock User provisioning operations. | Enables automated account enable/disable lifecycle management. |
| 3 | Added Set User Password provisioning operation. | Enables password resets with forced change on next sign-in. |
| 4 | Added Update User (Set User Properties) provisioning operation. | Enables updating user profile attributes (name, email, city, country). |

---

## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Fixed Test_Verify_Credentials connection test to use updated authentication attribute. | Connection test now validates credentials correctly. | Run Test Connection and verify Test_Verify_Credentials passes. |

---

**Version:** 2026.04.2
**Release Date:** April 22, 2026

## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Optimized FetchXML audit query in Full Account Import operation to filter by objecttypecode=8 (systemuser) and limit to the last 3650 days. | Reduced unnecessary data retrieval and improved performance. | Run Full Account Import operation and verify faster execution with correct audit data. |
| 2 | Fixed last logon date parsing in Full Account Import and Single User Refresh operations to correctly convert MM/dd/yyyy HH:mm:ss format to yyyy-MM-dd HH:mm:ss. | Last logon dates now display in a consistent format. | Run Full Account Import and Single User Refresh operations and verify date format. |

---

**Version:** 2026.03.1
**Release Date:** March 03, 2026

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Business Units now anchor the security model, keeping roles and teams with identical names unique per Business Unit. | Prevents role name collisions across Business Units. |
| 2 | Team types (Owner, Access, Security, Office) are now fetched for every team. | Provides complete team classification visibility. |

---

## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Full Access/Entitlement Import and Activity/Transaction Import now organize roles and teams under their parent Business Unit. | Improved organizational context for SOD analysis. | Run Full Access/Entitlement Import and verify Business Unit grouping. |
| 2 | Field Security Profile attributes moved from the Solutions MainApplicationArea to Entities. | More accurate categorization of field-level security. | Run Activity/Transaction Import and verify Field Security Profiles appear under Entities. |

---

## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Fixed Single User Refresh operation to fetch correct roles for users. | User role assignments now return accurate data. | Run Single User Refresh operation and verify correct role mappings. |

---

**Version:** 2026.01.1
**Release Date:** January 20, 2026

## Resolved Issues
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Fixed invalid last logon dates fetched in Full Account Import and Single User Refresh operations. | Last logon dates now display correct values. | Run Full Account Import and Single User Refresh operations and verify LastLogon field. |

---

**Version:** 2025.12.1
**Release Date:** December 24, 2025

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Implemented UpdateUserBusinessUnit custom operation. | Enables reassigning users to different Business Units. |

---

**Version:** 2025.10.1
**Release Date:** October 10, 2025

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Implemented Connection Validation operation. | Enables validation of connectivity and credentials before running operations. |

---

**Version:** 2025.08.1
**Release Date:** August 26, 2025

## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Improved README documentation. | Better configuration guidance and setup instructions. | Review README for updated content. |

---

**Version:** 2025.07.1
**Release Date:** July 28, 2025

## Enhancements
| ID | Description | Impact | How to Validate |
|---|---|---|---|
| 1 | Added Set User Properties operation. | Enables updating user profile attributes. | Run Set User Properties with test parameters and verify changes. |

---

**Version:** 2025.06.1
**Release Date:** June 19, 2025

## New Features
| ID | Description | Benefits |
|---|---|---|
| 1 | Implemented Single User Refresh operation. | Enables on-demand retrieval of individual user data. |
| 2 | Implemented Password Management operation. | Enables password management for user accounts. |
| 3 | Implemented Get User Roles operation (Single User Refresh). | Enables retrieval of role assignments for a specific user. |

---
