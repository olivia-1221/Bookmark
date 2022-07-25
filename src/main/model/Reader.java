package model;

// Represents an account having an owner name, book count, and average rating

import java.util.ArrayList;
import java.util.List;

// TODO: field, method specs

public class Reader {
    private String name;                        // the reader's owner name
    private int count;                          // the # of books in account's collection
    private ArrayList<Book> history;            // the reader's list of books
    private int goal;
    private double ratingSum;

    /*
     * REQUIRES: readerName has a non-zero length
     * EFFECTS: constructs an account with a name accountName;
     *          initializes the user's book count as 0;
     */
    public Reader(String accountName) {
        name = accountName;
        history = new ArrayList<Book>();
        count = getCount();
        goal = 0; //TODO: use
        ratingSum = 0;
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

    // TODO: spec
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
