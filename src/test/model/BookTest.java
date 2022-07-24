package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book b1;
    Book b2;
    Book b3;

    @BeforeEach
    void setUp() {
        b1 = new Book("Pride and Prejudice", "Jane Austen",
                2004, 12, 16, 4);
        b2 = new Book("Of Mice and Men", "John Steinbeck",
                2017, 07, 25, 5);
        b3 = new Book("Crow Lake", "Mary Lawson",
                1999, 01, 11, 3);
    }

    @Test
    void testConstructor() {
        assertEquals("Pride and Prejudice", b1.getTitle());
        assertEquals("Jane Austen", b1.getAuthor());
        assertEquals(4, b1.getRating());
    }

    @Test
    void testSetRatingOK() {
        assertTrue(b2.setRating(1));
        assertEquals(1, b2.getRating());
    }

    @Test
    // rating isn't 1, 2, 3, 4, or 5
    void testSetRatingNotOK() {
        assertFalse(b3.setRating(9));
    }
}