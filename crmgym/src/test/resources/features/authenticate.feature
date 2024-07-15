Feature: User Authentication

  Scenario Outline: Successful Login
    Given the system has a user with username "<username>" and password "<password>"
    When I send a login request with username "<username>" and password "<password>"
    Then the user should be authenticated successfully


    Examples:
      | username | password |
      | arnold.alexander1    | DxqWfp31r5    |
      | arnold.alexander2    | Mh59zXGR6p    |

  Scenario Outline: Unsuccessful Login with Blocking
    Given the system has a user with username "<username>" and password "<password>"
    When I send an invalid login request with username "<username>" and password "<invalidPassword>" three times from the same IP
    Then the IP should be blocked
    And further login attempts from that IP should be rejected

    Examples:
      | username | password | invalidPassword |
      | user1    | pass1    | wrongPass       |
      | user2    | pass2    | incorrectPass   |


