Feature: FacebookLogin

Scenario Outline: Login with InValid Credentials

Given User is on facebook Page
When User enter email id  "<username>"
And  User enter Password  "<password>"
Then Message displayed Invalid Credentials

Examples:
|username         |password         |
|Tom              |password1        |
| user2    | password2 |

