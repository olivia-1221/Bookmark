package model;

// Represents a book with a title, author, and rating

public class Book {
    private String title;   // the book's title
    private String author;  // the book's author
    private int rating;     // the user's rating of the book from 1-5

    /*
     * REQUIRES: title and author have a non-zero length; year, month, and date are valid
     * EFFECTS: constructs a book with a title and author
     */
    public Book(String title, String author, int rating) {
        this.title = title;
        this.author = author;
        this.rating = setRating(rating);
    }

    // TODO: make use of invalid value (error message)
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

    public double getRating() {
        return rating;
    }
}
