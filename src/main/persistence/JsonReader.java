package persistence;

import model.Book;
import model.History;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads history from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads history from file and returns it;
    // throws IOException if an error occurs reading data from file
    public History read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHistory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses history from JSON object and returns it
    private History parseHistory(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        History history = new History(name);
        addBooks(history, jsonObject);
        return history;
    }

    // MODIFIES: history
    // EFFECTS: parses books from JSON object and adds them to history
    private void addBooks(History history, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(history, nextBook);
        }
    }

    // MODIFIES: history
    // EFFECTS: parses book from JSON object and adds it to history
    private void addBook(History history, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        Integer rating = jsonObject.getInt("rating");
        Book thingy = new Book(title, author, rating);
        history.addBook(thingy);
    }
}
