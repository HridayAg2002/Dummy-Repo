# Pathlock Connector - Microsoft Entra ID
## Layer 400 - Filtering Users to be read from Microsoft Entra ID

### Overview

Layer 400 introduces an optional filtering mechanism for reading users from Microsoft Entra. It allows administrators to define custom filter criteria, ensuring only relevant users are considered.

This layer must be added to the connector zip file **before deployment** and requires configuration of the *Variables.txt* file.


### Prerequisites
- **Microsoft Graph API Knowledge:** Familiarity with [Graph API filters](https://learn.microsoft.com/en-us/graph/filter-query-parameter?tabs=http)  and [Users endpoint and available filters](https://learn.microsoft.com/en-us/graph/api/user-list?view=graph-rest-1.0&tabs=http#optional-query-parameters) .
- **Connector Deployment Access**: Ability to modify and deploy the Pathlock connector zip file.
- **Required Files**: Ensure *layer_400* folder is available in the connector package and if not copy it.


### Setup Instructions
1. **Modify** *Variables.txt*
Navigate to the *layer_400/Variables.txt* file and configure the following variables:
- **user_filter**:
Define a filter statement compatible with the Graph API Users endpoint. Only users matching this filter will be processed.
-**Consistency Level**:
When using advanced queries, the consistency level needs to be set to "eventual". See [Advanced Query capabilities](https://learn.microsoft.com/en-us/graph/aad-advanced-queries?tabs=http) 

Example Filter:

```txt
user_filter=country eq 'Belgium' or country eq 'India'
ConsistencyLevel=normal
```

