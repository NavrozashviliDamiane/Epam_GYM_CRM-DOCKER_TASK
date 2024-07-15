Feature: Trainer Management
  Scenario Outline: Trainer creation and retrieval
    Given the system has no trainer with username "<trainerUsername>"
    When I create a trainer with first name "<firstName>", last name "<lastName>", username "<trainerUsername>", active status "<isActive>", training date "<trainingDate>", training duration "<trainingDuration>", and action type "<actionType>"



    Examples:
      | trainerUsername | firstName | lastName | isActive | trainingDate | trainingDuration | actionType |
      | trainer1        | John      | Doe      | true     | 2024-06-15   | 60               | ADD        |
      | trainer2        | Jane      | Smith    | true     | 2024-06-16   | 45               | ADD        |

  Scenario Outline: Delete trainer
    Given the system has a trainer with username "<trainerUsername>"
    When I delete the trainer with username "<trainerUsername>"
    Then the trainer should be deleted successfully

    Examples:
      | trainerUsername |
      | trainer1        |
      | trainer2        |

  Scenario: Create trainer with invalid details
    When I create a trainer with invalid details
    Then the trainer creation should fail