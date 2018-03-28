Feature: SignUp using Data Tables

  Scenario:
    Given On Facebook page
    When I enter  valid data on the page

    | Fields | Values |
    | First Name | First Name |
    | Sur Name | Kenny  |
    | Mobile Number | 9968056767 |
    | New Password | New Password |


    Then the user registration should be unsuccessful