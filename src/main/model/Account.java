package model;

// Represents an account having an owner name, book count, and average rating

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.lang.Integer;

public class Account {
    private String name;                        // the account owner name
    private int count;                          // the # of books in account's collection
    private double averageRating;               // the account's average book rating
    private ArrayList<Book> history;            // the account's list of books

    /*
     * REQUIRES: accountName has a non-zero length
     * EFFECTS: constructs an account with a name accountName;
     *          initializes the user's book count as 0;
     */
    public Account(String accountName) {
        name = accountName;
        history = new ArrayList<Book>();
        count = getCount();
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds given book to the account's reading history if it isn't already there and returns true,
     *          otherwise returns false
     */
    public boolean addBook(Book book) {
        if (!history.contains(book)) {
            history.add(book);
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
        if (history.contains(book)) {
            history.remove(book);
            return true;
        }
        return false;
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
