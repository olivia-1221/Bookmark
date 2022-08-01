package persistence;

import model.Book;
import model.History;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            History history = new History("My Reading History");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            History history = new History("My Reading History");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHistory.json");
            writer.open();
            writer.write(history);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHistory.json");
            history = reader.read();
            assertEquals("My Reading History", history.getName());
            assertEquals(0, history.numBooks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            History history = new History("My Reading History");
            history.addBook(new Book("Pride and Prejudice", "Jane Austen", 4));
            history.addBook(new Book("Of Mice and Men", "John Steinbeck", 3));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHistory.json");
            writer.open();
            writer.write(history);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHistory.json");
            history = reader.read();
            assertEquals("My Reading History", history.getName());
            List<Book> books = history.getBooks();
            assertEquals(2, books.size());
            checkBook("Pride and Prejudice", "Jane Austen", 4, books.get(0));
            checkBook("Of Mice and Men", "John Steinbeck", 3, books.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}