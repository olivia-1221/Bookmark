package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {
    // unit tests for the History class

    History history;
    Book b1;
    Book b2;
    Book b3;

    @BeforeEach
    void setUp() {
        history = new History("Olivia's History");
        b1 = new Book("Pride and Prejudice", "Jane Austen",
                4);
        b2 = new Book("Of Mice and Men", "John Steinbeck",
                5);
        b3 = new Book("Crow Lake", "Mary Lawson",
                5);
    }

    @Test
    void testConstructor() {
        assertEquals("Olivia's History", history.getName());
        assertEquals(0, history.numBooks());
    }

    @Test
    void testAddBook() {
        history.addBook(b1);
        assertEquals(1, history.getBooks().size());
        assertEquals(b1, history.getBooks().get(0));
    }

    @Test
    void testAddBooks() {
        history.addBook(b1);
        history.addBook(b2);
        assertEquals(2, history.getBooks().size());
        assertEquals(b1, history.getBooks().get(0));
        assertEquals(b2, history.getBooks().get(1));
    }

    @Test
    void testAverageRating() {
        history.addBook(b1);
        history.addBook(b2);
        history.addBook(b3);
        assertEquals((double) (4 + 5 + 5) / 3, history.averageRating());
    }

    @Test
    void testNumBooks() {
        history.addBook(b1);
        history.addBook(b2);
        assertEquals(2, history.numBooks());
    }

    @Test
    void testCalculateStarPercentages() {
        history.addBook(b1);
        history.addBook(b2);
        history.addBook(b3);
        List<Double> starPercentages = history.calculateStarPercentages();
        assertEquals(5, starPercentages.size());
        assertEquals(0.0, starPercentages.get(0));
        assertEquals(0.0, starPercentages.get(1));
        assertEquals(0.0, starPercentages.get(2));
        assertEquals((double) 1/3 * 100, starPercentages.get(3));
        assertEquals((double) 2/3 * 100, starPercentages.get(4));
    }

    @Test
    void testFilterParticularStars() {
        List<Book> nothingThere = history.filterParticularStars(1);
        assertEquals(0, nothingThere.size());
        history.addBook(b1);
        history.addBook(b2);
        history.addBook(b3);
        List<Book> filterFiveStars = history.filterParticularStars(5);
        assertEquals(2, filterFiveStars.size());
        assertEquals(b2, filterFiveStars.get(0));
        assertEquals(b3, filterFiveStars.get(1));
    }
}
