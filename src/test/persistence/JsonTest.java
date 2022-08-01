package persistence;

import model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    // Code adapted from JsonSerializationDemo

    protected void checkBook(String title, String author, Integer rating, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(rating, book.getRating());
    }
}
