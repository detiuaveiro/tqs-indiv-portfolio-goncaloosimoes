Feature: Book Search Functionality
  As a user of the Cover Bookstore
  I want to be able to search for books by title, author, and publication year
  So that I can find books that match my interests

  Background:
    Given the following books are available in the bookstore:
      | Title                   | Author              | Publication Year |
      | "The Great Gatsby"      | F. Scott Fitzgerald |             1925 |
      | "To Kill a Mockingbird" | Harper Lee          |             1960 |
      | "1984"                  | George Orwell       |             1949 |
      | "Moby-Dick"             | Herman Melville     |             1851 |
      | "War and Peace"         | Leo Tolstoy         |             1869 |

  Scenario: Search for a book by exact title
    When I search for a book titled "1984"
    Then I should see "1984" by George Orwell

  Scenario: Search for books by author
    When I search for books by author "Harper Lee"
    Then I should see "To Kill a Mockingbird" by Harper Lee

  Scenario: Search for books by partial title
    When I search for books with the title containing "War"
    Then I should see "War and Peace" by Leo Tolstoy

  Scenario: Search for books by publication year
    When I search for books published in 1960
    Then I should see "To Kill a Mockingbird" by Harper Lee

  Scenario: Search for books with no matching results
    When I search for books by author "J.K. Rowling"
    Then I should see no books found

  Scenario: Search for books with multiple criteria
    When I search for books by author "George Orwell" published in 1949
    Then I should see "1984" by George Orwell
