Feature: Manage Training Sessions

  Scenario Outline: Add a new training session
    Given a training request with the following details:
      | Username       | <username>     |
      | TrainerUsername| <trainerUsername> |
      | TrainingName   | <trainingName> |
      | TrainingDate   | <trainingDate> |
      | TrainingDuration | <trainingDuration> |
    When I add the training
    Then the training should be added successfully

    Examples:
      | username   | trainerUsername | trainingName | trainingDate  | trainingDuration |
      | user1      | trainer1        | Training 1   | 2024-06-15    | 60               |
      | user2      | trainer2        | Training 2   | 2024-06-16    | 90               |
