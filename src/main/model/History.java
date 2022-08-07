package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a reading history having a collection of books
public class History implements Writable {
    private String name;
    private List<Book> books;
    private double ratingSum;

    // EFFECTS: constructs history with a name and empty history
    public History(String name) {
        this.name = name;
        books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds book to this history
    public void addBook(Book book) {
        books.add(book);
    }

    // EFFECTS: returns number of thingies in this history
    public int numBooks() {
        return books.size();
    }

     // MODIFIES: this
     // EFFECTS: deletes given book from the account's reading history if it's there and returns true,
     //          otherwise returns false
    public double averageRating() {
        ratingSum = 0;
        for (Book b : this.getBooks()) {
            this.ratingSum = b.getRating() + this.ratingSum;
        }
        return (this.ratingSum / this.numBooks());
    }

    // REQUIRES: numBooks() > 0
    // EFFECTS: returns the percentage breakdowns of the reader's ratings
    public ArrayList<Double> calculateStarPercentages() {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            ArrayList<Book> booksWithThisRating = filterParticularStars(i);
            int count = booksWithThisRating.size();
            double percentage = (double) count / this.numBooks() * 100;
            result.add(percentage);
        }
        return result;
    }

    // REQUIRES: int > 0
    // EFFECTS: returns all int-star books in the reader's history
    public ArrayList<Book> filterParticularStars(int i) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : this.getBooks()) {
            if (b.getRating() == i) {
                result.add(b);
            }
        }
        return result;
    }

    // EFFECTS: returns an unmodifiable list of books in this history
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", booksToJson());
        return json;
    }

    // EFFECTS: returns things in this reading history as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book t : books) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

