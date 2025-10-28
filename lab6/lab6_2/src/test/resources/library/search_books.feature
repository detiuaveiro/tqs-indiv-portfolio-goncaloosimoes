Feature: Search books in the library

  Background:
    Given a library with the following books
      | title                               | author         | category        | published  |
      | Harry Potter and the Goblet of Fire | J.K. Rowling   | Fantasy         | 2000-07-08 |
      | The Hobbit                          | J.R.R. Tolkien | Fantasy         | 1937-09-21 |
      | Dune                                | Frank Herbert  | Science Fiction | 1965-08-01 |
      | Foundation                          | Isaac Asimov   | Science Fiction | 1951-06-01 |

  Scenario: Search for a book by author
    When I search for books by "J.K. Rowling"
    Then I should see a list of books written by "J.K. Rowling"

  Scenario: Search for a book by category
    When I search for books in the "Science Fiction" category
    Then I should see a list of books in the "Science Fiction" category

  Scenario: No results found
    When I search for books by "Unknown Author"
    Then I should see a message indicating no results were found

  Scenario: Search for books by date range
    When I search for books published between "1960-01-01" and "1970-12-31"
    Then I should see a list of books published between those dates
