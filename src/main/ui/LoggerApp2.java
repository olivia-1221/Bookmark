package ui;

import model.Book;
import model.Reader;

import java.util.Scanner;

public class LoggerApp2 {
    private static final String SIGNUP_COMMAND = "sign up";

    private Scanner input;
    private Reader reader;
    private Book book;

    // TODO: add model specs

    public LoggerApp2() {
        runLogger();
    }

    private void runLogger() {
        System.out.println("\nWelcome to Bookmark! 🔖💡");
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            command = input.next();

            if (command.equals("q")) {
                System.out.println("\nThank you for using Bookmark!");
                keepGoing = false;
            } else {
                handleNewEntry();
            }
        }
    }

    private void processCommand(String command) {
        if (command.equals("c")) {
            registerNewReader();
        } else if (command.equals("b")) {
            handleNewEntry();
//        } else if (command.equals("t")) {
//            doTransfer();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void registerNewReader() {
        String name = input.nextLine();
        reader = new Reader(name);
        System.out.println("\nHello, " + reader.getName() + "! Honored to have you here 📚"
                + "\nType 'b' to log a new book into your reading history or 'q' to log out");
    }

    private void handleNewEntry() {
        System.out.println("\nPlease enter the title of the book:");
        String title = input.nextLine();
        System.out.println("\nPlease enter the author's name:");
        String author = input.nextLine();
        System.out.println("\nPlease enter a numerical rating according to the classic 1-5 ⭐️ scale:");
        int i = input.nextInt();
        book = new Book(title, author, i);
        reader.addBook(book);
        System.out.println("\nAwesome! New entry created in your reading history: " + "\n" + book.getRating()
                + " ⭐ | ️" + book.getTitle() + " by " + book.getAuthor());
    }

    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

//    private void displayMenu1() {
//        System.out.println("\nWelcome to Bookmark! Please enter the title of the book you want to log:");
//    }

}
