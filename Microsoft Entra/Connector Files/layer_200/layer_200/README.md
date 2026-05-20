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

#### Enable the System in the Workflow Step

The Entra IG connector system must be enabled in the workflow step that handles the integration callback from Microsoft Entra Identity Governance.

1. Navigate to _Admin_ > _Workflow_ > _Workflow Types_
2. Open the workflow **Assign Business Role After SOD Check**
3. Locate the step **Send feedback to Entra Identity Governance**
4. Click on the gear button to open the _View Step Parameters_
5. Go to the **Systems** tab
6. In the _Enabled for Systems_ list, check the box next to the Entra IG connector system
7. Click **Save**

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