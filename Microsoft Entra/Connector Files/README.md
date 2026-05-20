# Pathlock Connector - Microsoft Entra ID and Microsoft Entra Identity Governance

## Configuration

- **Username:** Mention the Application (client) ID. See → [_Register a client application_](#registering-a-client-application-for-integration) section.
- **Password:** Mention the Application (client) Secret. See → [_Create a client secret_](#create-a-client-secret) section.
- **Graph API version:** The Graph API Version. Use value "v1.0".
- **Tenant ID:** Mention the Directory (tenant) ID. See below Addition Information → _Register a client application_ section.
- **Read Access Packages** : Set this value to Yes to synchronize [Microsoft Entra Identity Governance Access Packages](#microsoft-entra-identity-governance-access-packages) into Pathlock Cloud as roles.

- **Entra IG Policy Template name:** This parameter is only relevant when using "Microsoft Entra Identity Governance" Integration. See -> [_Microsoft Entra Identity Governance Integration_](#microsoft-entra-identity-governance-integration)
- **Pathlock API Host:** Base URL for Pathlock custom report API (for example: `https://plc-integration.stage.pathlockgrc.com`).
- **Pathlock API User:** Username for Pathlock custom report API Basic Authentication (used by `createAccessPackage`, `ValidateBusinessRoleContent`, and `GetRiskInfo`).
- **Pathlock API Password:** Password for Pathlock custom report API Basic Authentication (used by `createAccessPackage`, `ValidateBusinessRoleContent`, and `GetRiskInfo`).

## Security Model Mapping

- **Users** : Users in Microsoft Entra ID
- **Roles** :
  - Groups in Microsoft Entra ID
    Group Types
    - Security : Security Groups can be assigned to or removed from users
    - Microsoft 365 : These Groups are Mail Enabled, and cannot be assigned or removed
    - Distribution : These Groups are Mail Enabled, and cannot be assigned or removed
  - Roles - Directory Roles (Microsoft Entra Roles)
    - Directory Roles are depicted with a prefix **PRIV**. These are used to provide users with the privileges to access Entra resources. Refer [_Privilege Access_](#privilege-access) section.
  - Access Packages in _Microsoft Entra Identity Governance_
    - Access Packages will only be synchronized when **Read Access Packages** is set to _Yes_. See -> [_Microsoft Entra Identity Governance Access Packages_](#microsoft-entra-identity-governance-access-packages)

**Notes**   
   Group prefix values ​​by default are as given below.
    - DistributionGroup=DIST:
    - Microsoft365Group=M365:
    - SecurityGroup=SEC:
    If you need to update the Group prefix values, open the variable.txt file and modify the prefix entry.
	
| Level   | Description          | Read /SOD          | Provision          | Usage Log |
| ------- | -------------------- | ------------------ | ------------------ | --------- |
| Level 1 | Group: Security      | :white_check_mark: | :white_check_mark: |           |
| Level 1 | Group: Microsoft 365 | :white_check_mark: | :white_check_mark: |           |
| Level 1 | Group: Distribution  | :white_check_mark: |                    |           |
| Level 2 | Role: Directory      | :white_check_mark: | :white_check_mark: |           |
| Level 2 | Role: Privileged     | :white_check_mark: | :white_check_mark: |           |

## Supported Operations

### Standard Operations

| Operation                  | Supported          |
| -------------------------- | ------------------ |
| Read User List             | :white_check_mark: |
| Read Role List             | :white_check_mark: |
| Read User Role Assignments | :white_check_mark: |
| Create User                | :white_check_mark: |
| Set User Properties        | :white_check_mark: |
| Lock User                  | :white_check_mark: |
| UnLock User                | :white_check_mark: |
| Attach Role to User        | :white_check_mark: |
| Remove Role from User      | :white_check_mark: |
| GetChangedUsers            | :white_check_mark: |

### Custom Operations

| Operation                          | Supported          |
| ---------------------------------- | ------------------ |
| Assign License                     | :white_check_mark: |
| Assign License with Disabled Plans | :white_check_mark: |
| Remove License                     | :white_check_mark: |
| Remove All Licenses                | :white_check_mark: |
| Get Group Owners                   | :white_check_mark: |
| Get License Details                | :white_check_mark: |
| List Service Principals            | :white_check_mark: |
| List Service Principal Permissions | :white_check_mark: |
| Remove Application Permission      | :white_check_mark: |
| Remove Delegated Permission        | :white_check_mark: |
| CreateGuestUser                    | :white_check_mark: |



## Connection Test



### Supported Test Operations

| Operation                     | Working              | isCritical?               |
| ------------------------------| ---------------------| ---------------------   | 
| Test Credentials Operation       | Checks if we are able to get an access token  | :white_check_mark:      |
| Test Read Users Operation      | Fetches a few users to test the operation | :white_check_mark:      |
| Test Read Roles Operation       | Fetches a few roles to test the operation | :white_check_mark:      |
| Test Read Child Roles Operation   | Fetches a few child roles to test the operation | :white_check_mark:      |
| Test Verify Read Permissions Operation    | Checks and verifies the presence of necessary read permissions mentioned in the README in [Assign Permissions to the client application](#assign-permissions-to-the-client-application) section | :white_check_mark:      |
| Test Verify Write Permissions Operation    | Checks and verifies the presence of necessary write permissions mentioned in the README in [Assign Permissions to the client application](#assign-permissions-to-the-client-application) section | :x:    |

 
### Notes

- All tests above are marked as **critical**. If all critical tests pass, the connector is considered working.
- Success and failure messages are configured for each test in `Test_OperationList.xml`.
- Test definitions are managed in `ConnectionTest/Test_OperationList.xml` and mapped in `Mapping.xml`.  



### Usage

Run **Test Connection** from PLC to execute all configured tests and verify connector functionality for all major operations.  
If all critical tests succeed, the connector is assumed to be configured and working correctly.
 

## Field Mapping Information

### Users

**API Endpoint**: /users

| **Pathlock Cloud Field** | **API Field**              | **Notes**                                                 |
| ------------------------ | -------------------------- | --------------------------------------------------------- |
| UserName                 | userPrincipalName          | User's Username in MS Entra                               |
| FullName                 | displayName                | User's Firstname and Lastname in MS Entra                 |
| EMail                    | mail                       | User's Business Email ID in MS Entra                      |
| CreateDate               | createdDateTime            | User's creation date in MS Entra                          |
| PasswordChangedDate      | lastPasswordChangeDateTime | User's password change date on in MS Entra                |
| LockStatus               | accountEnabled             | If LockStatus is 'True', then User is Active              |
| ApiIdentifier            | id                         | User's Username in MS Entra                               |
| UserType                 | EmployeeType               | User's EmployeeType in MS Entra                           |
| Company                  | companyName                | User's company name in MS Entra                           |
| City                     | city                       | User's City in MS Entra                                   |
| Building                 | employeeType               | User's EmployeeType in MS Entra                           |
| Country                  | country                    | User's Country in MS Entra                               |
| Department               | department                 | User's Department in Organization Information             |
| UserType                 | userType                   | User's userType in MS Entra                               |

### Roles

#### Groups

**API Endpoint**: /groups

| **Pathlock Cloud Field** | **API Field**   | **Notes**                                                           |
| ------------------------ | --------------- | ------------------------------------------------------------------- |
| RoleName                 | displayName     | Group name in Entra                                                 |
| RoleAttribute1           | id              | Group ID                                                            |
| CreateDate               | createdDate     | Group create date in Entra                                          |
| Description              | description     | Group description                                                   |
| CustomRoleType           | GroupType       | Group Type in Entra                                                 |
| RoleAttribute2           | MailEnabled     | If the group is mail-enabled in Exchange                                |
| RoleAttribute3           | SecurityEnabled | depicts whether the group be used for access/permission assignments |

## Additional Information

### Prerequisites

- A Microsoft Entra ID tenant account that has at least the Cloud Application Administrator role.

### Registering a client application for integration

1. Sign in to the Microsoft Entra admin center https://entra.microsoft.com/
2. If you have access to multiple tenants, use the Settings icon in the top menu to switch to the tenant in which you want to register the application from the Directories + subscriptions menu.
3. Browse to Identity > Applications > App registrations and select New registration.
4. Enter a display Name for your application.
5. Choose an option for: Specify who can use the application in the Supported account types section.
6. Don't enter anything for Redirect URI (optional).
7. Select Register to complete the initial app registration.
8. When registration finishes, the Microsoft Entra admin center displays the app registration's Overview pane. On this page, the app was assigned values for the Application (client) ID and Directory (tenant) ID. Copy and securely save these values.

#### Create a client secret

1. Select Certificates and secrets > Client secrets tab.
2. Click New client secret button.
3. Enter a description and set the Expiry days/months.
4. Click Add button and Copy the secret from 'Value' column. This is the Application (client) secret. Copy and securely save the secret.

#### Assign Permissions to the client application

1. Select API permissions > Add a permission > Microsoft Graph > Application permissions
2. Select these permissions:
   - Application.Read.All
   - Application.ReadWrite.All
   - AppRoleAssignment.ReadWrite.All
   - Group.Read.All
   - Group.ReadWrite.All
   - GroupMember.ReadWrite.All
   - User.EnableDisableAccount.All
   - User.ReadWrite.All
   - User-PasswordProfile.ReadWrite.All
   - User.ManageIdentities.All
   - UserAuthenticationMethod.ReadWrite.All
   - DelegatedPermissionGrant.ReadWrite.All
   - Directory.ReadWrite.All
   - RoleManagement.ReadWrite.Directory
   - RoleManagement.Read.All
   - User.Invite.All
   - Below authorizations are only required when using "Microsoft Entra Identity Governance" Integration. See -> [_Microsoft Entra Identity Governance Integration_](#microsoft-entra-identity-governance-integration)
     - EntitlementManagement.Read.All
     - EntitlementManagement.ReadWrite.All
3. And, then click Add permissions.
4. Click the 'Grant admin consent for [your tenant]' button and, then click Yes to finish.

---

## Privilege Access

This connector supports Read, Assign and De-assign Directory Roles (Microsoft Entra Roles).

> **NOTE:** Operations in this section are valid only for providing these roles to User type objects. It does _NOT_ support adding privileges to Groups and Applications/Service Principals.

### Read Role List

Read all Groups and Directory Roles in Entra. All Directory Roles are depicted with _PRIV_ prefix. Some roles are marked to be **Privileged** (have sensitive permissions) and have been specifically mentioned as Privileged. Other Directory Roles are mentioned as **Directory**.

### Get User Roles

**Mandatory Parameters:**

- Username

Fetch all Groups and Directory Roles (Only Active Role Assignments) assigned to the user.

### Attach Role To User

**Mandatory Parameters:**

- UserName
- RoleName

Attach Group or Directory Role to the user.

### Remove Role From User

**Mandatory Parameters:**

- UserName
- RoleName

Remove Group or Directory Role from the user.

---

## Application Access

This connector supports Listing Service Principals, Reading Application and Delegated permissions as well as remove them. This falls under the Non-Human Identity (NHI) bucket of Microsoft Entra Identity Governance.

### List Service Principals

List all service principals within the Entra tenant, including Applications, Managed Identities and Legacy service principals.

### List Service Principal Permissions

List all Application and Delegated permissions

**Mandatory Parameters**
| Parameter | Description | Example |
| --------- | --------------------------------------------------- | ---------------- |
| AppName | ApplicationName (same as in Service Principal List) | Test_Application |

### Remove Application Permissions

Remove Application Permission from the Service Principal

**Mandatory Parameters**
| Parameter | Description | Example |
| ---------- | ----------------------------------------------------------------------- | --------------------------- |
| AppName | ApplicationName (same as in Service Principal List) | Test_Application |
| Permission | Permission to be removed (same as in Service Principal Permission List) | User.Read.All (Application) |

### Remove Delegated Permissions

Remove Application Permission from the Service Principal

**Mandatory Parameters**
| Parameter | Description | Example |
| ---------- | ----------------------------------------------------------------------- | ------------------------- |
| AppName | ApplicationName (same as in Service Principal List) | Test_Application |
| Permission | Permission to be removed (same as in Service Principal Permission List) | User.Read.All (Delegated) |

> NOTE:
> When you remove a permission (Application or Delegated), it will be deleted from all resources where it appears.
> **Example:** If User.Read.All (Application) is granted in both Microsoft Graph and SharePoint Online, running the Remove Application Permission operation will remove it from both resources.

---

## Other Operation Details

### Set User Password

**Mandatory Parameters:**

- username
- password

This will set the new password, and set ForceChangePasswordNextSignIn to true.

In case you want to set a new password, but not force the reset on next signin, call Update User operation with the following parameters:

- passwordProfile_password : new password
- passwordProfile_forceChangePasswordNextSignIn : true

### Update User

**Mandatory Parameters:**

- username ( UserPrincipalName )

**Optional Parameters:**

- Any simple user attribute can be specified as user\_[attributeName]. A simple user attribute is one that is not a collection or Child Object.
  - For a list of properties, see https://learn.microsoft.com/en-us/graph/api/resources/user?view=graph-rest-1.0#properties
  - For example, to set the department field, pass the parameter user_department to the operation.
- Child Objects can be specified by using the name of the child object , followed by an underscore (\_) , followed by the attributename.
  - The following child objects are supported:
    - onPremisesExtensionAttributes
    - onPremisesSipInfo
    - cloudRealtimeCommunicationInfo
    - identities
  - Examples:
    - To set extensionAttribute1, pass the parameter onPremisesExtensionAttributes_extensionAttribute1 to the operation.
    - To set sipPrimaryAddress, pass the parameter onPremisesSipInfo_sipPrimaryAddress to the operation.

### Create User

**Mandatory Parameters:**

- username ( UserPrincipalName )
- password

**Optional Parameters:**

- DisplayName
- ForceChangePasswordNextSignIn, true or false. Default value is false.
- Any simple user attribute can be specified as user\_[attributeName]. A simple user attribute is one that is not a collection or Child Object.
  - For a list of properties, see https://learn.microsoft.com/en-us/graph/api/resources/user?view=graph-rest-1.0#properties
  - For example, to set the department field, pass the parameter user_department to the operation.
- Child Objects can be specified by using the name of the child object , followed by an underscore (\_) , followed by the attributename.
  - The following child objects are supported:
    - onPremisesExtensionAttributes
    - onPremisesSipInfo
    - cloudRealtimeCommunicationInfo
    - identities
  - Examples:
    - To set extensionAttribute1, pass the parameter onPremisesExtensionAttributes_extensionAttribute1 to the operation.
    - To set sipPrimaryAddress, pass the parameter onPremisesSipInfo_sipPrimaryAddress to the operation.

#### Create Guest User

**API Endpoint**: /invitations

| Parameter                | Description                                                    | Required (Y/N) ?                    |
| ------------------------ | -------------------------------------------------------------- | ----------------------------------- |
| UserName                 | User's Firstname and Lastname in MS Entra                      | :white_check_mark: Yes              |
| Email Address            | User's Business Email ID in MS Entra                           | :white_check_mark: Yes              |
| Email Message            | Custom message included in the guest user’s invitation email.  | :white_check_mark: Yes              |

 **NOTE:** Assign the User.Invite.All Permissions to the client application.
 
 
### Assign License

**Mandatory Parameters:**

- userid ( UserPrincipalName )
- skuId

### Assign License with Disabled Plans

**Mandatory Parameters:**

- userid ( UserPrincipalName )
- skuId
- disabledPlans : comma separated list of plans to disable, each value should be between double quotes.
  - example : "plan1", "plan2". A plan should be specified by it's id.

### Remove License

**Mandatory Parameters:**

- userid ( UserPrincipalName )
- skuId

### Remove All Licenses

**Mandatory Parameters:**

- userid ( UserPrincipalName )

### Get License Details

This operation will return all the available licenses, and their plans.

> For this operation, api permission LicenseAssignment.ReadWrite.All or LicenseAssignment.Read.All is required

**Optional Parameters:**

- licenseFilter : Only return licenses that contain this text. Leave blank for all licenses

### Get Group Owners

No Parameters.
This operation returns all Owners for all Groups.

---

## Microsoft Entra Identity Governance Access Packages

By enabling this feature, _Access Packages_ from Microsoft Entra Identity Governance will be loaded as roles for the users in Pathlock Cloud.

These roles will have the prefix _AP:_.
The names of the roles will contain the Access Package Catalog, followed by a _/_, followed by the name of the Access Package.

### Example Access Package names

Access Catalog : Finance
Access Package : Controller
Pathlock Role Name : AP:Finance/Controller

### Enabling

To enable this functionality, set System Parameter _Read Access Packages_ to Yes.

### Access Packages as Cross System Composite roles in Pathlock Cloud

Access Packages can contain resource roles in Applications defined in Entra ID.

If these Applications are also defined in Pathlock Cloud as systems, the Access Packages can be loaded as cross system composite roles.

To link the App name in Microsoft Entra ID, to the System in pathlock Cloud, enter the Display Name of the Entra ID App, in the field _CUA - Rfc Destinations_ of the System in Pathlock Cloud.

> **Menu Path:** Admin > Technical Configuration > Connected Systems > Systems

---

## Microsoft Entra Identity Governance Integration

This section describes the Integration between Microsoft Entra Identity Governance and Pathlock Cloud.

> **Note:** The information in this section is only relevant in case Provisioning Requests for Access Packages are done from Microsoft Entra Identity Governance, and Pathlock Cloud Business roles are Published to Microsoft Entra Identity Governance.

### Preparing Pathlock Cloud

#### Enable Publishing of Business Roles

- Navigate to _Admin_ > _Server Maintenance_ > _File System Manager_
- Create a folder with the name _ScreenConfig_
- Upload the file _BusinessRoles.xml_ into this folder.

#### Uploading Workflows

- Navigate to _Admin_ > _Workflow_ > _Workflow Types_
- Use the ⋮-menu and select _Import_.
- Import the Workflows by uploading the file _Pathlock - Entra IG Integration - Workflows.zip_.

#### Uploading Reports

- Navigate to _Admin_ > _Reporting_ > _Custom reports Configuration_
- Use the ⋮-menu and select _Import_.
- Import the reports by uploading the file _Pathlock - Entra IG Integration - Reports.zip_.

#### Create Integration User for Entra Logic App

- Navigate to _Admin_ > _System Settings_ > _Users Admin_ > _Pathlock Users Administration_
- Create a Pathlock Cloud User
- Assign the required roles to the User
- Note down the username and password, to be used in the [Logic App Parameters](#logic-app-parameters)

#### Deploy the Target System Connector

- Create a System, and upload the _Microsoft Entra ID_ connector, and configure it.

#### Configure the Pathlock Url

- Navigate to _Admin_ > _Technical Configuration_ > _Connected Systems_ > _Connector Editor_
- Select the System that you created in the previous step for _Microsoft Entra ID_
- Edit the _layer_200/Variables.txt_ file
- Set the variable _pathlockurl_ to the url of your Pathlock Instance, for example *https://customer.pre-prod.pathlockgrc.com*

```txt
  pathlockurl=https://customer.pre-prod.pathlockgrc.com
```

#### Deploy the HR Identity Source Connector

- Deploy the _Azure Active Directory - HR ID Source - Connector_ , and configure the Employee Source.

#### Access Package Catalog

Pathlock Cloud can publish _Business Roles_ as _Access Packages_ into one _Access Package Catalog_ in Microsoft Entra Identity Governance.

To configure which [_Access Package Catalog_](#configure-or-create-the-access-package-catalog) to use, configure the workflow step parameter _PackageCatalog_ in step _Publish Business Role_ in workflow _Publish Business Role as Access Package_ in Pathlock Cloud.

The value of this parameter should be the name of the Access Package Catalog.

#### Pathlock Custom Report API

**Operation Name:** **`createAccessPackage`**, **`ValidateBusinessRoleContent`**, **`GetRiskInfo`**

The following connector operation files call Pathlock custom report APIs:

- `createAccessPackage/06_getBusinessRoleContent.xml` (operation `createAccessPackage`)

- `validateaps/02_loadBusinessRoles.xml` (operation `ValidateBusinessRoleContent`)

- `ig_callback/01_GetRiskInfo.xml` (operation `GetRiskInfo`)

- `POST https://<pathlock-host>/app/api/ReportRun/PostRunReport?type=custom&name=...`

- API host is configured by system parameter `Pathlock_API_Host`
- Authentication: Basic Authentication using system parameters `Pathlock_API_User` and `Pathlock_API_Password`

#### Provisioning on Target systems

By default, when a request is approved in _Microsoft Entra Identity Governance_, Pathlock Cloud will assign the _Business Role_ and all _Technical Roles_ that are part of it.

If a user does not exist yet in the target system, that user will also be created.

The username will be determined based on the [_Username Pattern Configuration_](#username-pattern-configuration)

If provisioning is not required on all systems, configure [_Excluding Systems for Provisioning_](#excluding-systems-for-provisioning)

#### Username Pattern Configuration

- Navigate to _Admin_ > _User Lifecycle Management_ > _User Account Provisioning_ > _Pattern Sets Configuration_
- Create a new _Pattern Set_ with the name _Entra IG_
- Navigate to _Admin_ > _User Lifecycle Management_ > _User Account Provisioning_ > _Username Pattern Configuration_
- For each _Target System_ in scope, configure the Username Pattern for the target system.

#### Excluding Systems for Provisioning

- Navigate to _Admin_ > _Workflow_ > _Parameters_ > _Workflow Parameters Configuration_
- Select workflow type _Assign Business Role after SOD Check_ in the _Filter_ section of the screen.

- Add a new record
- In the _Field Type_ select [New Value]
- In the field that is displayed, add "enable\_" followed by the name of the system in Pathlock Cloud.
  - For example "enable_SAP ERP"
- In the value field, enter _No_

- Repeat this for every system for which provisioning should be disabled.

#### Configuring the Synchronization Reporting

Pathlock Cloud contains a report that will allow identifying any differences between _Business Roles_ and _Access Packages_.

- Navigate to _Admin_ > _Extended Data Models_ > _Extended Data Tables_
- Click "New Record"
  - Table Name : **BusinessRoleVSAccessPackage**
  - Description: **Differences between Business Roles in Pathlock Cloud and Access Packages in Microsoft Entra Identity Governance**
- Save
- Click the Gear icon ⚙ in fron of Table **BusinessRoleVSAccessPackage**
- Click _Manage Data Import_
- Enter the following values :
  - Operation Name : **ValidateBusinessRoleContent**
  - Operation Parameters : **PackageCatalog=[Name of the Access Package Catalog]**
    - Replace "[Name of the Access Package Catalog]" with the name of the Access Package Catalog
    - See [Access Package Catalog](#access-package-catalog)
  - Time Interval : **Daily**
  - Is Active : Enabled
  - Next Run : Select today's date
  - Web Hook : disabled
  - Incremental Load : disabled

#### Configuring the Risk Analysis in the workflow

By default, Pathlock Cloud will only include the following Risks in the workflow:

- Segregation of Duties Risks
- Risks that are caused by the new Business Role/Access Package that is assigned

That means, the following risks are not include:

- Sensitive Access
- Risks the User or Employee already had because of earlier assigned Access

The following Advanced Configuration Parameters can be used to configure this:

- _SoD Simulate Include Previous Violations_ : when _enabled_ a simulated Risk analysis will include All risks of a user/employee.
- _Show All Risks In Risk Analysis_ : when _enabled_ this will include All risks of a user/employee in the Workflow, new risks as well as existing risks. In the Workflow page, existing risks are displayed with the indicator _From old mapping_.
- _SoD Impact Analysis Calculator Exclude Sensitive Access_ : when _disabled_, Sensitive Access Risks will also be included in the Workflow.

> **CAUTION:**
> To include existing violations in the workflow, both _SoD Simulate Include Previous Violations_ and _Show All Risks In Risk Analysis_ should be enabled.

### Preparing Microsoft Entra Identity Governance

#### Configure or create the Access Package Catalog

- In Microsoft Entra Identity Governance, create or find the _Access Package Catalog_ to use for Pathlock Cloud Integration.
- Configure the name of this _Access Package Catalog_ in Pathlock Cloud. See → [_Access Package Catalog_](#access-package-catalog) section.

#### Create a Policy Template

- In any _Access Package_, in any _Access Package Catalog_, create a Policy that should be used as a template for all _Access Packages_ published by Pathlock Cloud.
- Configure the name in system parameter _Entra IG Policy Template name_.
- This policy does not need to be active, it is used to ensure all _Access Packages_, have the correct configuration settings and request forms.

### Step-by-Step: Deploy Logic App via ARM Template and Use It in a Custom Extension

#### 1. Deploy the Logic App Using the ARM Template (via Azure Portal)

1. Go to the Azure Portal: [https://portal.azure.com](https://portal.azure.com)
2. Search for **Deploy a custom template**.
3. Click **Build your own template in the editor**.
4. In the template editor, click **Load file** and upload your ARM template (e.g., `EntraIG_LogicApp_ARM_Deploy.json`).
5. Click **Save** after loading the template.
6. In the **Custom deployment** form, fill in the required parameters:

   - **Subscription**: Select your Azure subscription.
   - **Resource group**: Choose an existing group or click **Create new**.
   - **Region**: e.g., `East US`.
   - **Logic App Name**: Enter the name for the Logic App.
   - **Pathlock Tenant**: Enter the Pathlock tenant URL (e.g., `ccustomer.pre-prod.pathlockgrc.com`).
   - **Pathlock Entra ID System Name**: Enter the system name configured in Pathlock for [Target System Connector](#deploy-the-target-system-connector) integration with Microsoft Entra ID Governance.
   - **API User ID**: Enter the username for the [integration user](#create-integration-user-for-entra-logic-app).
   - **API User Password**: Enter the corresponding API password for [integration user](#create-integration-user-for-entra-logic-app).

7. Click **Review + Create**, review the details, and then click **Create** to deploy the Logic App.

#### 2. Use the Deployed Logic App in a Custom Extension (Microsoft Entra ID Governance)

1. Go to the **Microsoft Entra Admin Center**.
2. Navigate to **Identity Governance > Entitlement Management > Catalogs**.
3. Select the catalog where you want to add the custom extension (or create a new one).
4. Go to the **Custom Extensions** tab.
5. Click **+ Add Custom Extension**.

##### a. Basics

- Provide a name and description for your custom extension.

##### b. Extension Type

- Select **Request workflow**.
- Choose trigger events like:
  - Request Created
  - Request Approved
  - Access Granted
  - Access Removed

##### c. Extension Configuration

- Select **Launch and wait** behavior.
- Set **Wait time** to `1 day` (or as needed).

##### d. Details

- When asked **"Do you want to create a new Logic App?"**, select **No**.
- Pick the Logic App you deployed earlier from the list.

##### e. Review + Create

- Confirm all values and click **Create** to finalize the custom extension setup.

> The user that will create the Custom Extension requires _Contributer_ access to the Azure Subscription.
> This access can be granted from the _Access Control(IAM)_ page of the _Azure Subscription_

#### Logic App Identity

- In the _Custom Extension_ screen, click on the name of the _Logic App_.
- Select "Identity"
- Set the status to _On_
- Save
- Confirm _Enable system assigned managed identity_
- Copy the _Object (principal) Id_
- In _Microsoft Entra ID_, navigate to _Enterprise Applications_
- Remove all filters
- Paste the copied _Object (principal) Id_ into the search field
- The search result shows an App with the same name as your Logic App.
- Copy the name of the App

#### Logic App Authorizations

The authorizations the Logic App requires on Entra can be assigned using a PowerShell Script.

- The following Authorizations will be assigned:

  - User.Read.All
  - EntitlementManagement.Read.All

- On a windows computer, execute the powershell script _AuthorizeLogicApp.ps1_ and provide the name of the app to parameter _AppName_

  ```powershell
  Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass -Force
  ./AuthorizeLogicApp.ps1 -AppName "[your appname here]"
  ```


> **Note:** If system security prevents you from running the PowerShell script, use the Postman collection in the connector package’s scripts directory to authorize the Logic App. The Application (client) must have the AppRoleAssignment.ReadWrite.All role.

- In the Authentication Popup, enter the Entra credentials for a user with the _Global Administrator_-role.
