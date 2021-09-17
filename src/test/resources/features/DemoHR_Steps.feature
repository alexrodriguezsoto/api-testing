Feature: Query database demoHR
  @demoHR
  Scenario: postgres testing demoHR departments
    Given user establish connection to dataBase "demoHR"
    Then user executes query "select * from students" and verifies result "Alex"
    Then user executes query "select * from employees limit 1" and verifies column name "first_name" and result value "Mary"
    Then user gets total row count for query "select * from departments"
    And user closes connection to database