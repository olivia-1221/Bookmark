package model;

// Represents a book with a title, author, and rating

public class Book {
    private String title;   // the book's title
    private String author;  // the book's author
    private int rating;     // the user's rating of the book from 1-5

    // A list of genres a user can shelve a book as
    enum Genre {
        CLASSIC,
        FANTASY,
        SCIENCE_FICTION,
        ROMANCE,
        HORROR,
        MYSTERY
    }

    /*
     * REQUIRES: title and author have a non-zero length; year, month, and date are valid
     * EFFECTS: constructs a book with a title and author
     */
    public Book(String title, String author, int rating) {
        this.title = title;
        this.author = author;
        this.rating = setRating(rating);
    }

    // TODO: use invalid value (error message)
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
