package persistence;

import model.Book;
import model.History;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    // Code adapted from JsonSerializationDemo

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            History history = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHistory.json");
        try {
            History history = reader.read();
            assertEquals("My Reading History", history.getName());
            assertEquals(0, history.numBooks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHistory.json");
        try {
            History history = reader.read();
            assertEquals("My Reading History", history.getName());
            List<Book> books = history.getBooks();
            assertEquals(2, books.size());
            checkBook("1984", "George Orwell", 2, books.get(0));
            checkBook("Crow Lake", "Mary Lawson", 5, books.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}