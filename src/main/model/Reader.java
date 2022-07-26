package model;

// Represents an account having an owner name, reading history, book count, total ratings, and average rating

import java.util.ArrayList;

public class Reader {
    private String name;                        // the reader's owner name
    private int count;                          // the # of books in account's collection
    private ArrayList<Book> history;            // their list of books
    private int goal;                           // their reading goal (isn't in use yet)
    private double ratingSum;                   // the sum of their ratings
    private double averageRating;               // their average rating

    /*
     * REQUIRES: readerName has a non-zero length
     * EFFECTS: constructs an account with a name readerName;
     *          initializes the user's book count as 0;
     */
    public Reader(String readerName) {
        name = readerName;
        history = new ArrayList<Book>();
        count = getCount();
        goal = 0; // This isn't in use yet but will be!
        ratingSum = 0;
        averageRating = averageRating();
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds given book to the account's reading history if it isn't already there and returns true,
     *          otherwise returns false
     */
    public boolean addBook(Book book) {
        if (!this.history.contains(book)) {
            this.history.add(book);
            return true;
        }
        return false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: deletes given book from the account's reading history if it's there and returns true,
     *          otherwise returns false
     */
    public boolean removeBook(Book book) {
        if (this.history.contains(book)) {
            this.history.remove(book);
            return true;
        }
        return false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: deletes given book from the account's reading history if it's there and returns true,
     *          otherwise returns false
     */
    public double averageRating() {
        for (Book b : this.getHistory()) {
            this.ratingSum = b.getRating() + this.ratingSum;
        }
        return (this.ratingSum / this.getCount());
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count = this.history.size();
    }

    public ArrayList<Book> getHistory() {
        return history;
    }

}
