package library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> store;
    
    public Library() {
        store = new ArrayList<>();
    }

    public void addBook(Book book) {
        store.add(book);
    }

    public List<Book> getAllBooks() {
        return store;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : store) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findBooksInCategory(String category) {
        return store.stream()
                .filter(book -> category.equalsIgnoreCase(book.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        for (Book book : store) {
            System.out.println(book.toString());
        }
        return "";
    }

    public List<Book> findBooksBetween(LocalDate start, LocalDate end) {
        return store.stream()
                .filter(book -> !book.getPublishedDate().isBefore(start) && !book.getPublishedDate().isAfter(end))
                .collect(Collectors.toList());
    }
}
