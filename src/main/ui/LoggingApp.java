package ui;

import model.Book;
import model.Genre;
import model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

public class LoggingApp {
    private static final String SIGNUP_COMMAND = "sign up";
    private static final String LOGIN_COMMAND = "login";
    private static final String LOG_BOOK_COMMAND = "log";
    private static final String VIEW_HISTORY_COMMAND = "view";
    private static final String VIEW_FILTERED_HISTORY_COMMAND = "filter";

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private Scanner input;
    private Reader reader;
    private Book book;
    private List<Reader> readerList;

    // TODO: specs

    public LoggingApp() {
        runLogger();
    }

    private void runLogger() {
        System.out.println("\n🔖💡 Welcome to Bookmark! \nType '" + SIGNUP_COMMAND + "'"
                + " to register a new account. Type '" + LOGIN_COMMAND + "' to login to an existing one.");
        // TODO: print instructions/description; login; be able to quit or return to main menu
        // TODO: single point of control for rating emoji; separate method for above
        // TODO: avoid duplication
        boolean keepGoing = true;
        String str;
        readerList = new ArrayList<>();

        init();

        while (keepGoing) {
            if (input.hasNext()) {
                str = input.nextLine();
                parseInput(str);
            }
        }
    }

    private void parseInput(String str) {
        if (str.length() > 0) {
            switch (str) {
                case SIGNUP_COMMAND:
                    registerNewReader();
                    break;
                case LOGIN_COMMAND:
                    logIn();
                    break;
                case LOG_BOOK_COMMAND:
                    handleNewEntry();
                    break;
                case VIEW_HISTORY_COMMAND:
                    printHistory();
                    break;
                case VIEW_FILTERED_HISTORY_COMMAND:
                    displaySelectHistoryMenu();
                    viewSelectHistory(input.nextInt());
                    break;
            }
        }
    }

    private void registerNewReader() {
        System.out.println("\nWhat's your name?");
        String name = input.nextLine();
        reader = new Reader(name);
        readerList.add(reader);
        System.out.println("\nHello, " + reader.getName() + " 📚"
                + "\nType " + "'" + LOG_BOOK_COMMAND + "'" + " to log a new book.");
        // TODO: set reading goal (# of books) for the next year; logout/quit
        // TODO: make sure name isn't already in list, else print 'initials instead etc.'
    }

    private void logIn() {
        System.out.println("\nPlease enter the name you signed up with:");
        String name = input.nextLine();
        List<String> names =
                readerList.stream()
                        .map(Reader::getName)
                        .collect(Collectors.toList());
        if (names.contains(name)) {
            System.out.println("\nHi again, " + name + "!");
        } else {
            System.out.println("\nHmm... it doesn't seem like you're registered. To register, enter your ");
            //TODO: add more to the string
        }
    }

    private void handleNewEntry() {
        System.out.println("\nThe book's title:");
        String title = input.nextLine();
        System.out.println("\nThe author's name:");
        String author = input.nextLine();
        System.out.println("\nEnter a numerical rating on the classic 1-5 ⭐️ scale:");
        int i = input.nextInt();
        // TODO: create GENRE field for book as another category to filter by later
        book = new Book(title, author, i);
        reader.addBook(book);
        System.out.println("\nAwesome! New entry created in " + reader.getName() + "'s history:" + "\n"
                + book.getRating() + " ⭐ | ️" + book.getTitle() + " by " + book.getAuthor());
        System.out.println("\nEnter '" + LOG_BOOK_COMMAND + "' to log a new book."
                + "\nEnter '" + VIEW_HISTORY_COMMAND + "' to view your reading history."
                + "\nEnter '" + VIEW_FILTERED_HISTORY_COMMAND + "' to filter your history according to a certain "
                + "star rating.");
        // TODO: last line above
        // TODO: summary statistics
    }

    private void printHistory() {
        System.out.println("\n" + reader.getName() + "'s reading history:");
        for (Book b : reader.getHistory()) {
            System.out.println("\t" + b.getRating() + " ⭐ | ️" + b.getTitle() + " by " + b.getAuthor());
        }
        if (reader.getCount() == 1) {
            System.out.println("You've logged " + reader.getCount() + " book so far and your average rating is ~"
                    + df.format(reader.averageRating()) + " ⭐.");
        } else {
            System.out.println("You've logged " + reader.getCount() + " books so far and your average rating is ~"
                    + df.format(reader.averageRating()) + " ⭐.");
        }
        System.out.println("\nEnter '" + LOG_BOOK_COMMAND + "' to log a new book."
                + "\nEnter '" + VIEW_FILTERED_HISTORY_COMMAND + "' to filter your history by a certain"
                + " star rating."
                + "\nEnter '" + LOGIN_COMMAND + "' to login to a different account."
                + "\nEnter '" + SIGNUP_COMMAND + "' to register a new name.");
        //TODO: direct method to filter
        //TODO: add option to register new account
    }

    private void viewSelectHistory(int i) {
        System.out.println("\nAll your " + i + " ⭐ books:");
        ArrayList result = new ArrayList<Book>();
        for (Book b : reader.getHistory()) {
            if (b.getRating() == i) {
                result.add(b);
                System.out.println("\t" + b.getTitle() + " by " + b.getAuthor());
            }
        } //TODO: view according to another star count, view full history
    }

    private void displaySelectHistoryMenu() {
        System.out.println("\nEnter as an integer your desired number of ⭐s.");
    }

    private void displayGenres() {
        for (Genre myVar : Genre.values()) {
            System.out.println(myVar);
        }
    }

    private void init() {
        input = new Scanner(System.in);
    }
}