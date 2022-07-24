package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    Account account;
    Book b1;
    Book b2;
    Book b3;

    @BeforeEach
    void setUp() {
        account = new Account("Olivia");
        b1 = new Book("Pride and Prejudice", "Jane Austen",
                2004, 12, 16, 4);
        b2 = new Book("Of Mice and Men", "John Steinbeck",
                2017, 07, 25, 5);
        b3 = new Book("Crow Lake", "Mary Lawson",
                1999, 01, 11, 3);
    }

    @Test
    void testConstructor() {
        assertEquals("Olivia", account.getName());
        assertEquals(0, account.getCount());
        assertEquals(Arrays.asList(), account.getHistory());
    }

    @Test
    // adding multiple books that aren't in the account's history yet
    void testAddBooks() {
        assertTrue(account.addBook(b1));
        assertEquals(1, account.getCount());
        assertEquals(Arrays.asList(b1), account.getHistory());
        assertTrue(account.addBook(b2));
        assertEquals(2, account.getCount());
        assertEquals(Arrays.asList(b1, b2), account.getHistory());
    }

    @Test
        // adding same book multiple times
    void testAddBookNotOK() {
        assertTrue(account.addBook(b1));
        assertFalse(account.addBook(b1));
        assertEquals(1, account.getCount());
        assertEquals(Arrays.asList(b1), account.getHistory());
    }

    @Test
    // removing multiple books that are in the account's history
    void testRemoveBooks() {
        account.addBook(b1);
        account.addBook(b2);
        account.addBook(b3);
        assertTrue(account.removeBook(b1));
        assertEquals(2, account.getCount());
        assertTrue(account.removeBook(b3));
        assertEquals(1, account.getCount());
        assertEquals(Arrays.asList(b2), account.getHistory());
    }

    @Test
    // removing multiple books that are in the account's history
    void testRemoveBookNotOK() {
        account.addBook(b1);
        assertFalse(account.removeBook(b3));
        assertEquals(1, account.getCount());
        assertEquals(Arrays.asList(b1), account.getHistory());
    }
}
