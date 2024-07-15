Feature: IP Blocking

  Scenario: Blocked IP Handling
    Given an IP address is blocked due to too many unsuccessful login attempts
    When I send a login request from the blocked IP
    Then the request should be rejected with a status of too many requests
