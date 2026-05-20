{
    "$schema": "https://schema.management.azure.com/schemas/2019-04-01/deploymentTemplate.json#",
    "contentVersion": "1.0.0.0",
    "parameters": {
    "Logic App Name": {
        "type": "String",
        "metadata": {
            "description": "The name of the Logic App. Only letters, digits, '-', '.', '(', ')' or '_' are allowed. Do not include spaces."
        }
    },
    
    "PathlockTenant": {
        "type": "String",
        "metadata": {
            "description": "Enter the Pathlock tenant URL (e.g., customer.pathlockgrc.com)."
        }
    },
    "PathlockEntraIDSystemName": {
        "type": "String",
        "metadata": {
            "description": "The name of the system in Pathlock from which requests are made"
        }
    },
    "APIUserID": { 
        "type": "String",
        "metadata": {
            "description": "The username used for authenticating with the Pathlock API"
        }
    },
    "APIUserPassword": { 
        "type": "SecureString",
        "metadata": {
            "description": "The password for the API user in Pathlock"
        }
    }
}

,
    "variables": {},
    "resources": [
        {
            "type": "Microsoft.Logic/workflows",
            "apiVersion": "2017-07-01",
            "name": "[parameters('Logic App Name')]",
            "location": "eastus",
            "tags": {
                "Purpose": "Azure AD Entitlement Management"
            },
            "identity": {
                "type": "SystemAssigned"
            },
            "properties": {
                "state": "Enabled",
                "accessControl": {
                    "triggers": {
                        "openAuthenticationPolicies": {
                            "policies": {
                                "AzureADEntitlementManagementPOPAuthPolicy": {
                                    "type": "AADPOP",
                                    "claims": [
                                        {
                                            "name": "iss",
                                            "value": "[concat('https://sts.windows.net/', tenant().tenantId, '/')]"
                                        },
                                        {
  "name": "appid",
  "value": "810dcf14-1858-4bf2-8134-4c369fa3235b"
},

                                        {
                                            "name": "m",
                                            "value": "POST"
                                        },
                                        {
                                            "name": "u",
                                            "value": "management.azure.com"
                                        },
                                        {
                                            "name": "p",
                                            "value": "[concat('/subscriptions/', subscription().subscriptionId, '/resourceGroups/', resourceGroup().name, '/providers/Microsoft.Logic/workflows/', parameters('Logic App Name'))]"


                                        }
                                    ]
                                }
                            }
                        }
                    }
                },
                "definition": {
                    "$schema": "https://schema.management.azure.com/providers/Microsoft.Logic/schemas/2016-06-01/workflowdefinition.json#",
                    "contentVersion": "1.0.0.0",
                    "parameters": {
    "PathlockTenant": {
      "type": "String",
      "defaultValue": "[parameters('PathlockTenant')]"
    },
    "PathlockEntraIDSystemName": {
      "type": "String",
      "defaultValue": "[parameters('PathlockEntraIDSystemName')]"
    },
    "APIUserID": {
      "type": "String",
      "defaultValue": "[parameters('APIUserID')]"
    },
    "APIUserPassword": {
      "type": "String",
      "defaultValue": "[parameters('APIUserPassword')]"
    },
    "$connections": {
      "type": "Object",
      "defaultValue": {}
    }
  },
                    "triggers": {
                        "manual": {
                            "type": "Request",
                            "kind": "Http",
                            "inputs": {
                                "schema": {
                                    "type": "object",
                                    "properties": {
                                        "AccessPackageAssignmentRequestId": {
                                            "type": "string"
                                        },
                                        "CallbackUriPath": {
                                            "type": "string"
                                        },
                                        "CustomExtensionStageInstanceId": {
                                            "type": "string"
                                        },
                                        "Stage": {
                                            "type": "string"
                                        },
                                        "RequestType": {
                                            "type": "string"
                                        },
                                        "Answers": {
                                            "type": "array"
                                        },
                                        "State": {
                                            "type": "string"
                                        },
                                        "Status": {
                                            "type": "string"
                                        },
                                        "CallbackConfiguration": {
                                            "type": "object",
                                            "properties": {
                                                "DurationBeforeTimeout": {
                                                    "type": "string"
                                                }
                                            }
                                        },
                                        "AccessPackage": {
                                            "type": "object",
                                            "properties": {
                                                "Id": {
                                                    "type": "string",
                                                    "description": "AccessPackage-Id"
                                                },
                                                "DisplayName": {
                                                    "type": "string",
                                                    "description": "AccessPackage-DisplayName"
                                                },
                                                "Description": {
                                                    "type": "string",
                                                    "description": "AccessPackage-Description"
                                                }
                                            }
                                        },
                                        "AccessPackageCatalog": {
                                            "type": "object",
                                            "properties": {
                                                "Id": {
                                                    "type": "string",
                                                    "description": "AccessPackageCatalog-Id"
                                                },
                                                "DisplayName": {
                                                    "type": "string",
                                                    "description": "AccessPackageCatalog-DisplayName"
                                                },
                                                "Description": {
                                                    "type": "string",
                                                    "description": "AccessPackageCatalog-Description"
                                                }
                                            }
                                        },
                                        "Assignment": {
                                            "type": "object",
                                            "properties": {
                                                "Id": {
                                                    "type": "string",
                                                    "description": "Assignment-Id"
                                                },
                                                "Target": {
                                                    "type": "object",
                                                    "properties": {
                                                        "ConnectedOrganization": {
                                                            "type": "object",
                                                            "properties": {
                                                                "Id": {
                                                                    "type": "string",
                                                                    "description": "Assignment-Target-ConnectedOrganization-Id"
                                                                },
                                                                "DisplayName": {
                                                                    "type": "string",
                                                                    "description": "Assignment-Target-ConnectedOrganization-DisplayName"
                                                                },
                                                                "Description": {
                                                                    "type": "string",
                                                                    "description": "Assignment-Target-ConnectedOrganization-Description"
                                                                }
                                                            }
                                                        },
                                                        "Id": {
                                                            "type": "string",
                                                            "description": "Assignment-Target-Id"
                                                        },
                                                        "ObjectId": {
                                                            "type": "string",
                                                            "description": "Assignment-Target-ObjectId"
                                                        },
                                                        "DisplayName": {
                                                            "type": "string",
                                                            "description": "Assignment-Target-DisplayName"
                                                        }
                                                    }
                                                },
                                                "State": {
                                                    "type": "string",
                                                    "description": "Assignment-State"
                                                },
                                                "Status": {
                                                    "type": "string",
                                                    "description": "Assignment-Status"
                                                },
                                                "AssignmentPolicy": {
                                                    "type": "object",
                                                    "properties": {
                                                        "Id": {
                                                            "type": "string",
                                                            "description": "AssignmentPolicy-Id"
                                                        },
                                                        "DisplayName": {
                                                            "type": "string",
                                                            "description": "AssignmentPolicy-DisplayName"
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        "Requestor": {
                                            "type": "object",
                                            "properties": {
                                                "Id": {
                                                    "type": "string",
                                                    "description": "Requestor-Id"
                                                },
                                                "ObjectId": {
                                                    "type": "string",
                                                    "description": "Requestor-ObjectId"
                                                },
                                                "DisplayName": {
                                                    "type": "string",
                                                    "description": "Requestor-DisplayName"
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            "operationOptions": "IncludeAuthorizationHeadersInOutputs"
                        }
                    },
                    "actions": {
                        "Is_Stage_RequestCreated": {
                            "actions": {
                                "Pathlock_SOD_Analysis": {
                                    "type": "Http",
                                    "inputs": {
                                        "uri": "https://@{parameters('PathlockTenant')}/app/Portal/SODEntraIG/api.ashx",
                                        "method": "POST",
                                        "body": {
                                            "api_key": "key",
                                            "systemName": "@{parameters('PathlockEntraIDSystemName')}",
                                            "username": "@{triggerBody()?['Assignment']?['Target']?['PrincipalName']}",
                                            "AccessPackageAssignmentId": "@{triggerBody()?['AccessPackageAssignmentRequestId']}",
                                            "BusinessRole": "@{triggerBody()?['AccessPackage']?['DisplayName']}",
                                            "requestVariables": {
                                                "Event": "@{triggerBody()?['Stage']}",
                                                "CallbackUriPath": "@{triggerBody()?['CallbackUriPath']}",
                                                "CustomExtensionStageInstanceId": "@{triggerBody()?['CustomExtensionStageInstanceId']}"
                                            }
                                        },
                                        "authentication": {
                                            "type": "Basic",
                                            "username": "@{parameters('APIUserID')}",
                                            "password": "@{parameters('APIUserPassword')}"
                                        }
                                    },
                                    "runtimeConfiguration": {
                                        "contentTransfer": {
                                            "transferMode": "Chunked"
                                        }
                                    }
                                }
                            },
                            "runAfter": {},
                            "else": {
                                "actions": {
                                    "Is_Stage_Granted": {
                                        "actions": {
                                            "Pathlock_Assign_Access_Package_Roles": {
                                                "type": "Http",
                                                "inputs": {
                                                    "uri": "https://@{parameters('PathlockTenant')}/app/Portal/AssignBusinessRole_Azure_No_Sod/api.ashx",
                                                    "method": "POST",
                                                    "body": {
                                                        "AccessPackageAssignmentId": "@{triggerBody()?['Assignment']?['Id']}",
                                                        "BusinessRole": "@{body('Parse_Access_Package_Name')?['displayName']}",
                                                        "Event": "@{triggerBody()?['Stage']}",
                                                        "api_key": "key",
                                                        "systemName": "@{parameters('PathlockEntraIDSystemName')}",
                                                        "username": "@{body('Parse_User_Principal_Name')?['userPrincipalName']}",
                                                        "requestVariables": {
                                                            "Event": "@{triggerBody()?['Stage']}",
                                                            "CallbackUriPath": "@{triggerBody()?['CallbackUriPath']}",
                                                            "RequestId": "@{triggerBody()?['AccessPackageAssignmentRequestId']}",
                                                            "CustomExtensionStageInstanceId": "@{triggerBody()?['CustomExtensionStageInstanceId']}"
                                                        }
                                                    },
                                                    "authentication": {
                                                        "type": "Basic",
                                                        "username": "@{parameters('APIUserID')}",
                                                        "password": "@{parameters('APIUserPassword')}"
                                                    }
                                                },
                                                "runtimeConfiguration": {
                                                    "contentTransfer": {
                                                        "transferMode": "Chunked"
                                                    }
                                                }
                                            }
                                        },
                                        "runAfter": {
                                            "Parse_Access_Package_Name": [
                                                "Succeeded"
                                            ]
                                        },
                                        "else": {
                                            "actions": {
                                                "Is_Stage_Removed": {
                                                    "actions": {
                                                        "Pathlock_Remove_Access_Package_Roles": {
                                                            "type": "Http",
                                                            "inputs": {
                                                                "uri": "https://@{parameters('PathlockTenant')}/app/Portal/RemoveBusinessRole_Azure_No_Sod/api.ashx",
                                                                "method": "POST",
                                                                "body": {
                                                                    "AccessPackageAssignmentId": "@{triggerBody()?['Assignment']?['Id']}",
                                                                    "BusinessRole": "@{body('Parse_Access_Package_Name')?['displayName']}",
                                                                    "Event": "@{triggerBody()?['Stage']}",
                                                                    "api_key": "key",
                                                                    "systemName": "@{parameters('PathlockEntraIDSystemName')}",
                                                                    "username": "@{body('Parse_User_Principal_Name')?['userPrincipalName']}",
                                                                    "requestVariables": {
                                                                        "Event": "@{triggerBody()?['Stage']}",
                                                                        "CallbackUriPath": "@{triggerBody()?['CallbackUriPath']}",
                                                                        "CustomExtensionStageInstanceId": "@{triggerBody()?['CustomExtensionStageInstanceId']}"
                                                                    }
                                                                },
                                                                "authentication": {
                                                                    "type": "Basic",
                                                                    "username": "@{parameters('APIUserID')}",
                                                                    "password": "@{parameters('APIUserPassword')}"
                                                                }
                                                            },
                                                            "runtimeConfiguration": {
                                                                "contentTransfer": {
                                                                    "transferMode": "Chunked"
                                                                }
                                                            }
                                                        }
                                                    },
                                                    "else": {
                                                        "actions": {
                                                            "Pathlock_Acknowledge_Approval": {
                                                                "type": "Http",
                                                                "inputs": {
                                                                    "uri": "https://@{parameters('PathlockTenant')}/app/Portal/Approval_EntraIG/api.ashx",
                                                                    "method": "POST",
                                                                    "body": {
                                                                        "AccessPackageAssignmentId": "@{triggerBody()?['Assignment']?['Id']}",
                                                                        "BusinessRole": "@{body('Parse_Access_Package_Name')?['displayName']}",
                                                                        "Event": "@{triggerBody()?['Stage']}",
                                                                        "api_key": "key",
                                                                        "systemName": "@{parameters('PathlockEntraIDSystemName')}",
                                                                        "username": "@{body('Parse_User_Principal_Name')?['userPrincipalName']}",
                                                                        "requestVariables": {
                                                                            "Event": "@{triggerBody()?['Stage']}",
                                                                            "CallbackUriPath": "@{triggerBody()?['CallbackUriPath']}",
                                                                            "CustomExtensionStageInstanceId": "@{triggerBody()?['CustomExtensionStageInstanceId']}"
                                                                        }
                                                                    },
                                                                    "authentication": {
                                                                        "type": "Basic",
                                                                        "username": "@{parameters('APIUserID')}",
                                                                        "password": "@{parameters('APIUserPassword')}"
                                                                    }
                                                                },
                                                                "runtimeConfiguration": {
                                                                    "contentTransfer": {
                                                                        "transferMode": "Chunked"
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    "expression": {
                                                        "and": [
                                                            {
                                                                "equals": [
                                                                    "@triggerBody()?['Stage']",
                                                                    "AssignmentRemoved"
                                                                ]
                                                            }
                                                        ]
                                                    },
                                                    "type": "If"
                                                }
                                            }
                                        },
                                        "expression": {
                                            "and": [
                                                {
                                                    "equals": [
                                                        "@triggerBody()?['Stage']",
                                                        "assignmentRequestGranted"
                                                    ]
                                                }
                                            ]
                                        },
                                        "type": "If"
                                    },
                                    "Read_User_PrincipalName": {
                                        "type": "Http",
                                        "inputs": {
                                            "uri": "https://graph.microsoft.com/v1.0/users/@{triggerBody()?['Assignment']?['Target']?['ObjectId']}?$select=userPrincipalName",
                                            "method": "GET",
                                            "authentication": {
                                                "type": "ManagedServiceIdentity",
                                                "audience": "https://graph.microsoft.com"
                                            }
                                        },
                                        "runtimeConfiguration": {
                                            "contentTransfer": {
                                                "transferMode": "Chunked"
                                            }
                                        }
                                    },
                                    "Parse_User_Principal_Name": {
                                        "runAfter": {
                                            "Read_User_PrincipalName": [
                                                "Succeeded"
                                            ]
                                        },
                                        "type": "ParseJson",
                                        "inputs": {
                                            "content": "@body('Read_User_PrincipalName')",
                                            "schema": {
                                                "properties": {
                                                    "@@odata.context": {
                                                        "type": "string"
                                                    },
                                                    "businessPhones": {
                                                        "type": "array"
                                                    },
                                                    "displayName": {
                                                        "type": "string"
                                                    },
                                                    "givenName": {},
                                                    "id": {
                                                        "type": "string"
                                                    },
                                                    "jobTitle": {},
                                                    "mail": {},
                                                    "mobilePhone": {},
                                                    "officeLocation": {},
                                                    "preferredLanguage": {},
                                                    "surname": {},
                                                    "userPrincipalName": {
                                                        "type": "string"
                                                    }
                                                },
                                                "type": "object"
                                            }
                                        }
                                    },
                                    "Read_Access_Package_Name": {
                                        "runAfter": {
                                            "Parse_User_Principal_Name": [
                                                "Succeeded"
                                            ]
                                        },
                                        "type": "Http",
                                        "inputs": {
                                            "uri": "https://graph.microsoft.com/v1.0/identityGovernance/entitlementManagement/accessPackages/@{triggerBody()?['AccessPackage']?['Id']}?$select=displayName",
                                            "method": "GET",
                                            "authentication": {
                                                "type": "ManagedServiceIdentity",
                                                "audience": "https://graph.microsoft.com"
                                            }
                                        },
                                        "runtimeConfiguration": {
                                            "contentTransfer": {
                                                "transferMode": "Chunked"
                                            }
                                        }
                                    },
                                    "Parse_Access_Package_Name": {
                                        "runAfter": {
                                            "Read_Access_Package_Name": [
                                                "Succeeded"
                                            ]
                                        },
                                        "type": "ParseJson",
                                        "inputs": {
                                            "content": "@body('Read_Access_Package_Name')",
                                            "schema": {
                                                "properties": {
                                                    "@@odata.context": {
                                                        "type": "string"
                                                    },
                                                    "catalogId": {
                                                        "type": "string"
                                                    },
                                                    "createdBy": {
                                                        "type": "string"
                                                    },
                                                    "createdDateTime": {
                                                        "type": "string"
                                                    },
                                                    "description": {
                                                        "type": "string"
                                                    },
                                                    "displayName": {
                                                        "type": "string"
                                                    },
                                                    "id": {
                                                        "type": "string"
                                                    },
                                                    "isHidden": {
                                                        "type": "boolean"
                                                    },
                                                    "isRoleScopesVisible": {
                                                        "type": "boolean"
                                                    },
                                                    "modifiedBy": {
                                                        "type": "string"
                                                    },
                                                    "modifiedDateTime": {
                                                        "type": "string"
                                                    }
                                                },
                                                "type": "object"
                                            }
                                        }
                                    }
                                }
                            },
                            "expression": {
                                "and": [
                                    {
                                        "equals": [
                                            "@triggerBody()?['Stage']",
                                            "assignmentRequestCreated"
                                        ]
                                    }
                                ]
                            },
                            "type": "If"
                        }
                    },
                    "outputs": {}
                },
                "parameters": {
                    "$connections": {
                        "type": "Object",
                        "value": {}
                    }
                }
            }
        }
    ]
}