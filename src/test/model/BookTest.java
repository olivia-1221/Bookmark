package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book b1;
    Book b2;
    Book b3;

    @BeforeEach
    void setUp() {
        b1 = new Book("Pride and Prejudice", "Jane Austen",
                4);
        b2 = new Book("Of Mice and Men", "John Steinbeck",
                5);
        b3 = new Book("Crow Lake", "Mary Lawson",
                3);
    }

    @Test
    void testConstructor() {
        assertEquals("Pride and Prejudice", b1.getTitle());
        assertEquals("Jane Austen", b1.getAuthor());
        assertEquals(4, b1.getRating());
    }

    @Test
    void testSetRatingOK() {
        assertEquals(1, b2.setRating(1));
    }

    @Test
    // rating isn't 1, 2, 3, 4, or 5
    void testSetRatingNotOK() {
        assertEquals(-1, b3.setRating(9));
    }
}