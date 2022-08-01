package ui;

import model.Book;
import model.History;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;

// Book logging application
public class BookLoggerApp {
    private static final String JSON_STORE = "./data/history.json";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    // https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/#decimalformat000
    private Scanner input;
    private History history;
    private Book book;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String LOG_BOOK_COMMAND = "1";
    private static final String VIEW_HISTORY_COMMAND = "2";
    private static final String VIEW_STATISTICS_COMMAND = "3";
    private static final String SAVE_COMMAND = "4";
    private static final String LOAD_COMMAND = "5";

    // EFFECTS: runs the logger application
    public BookLoggerApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        history = new History("My Reading History");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLogger();
    }

    // MODIFIES: this
    // EFFECTS: displays intro. screen and processes user input
    // Code adapted from FitLifeGymKiosk on edX
    private void runLogger() {
        displayIntroduction();
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        while (keepGoing) {
            if (input.hasNext()) {
                command = input.nextLine();
                parseInput(command);
            }
        }
    }

    // EFFECTS: prints menu options and info depending on input str
    // Code adapted from FitLifeGymKiosk on edX
    private void parseInput(String str) {
        if (str.length() > 0) {
            switch (str) {
                case LOG_BOOK_COMMAND:
                    newEntry();
                    break;
                case VIEW_HISTORY_COMMAND:
                    printHistory();
                    break;
                case VIEW_STATISTICS_COMMAND:
                    viewStatistics();
                    break;
                case SAVE_COMMAND:
                    saveHistory();
                    break;
                case LOAD_COMMAND:
                    loadHistory();
                    break;
            }
        }
    }

    /*
     * REQUIRES: user inputs for title and author have a non-zero length; 1 <= user input for rating <= 5
     * MODIFIES: this
     * EFFECTS: creates a new book in the user's history with given title, author, and rating
     */
    private void newEntry() {
        System.out.println("\n🐝 Title?");
        String t = input.nextLine();
        System.out.println("\n🍀 Author?");
        String a = input.nextLine();
        System.out.println("\nEnter a rating on the classic 1-5 ⭐️ scale:");
        int i = input.nextInt();
        book = new Book(t, a, i);
        history.addBook(book);
        System.out.println("\nAwesome! New entry created:" + "\n"
                + book.getRating() + " ⭐ | ️" + book.getTitle() + " by " + book.getAuthor());
        displayGeneralMenu();
    }

    // EFFECTS: prints user's history (books, ratings)
    private void printHistory() {
        System.out.println("\nYour reading history:");
        for (Book b : history.getBooks()) {
            System.out.println("\t" + b.getRating() + " ⭐ | ️" + b.getTitle() + " by " + b.getAuthor());
        }
        displayGeneralMenu();
    }

    // EFFECTS: prints user's stats (# books read, average rating, % breakdown of ratings)
    private void viewStatistics() {
        if (history.numBooks() == 1) {
            System.out.println("\nYou've logged " + history.numBooks() + " book so far and your average rating is ~"
                    + df.format(history.averageRating()) + ".");
        } else {
            System.out.println("\nYou've logged " + history.numBooks() + " books so far and your average rating is ~"
                    + df.format(history.averageRating()) + ".");
        }

        System.out.println("A breakdown of your ratings:");
        ArrayList<String> list = new ArrayList<>();
        for (Double d : history.calculateStarPercentages()) {
            String phrase = d.toString() + "%";
            list.add(phrase);
        }

        for (int i = 0; i <= 4; i++) {
            viewStarPercentages(i, list);
        }

        displayGeneralMenu();
    }

     // REQUIRES: int is >= 0
     // EFFECTS: prints the percentage breakdowns of the user's ratings
    private void viewStarPercentages(int i, ArrayList<String> percentageList) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("\t1 ⭐ ");
        list.add("\t2 ⭐️ ");
        list.add("\t3 ⭐️ ");
        list.add("\t4 ⭐ ");
        list.add("\t5 ⭐ ");
        System.out.println(list.get(i).concat(percentageList.get(i)));
    }

    // EFFECTS: displays intro message and menu
    private void displayIntroduction() {
        System.out.println("\n🔖💡 Welcome to Bookmark!");
        displayGeneralMenu();
    }

    // EFFECTS: displays options
    private void displayGeneralMenu() {
        System.out.println("\nSelect from:"
                + "\n\t" + LOG_BOOK_COMMAND + " -> log new book"
                + "\n\t" + VIEW_HISTORY_COMMAND + " -> view reading history"
                + "\n\t" + VIEW_STATISTICS_COMMAND + " -> view statistics"
                + "\n\t" + SAVE_COMMAND + " -> save history to file"
                + "\n\t" + LOAD_COMMAND + " -> load history from file");
    }

    // EFFECTS: saves history to file
    // Code adapted from JsonSerializationDemo
    private void saveHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(history);
            jsonWriter.close();
            System.out.println("\nSaved " + history.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("\nUnable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads history from file
    // Code adapted from JsonSerializationDemo
    private void loadHistory() {
        try {
            history = jsonReader.read();
            System.out.println("\nLoaded " + history.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("\nUnable to read from file: " + JSON_STORE);
        }
    }
}