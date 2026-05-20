# Pathlock Microsoft Entra ID Connector

## 1. Overview
Microsoft Entra ID (formerly Azure Active Directory) is Microsoft's cloud-based identity and access management service. This connector enables Pathlock to manage identities, groups, directory roles, access packages, application permissions, and licenses within Microsoft Entra ID and Entra Identity Governance via the Microsoft Graph API.

---
## 2. Supported Features
- Full Account Import
- Full Access/Entitlement Import
- Single User Refresh
- User-Role Assignment Import
- Child Roles Import
- Create Account
- Update Account Properties
- Set Password
- Lock Account
- Unlock Account
- Add Access
- Remove Access
- Test Connection
- Changed Users Detection

---
## 3. Supported Versions and Compatibility
- **Pathlock Cloud:** 2025.3.1
- **Target System Versions/Flavors:** Microsoft Entra ID P1 and later
- **Integration Method:** REST API (Microsoft Graph API)

---
## 4. Supported Use Cases
- **User Access Review (UAR):** Import users, groups, directory roles, and access package assignments
- **Provisioning:** Create, lock, unlock users; set passwords; manage group and directory role membership; manage licenses
- **Application Access Management:** List service principals; read and remove application and delegated permissions
- **Identity Governance:** Manage access packages and catalog assignments via Entra Identity Governance

---
## 5. Architecture
The connector integrates with Microsoft Entra ID using the Microsoft Graph API v1.0. Authentication is performed via OAuth 2.0 client credentials. All communication is encrypted via HTTPS.

---
## 7. Support
Customers receive **enterprise-level support** for licensed Pathlock connectors:
- Access to full [**documentation**](https://help.pathlock.com/pathlock-cloud-documentation/) and [**training**](https://university-pathlock.talentlms.com/) resources
- Ability to [**raise support tickets**](https://pathlock.com/support/) via the Pathlock Support Portal
- Guidance from Pathlock experts and ongoing updates/enhancements

---
## 8. Provider Information
This app was developed by members of the **Pathlock Community**.
