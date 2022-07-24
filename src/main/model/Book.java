package model;

// Represents a book with a title, author, date completed, and rating

import java.util.Date;

public class Book {
    private String title;   // the book's title
    private String author;  // the book's author
    private Date date;      // the date the user completed the book
    private int rating;     // the user's rating of the book from 1-5

    /*
     * REQUIRES: title and author have a non-zero length; year, month, and date are valid
     * EFFECTS: constructs a book with a title and author
     */
    public Book(String title, String author, int year, int month, int date, int rating) {
        this.title = title;
        this.author = author;
        this.date = new Date(year, month, date);
        this.rating = rating;
    }

    // TODO: method specs for editors
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(int year, int month, int date) {
        this.date = new Date(year, month, date);
    }

    public boolean setRating(int rating) {
        if ((rating == 1) || (rating == 2) || (rating == 3) || (rating == 4) || (rating == 5)) {
            this.rating = rating;
            return true;
        } else {
            return false;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }
}
