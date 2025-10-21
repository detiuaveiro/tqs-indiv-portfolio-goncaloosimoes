Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I add 4 and 5
    Then the result is 9

  Scenario: Substraction
    When I subtract 7 to 2
    Then the result is 5

  Scenario: Multiplication
    When I multiply 3 and 4
    Then the result is 12

  Scenario: Division
    When I divide 8 by 2
    Then the result is 4

  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>

    Examples: Single digits
      | a  | b | c  |
      |  1 | 2 |  3 |
      |  3 | 7 | 10 |
      | 12 | 2 | 14 |
      |  1 | 1 |  2 |
      |  6 | 3 |  9 |

  Scenario Outline: Several multiplications
    When I multiply <a> and <b>
    Then the result is <c>

    Examples: Single digits
      | a  | b | c  |
      |  1 | 1 |  1 |
      |  2 | 4 |  8 |
      | 10 | 8 | 80 |
      | 12 | 2 | 24 |
      |  6 | 3 | 18 |

  Scenario: Division by zero
    When I divide 5 by 0
    Then an error is raised indicating division by zero