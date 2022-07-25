package ui;

import model.Book;
import model.Reader;

import java.util.Scanner;

public class LoggerApp2 {
    private static final String SIGNUP_COMMAND = "sign up";
    private static final String LOG_BOOK_COMMAND = "log book";
    private static final String VIEW_HISTORY_COMMAND = "view history";
    // TODO: put command constants into print statements

    private Scanner input;
    private Reader reader;
    private Book book;

    // TODO: add model specs

    public LoggerApp2() {
        runLogger();
    }

    private void runLogger() {
        System.out.println("\nWelcome to Bookmark! 🔖💡 \nInput 'sign up' to register for a new account.");
        // TODO: print instructions/description; login
        boolean keepGoing = true;
        String str;

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
                case LOG_BOOK_COMMAND:
                    handleNewEntry();
                    break;
                case VIEW_HISTORY_COMMAND:
                    printHistory();
                    break;
            }
        }
    }

    private void registerNewReader() {
        System.out.println("\nWhat's your name?");
        String name = input.nextLine();
        reader = new Reader(name);
        System.out.println("\nHello, " + reader.getName() + "! 🤓📚"
                + "\nType 'log book' to log a new book into your reading history.");
        // TODO: set reading goal (# of books) for the next year; logout
    }

    private void handleNewEntry() {
        System.out.println("\nEnter the title of the book:");
        String title = input.nextLine();
        System.out.println("\nEnter the author's name:");
        String author = input.nextLine();
        System.out.println("\nEnter a numerical rating on the classic 1-5 ⭐️ scale:");
        int i = input.nextInt();
        // TODO: create GENRE field for book as another category to filter by later
        book = new Book(title, author, i);
        reader.addBook(book);
        System.out.println("\nAwesome! New entry created in " + reader.getName() + "'s history:" + "\n"
                + book.getRating() + " ⭐ | ️" + book.getTitle() + " by " + book.getAuthor()
                + "\nEnter 'log book' to log a new book into your reading history."
                + "\nEnter 'view history' to see your entire reading history.");
    }

    private void printHistory() {
        System.out.println("\n" + reader.getName() + "'s reading history:");
        for (Book b: reader.getHistory()) {
            System.out.println("\t" + b.getRating() + " ⭐ | ️" + b.getTitle() + " by " + b.getAuthor());
        }
        if (reader.getCount() == 1) {
            System.out.println("You've logged " + reader.getCount() + " book so far. Neat :)");
        }
        System.out.println("You've logged " + reader.getCount() + " books so far. Neat :)");
    }

    private void init() {
        input = new Scanner(System.in);
    }
}