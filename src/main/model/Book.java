package model;

// Represents a book with a title, author, and rating

import org.json.JSONObject;
import persistence.Writable;

public class Book implements Writable {
    private String title;   // the book's title
    private String author;  // the book's author
    private int rating;     // the user's rating of the book from 1-5

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
     * REQUIRES: title and author have a non-zero length
     * MODIFIES: this
     * EFFECTS: constructs a book with a title, author, and rating
     */
    public Book(String title, String author, int rating) {
        this.title = title;
        this.author = author;
        this.rating = setRating(rating);
    }

    /*
     * EFFECTS: returns the rating it's been passed if it's within the valid range, otherwise returns -1
     */
    public int setRating(int v) {
        if (v >= 1 && v <= 5) {
            this.rating = v;
            return v;
        } else {
            return -1;
        }
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
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("rating", rating);
        return json;
    }
}
