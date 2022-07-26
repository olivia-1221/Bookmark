package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {
    // unit tests for the Reader class

    Reader reader;
    Book b1;
    Book b2;
    Book b3;

    @BeforeEach
    void setUp() {
        reader = new Reader("Olivia");
        b1 = new Book("Pride and Prejudice", "Jane Austen",
                4);
        b2 = new Book("Of Mice and Men", "John Steinbeck",
                5);
        b3 = new Book("Crow Lake", "Mary Lawson",
                5);
    }

    @Test
    void testConstructor() {
        assertEquals("Olivia", reader.getName());
        assertEquals(0, reader.getCount());
        assertEquals(Arrays.asList(), reader.getHistory());
    }

    @Test
    // adding multiple books that aren't in the account's history yet
    void testAddBooks() {
        assertTrue(reader.addBook(b1));
        assertEquals(1, reader.getCount());
        assertEquals(Arrays.asList(b1), reader.getHistory());
        assertTrue(reader.addBook(b2));
        assertEquals(2, reader.getCount());
        assertEquals(Arrays.asList(b1, b2), reader.getHistory());
    }

    @Test
        // adding same book multiple times
    void testAddBookNotOK() {
        assertTrue(reader.addBook(b1));
        assertFalse(reader.addBook(b1));
        assertEquals(1, reader.getCount());
        assertEquals(Arrays.asList(b1), reader.getHistory());
    }

    @Test
    // removing multiple books that are in the account's history
    void testRemoveBooks() {
        reader.addBook(b1);
        reader.addBook(b2);
        reader.addBook(b3);
        assertTrue(reader.removeBook(b1));
        assertEquals(2, reader.getCount());
        assertTrue(reader.removeBook(b3));
        assertEquals(1, reader.getCount());
        assertEquals(Arrays.asList(b2), reader.getHistory());
    }

    @Test
    // removing multiple books that are in the account's history
    void testRemoveBookNotOK() {
        reader.addBook(b1);
        assertFalse(reader.removeBook(b3));
        assertEquals(1, reader.getCount());
        assertEquals(Arrays.asList(b1), reader.getHistory());
    }

    @Test
        // removing multiple books from the account's history
    void testAverageRating() {
        reader.addBook(b1);
        reader.addBook(b2);
        reader.addBook(b3);
        assertEquals((double) (4 + 5 + 5) / 3, reader.averageRating());
    }

    @Test
    void testGetCount() {
        reader.addBook(b1);
        reader.addBook(b2);
        assertEquals(2, reader.getCount());
    }
}
