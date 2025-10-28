package library;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;

public class StepDefinitions {
    private Library library;
    private List<Book> results;

    // Define a new parameter type to interpret dates in ISO-8601 format (yyyy-MM-dd)
    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day) {
        return LocalDate.of(
            Integer.parseInt(year), 
            Integer.parseInt(month), 
            Integer.parseInt(day)
            );
    }

    @Given("a library with the following books")
    public void a_library_with_the_following_books(io.cucumber.datatable.DataTable dataTable) {
        library = new Library();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            library.addBook(new Book(
                    row.get("title"),
                    row.get("author"),
                    row.get("category"),
                    row.get("published")
            ));
        }
    }

    @When("I search for books by {string}")
    public void i_search_for_books_by(String author) {
        results = library.findBooksByAuthor(author);
    }

    @When("I search for books in the {string} category")
    public void i_search_for_books_in_the_category(String category) {
        results = library.findBooksInCategory(category);
    }

    @Then("I should see a list of books written by {string}")
    public void i_should_see_a_list_of_books_written_by(String author) {
        assertFalse(results.isEmpty(), "No books found for " + author);
        assertTrue(results.stream().allMatch(b -> b.getAuthor().equalsIgnoreCase(author)));
    }

    @Then("I should see a list of books in the {string} category")
    public void i_should_see_a_list_of_books_in_the_category(String category) {
        for (Book book : results) {
            assert book.getCategory().equalsIgnoreCase(category);
        }
    }

    @Then("I should see a message indicating no results were found")
    public void i_should_see_a_message_indicating_no_results_were_found() {
        assert results.isEmpty();
    }

    // @When("I search for books published between {iso8601Date} and {iso8601Date}")
    // public void i_search_for_books_published_between(LocalDate start, LocalDate end) {
    //     results = library.findBooksBetween(start, end);
    // }

    @When("I search for books published between {string} and {string}")
    public void i_search_for_books_published_between_and(String string, String string2) {
        results = library.findBooksBetween(LocalDate.parse(string), LocalDate.parse(string2));
    }

    @Then("I should see a list of books published between those dates")
    public void i_should_see_a_list_of_books_published_between_those_dates() {
        assertFalse(results.isEmpty(), "No books found in the specified date range");
        for (Book book : results) {
            LocalDate date = book.getPublishedDate();
            assertTrue(date.isAfter(LocalDate.of(1960, 1, 1).minusDays(1)) &&
                       date.isBefore(LocalDate.of(1970, 12, 31).plusDays(1)),
                       "Book " + book.getTitle() + " is outside the range");
        }
    }
}
