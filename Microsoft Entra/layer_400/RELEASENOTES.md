# Pathlock Connector - Microsoft Entra ID and Microsoft Entra 
## Layer 400 - Filtering Users to be read from Entra

### Release Notes

#### Version 2025.07.1 - Released on July 1, 2025
 
##### New Functionality
 - *None in this release*
##### Changed Functionality
 - **Special character handling in Usernames**
   - All special characters are now handled correctly in the usernames. This is the improvement in the previous implementation handling the design gap and cover all the possible cases.
  
----

#### Version 2025.06.1 - Released on June 27, 2025
 
##### New Functionality
 - *None in this release*
##### Changed Functionality
 - **Attach and Remove Role support added for M365 Groups**
   - The previous implementation was restricted to the role assignment and de-assignment with group type "Security Groups" only. This implementation added the same support for M365 groups.
##### Resolved Bugs
 - **Resolved issues with repeating and unexpected crashing of Sync Users job**
   - The issue was found due to nesting of groups. This programmed logic wasn't able to find the UserPrincipalNames in case when the members of the requested group turned out to be groups. This issue was fixed by fixing the returned object type in the APIs to be **microsoft.graph.user**.

----

#### Version 2025.05.1 - Released on May 15, 2025
 
##### New Functionality
 - *None in this release*
##### Changed Functionality
 - **Support added for usernames with special characters**
   - Usernames with special characters like apostrophe (') and hashtag (#) are now supported.
##### Resolved Bugs
 - *None in this release*

----

#### Version 2024.12.1 - Released on December 16, 2024
 
##### New Functionality
 - Initial release of this layer
    - Introduces a filtering mechanism to selectively read users from Microsoft Entra ID.
##### Changed Functionality
 - *None in this release*
##### Resolved Bugs
 - *None in this release*

----

## Additional Notes
- **Documentation Update**: Refer to the Layer 400 README for configuration details and best practices.
- **Known Issues**: No known issues with this release.
