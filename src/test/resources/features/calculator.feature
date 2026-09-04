Feature: Calculator

  Scenario: add two numbers
    Given Two input values, 6 and 2
    When I select the "+" operation
    Then I expect the result 8

  Scenario: multiply two numbers
    Given Two input values, 6 and 2
    When I select the "*" operation
    Then I expect the result 12

  Scenario: divide two numbers
    Given Two input values, 6 and 2
    When I select the "/" operation
    Then I expect the result 3

  Scenario: raise a number to a power
    Given Two input values, 6 and 2
    When I select the "^" operation
    Then I expect the result 36

  Scenario: reject division by zero
    Given Two input values, 6 and 0
    When I try to divide the two values
    Then I receive a division by zero error

  Scenario Outline: calculate with an operator
    Given Two input values, <first> and <second>
    When I select the "<opt>" operation
    Then I expect the result <result>

    Examples:
      | first | second | opt | result |
      | 6     | 2      | +   | 8      |
      | 6     | 2      | *   | 12     |
      | 6     | 2      | /   | 3      |
      | 6     | 2      | ^   | 36     |
