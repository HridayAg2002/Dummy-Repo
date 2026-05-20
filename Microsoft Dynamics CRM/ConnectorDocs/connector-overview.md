# Pathlock Microsoft Dynamics 365 CRM Connector

## 1. Overview
The Pathlock Microsoft Dynamics 365 CRM Connector integrates with D365 CRM via OData APIs to provide comprehensive user access governance, provisioning, and SOD analysis. It supports user lifecycle management, role assignments with Business Unit context, and detailed security privilege analysis.

## 2. Supported Features
| Feature | Description |
|---|---|
| Full Account Import | Imports all system users from D365 CRM |
| Full Access/Entitlement Import | Imports security roles, teams, and field security profiles |
| Child Role Hierarchy Import | Imports team-to-role membership relationships |
| Update Account | Updates user properties |
| Disable Account | Locks/disables user accounts |
| Enable Account | Unlocks/enables user accounts |
| Add Access | Assigns security roles to users |
| Remove Access | Removes security roles from users |
| Password Management | Sets user passwords via Microsoft Entra ID |
| Activity/Transaction Import | Imports activity and transaction definitions |
| Role Content Import | Imports detailed role-to-privilege assignments |
| Connection Validation | Tests connectivity and credentials |
| Single User Refresh | Retrieves data for a specific user on demand |

- Support for retrieving Business Unit IDs for roles and teams
## 3. Supported Versions and Compatibility
| Component | Version |
|---|---|
| Connector Version | 2026.05.1 |
| Minimum Pathlock Cloud Version | 2025.4.0 |
| Minimum Target Application Version | v9.0 |

## 4. Supported Use Cases
- **User Access Review (UAR):** Full support for reading users, roles, role assignments, and child role hierarchy
- **Provisioning:** User lifecycle management including update, lock, unlock, and role assignment/removal
- **Segregation of Duties (SOD):** Full SOD analysis with transactions, role content, and child role hierarchy

## 5. Architecture
The connector integrates with Microsoft Dynamics 365 CRM using OData REST APIs with OAuth 2.0 authentication. Business Units serve as the organizational anchor for the security model, ensuring roles and teams with identical names remain unique per Business Unit.

## 6. Audit and Compliance
The connector supports activity/transaction import and role content import, enabling SOD analysis and risk assessment across security roles, teams, and field security profiles.

## 7. Limitations
- **User creation is not supported.** New users must be provisioned in Microsoft Entra ID and surfaced in Dynamics 365 CRM through standard licensing assignment.
- **Team provisioning is restricted to user-created Owner Teams.** Office Teams and Security Teams are backed by Microsoft Entra ID groups and must be managed there.
- **Default Business Unit Owner Teams are not provisionable.** Every Business Unit has a system-managed default Owner Team that shares the Business Unit's name (for example, `Team:Ivory Coast__Ivory Coast`). Membership is derived automatically from the user's Business Unit assignment and cannot be modified through the Dataverse `AddMembersTeam` / `RemoveMembersTeam` actions. To change a user's membership in a default Business Unit team, move the user to a different Business Unit using the `UpdateUserBusinessUnit` custom operation.
- **Security Role assignment requires matching Business Unit.** Dataverse rejects assignments where the user and the Security Role belong to different Business Units. This constraint does not apply to Teams or Field Security Profiles.

## 8. Support

Customers receive **enterprise-level support** for licensed Pathlock connectors:

- Access to full [**documentation**](https://help.pathlock.com/pathlock-cloud-documentation/) and [**training**](https://university-pathlock.talentlms.com/) resources
- Ability to [**raise support tickets**](https://pathlock.com/support/) via the Pathlock Support Portal
- Guidance from Pathlock experts and ongoing updates/enhancements

## 9. Provider Information
Developed by Pathlock Community.
