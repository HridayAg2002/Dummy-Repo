param(
    [Parameter(Mandatory = $true)]
    [string]$AppName
)


$permissions = @("User.Read.All", "EntitlementManagement.Read.All")

# 1. Install Microsoft Graph module if missing
if (-not (Get-Module -ListAvailable -Name Microsoft.Graph.Applications)) {
    Write-Host "Installing Microsoft.Graph module..."
    Install-Module -Name Microsoft.Graph.Applications -Force
}

# 2. Connect to Microsoft Graph
$scriptScopes = @("AppRoleAssignment.ReadWrite.All", "Directory.Read.All")
try {
    Connect-MgGraph -Scopes $scriptScopes -ErrorAction Stop
}
catch {
    Write-Host "Failed to connect. Ensure you have the required rights." -ForegroundColor Red
    exit
}

# 3. Retrieve the Target Application (The Logic App Service Principal)
$app = Get-MgServicePrincipal -Filter "displayName eq '$AppName'" -ConsistencyLevel eventual

if ($null -eq $app) {
    Write-Host "Application with name '$AppName' not found." -ForegroundColor Red
    exit
} elseif ($app.Count -gt 1) {
    Write-Host "Multiple applications found with name '$AppName'. Please provide a unique name." -ForegroundColor Red
    exit
}

# 4. Retrieve the Microsoft Graph Service Principal (The Resource)
$graphAppId = "00000003-0000-0000-c000-000000000000" 
$graphApi = Get-MgServicePrincipal -Filter "appId eq '$graphAppId'"

if ($null -eq $graphApi) {
    Write-Host "Microsoft Graph service principal not found." -ForegroundColor Red
    exit
}

# 5. Iterate and Assign Permissions
foreach ($permissionName in $permissions) {
    
    $role = $graphApi.AppRoles | Where-Object { 
        $_.Value -eq $permissionName -and $_.AllowedMemberTypes -contains "Application" 
    }

    if ($null -eq $role) {
        Write-Host "Permission '$permissionName' not found or is not an Application permission." -ForegroundColor Yellow
        continue
    }

    $existingAssignment = Get-MgServicePrincipalAppRoleAssignment -ServicePrincipalId $app.Id | Where-Object { $_.AppRoleId -eq $role.Id }

    if ($existingAssignment) {
        Write-Host "Permission '$permissionName' is already assigned to '$AppName'." -ForegroundColor Cyan
    }
    else {
        try {
            New-MgServicePrincipalAppRoleAssignment `
                -ServicePrincipalId $app.Id `
                -PrincipalId $app.Id `
                -ResourceId $graphApi.Id `
                -AppRoleId $role.Id | Out-Null

            Write-Host "Successfully granted '$permissionName' to '$AppName'." -ForegroundColor Green
        } catch {
            Write-Host "Error granting '$permissionName': $_" -ForegroundColor Red
        }
    }
}
Disconnect-MgGraph