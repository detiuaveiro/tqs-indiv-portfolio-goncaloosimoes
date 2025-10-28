package library;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private String category;
    private LocalDate publishedDate;

    public Book(String title, String author, String category, String published) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.publishedDate = LocalDate.parse(published);
    }

    public Book(String title, String author, LocalDate publishedDate) {
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getCategory() {
        return category;
    }
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    @Override
    public String toString() {
        return String.format("%s by %s (%s)", title, author, category);
    }
}
