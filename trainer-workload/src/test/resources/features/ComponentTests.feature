@jwtauth
Feature: JWT Authentication and Trainer Summary Management


  Scenario: Valid JWT Token and Training Session Creation
    Given a valid JWT token
    When a training session message is received
    Then the training session should be processed and stored

  Scenario: Invalid JWT Token
    Given an invalid JWT token
    When a training session message is received
    Then the message should be rejected

  Scenario: Training Session Deletion
    Given a valid JWT token
    When a training session deletion message is received
    Then the training session should be deleted