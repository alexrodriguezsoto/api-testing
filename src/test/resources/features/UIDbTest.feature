@test1
Feature: end to end test

  Scenario: Search project
    Given I open "tla"
    Given I search for "sql"
    When I verify "sql" should be displayed
    And there should be result for "sql"