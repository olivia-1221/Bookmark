package model;

// Represents a book with a title, author, and rating

import org.json.JSONObject;
import persistence.Writable;

public class Book implements Writable {
    private String title;
    private String author;
    private int rating;

//    // A list of genres a user can shelve a book as (TO BE USED)
//    enum Genre {
//        CLASSIC,
//        FANTASY,
//        SCIENCE_FICTION,
//        ROMANCE,
//        HORROR,
//        MYSTERY
//    }

    /*
     * REQUIRES: title and author have a non-zero length; 0 <= rating <= 5
     * MODIFIES: this
     * EFFECTS: constructs a book with a title, author, and rating
     */
    public Book(String title, String author, int rating) {
        this.title = title;
        this.author = author;
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getRating() {
        return rating;
    }

    @Override
    // Code adapted from JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("rating", rating);
        return json;
    }
}
