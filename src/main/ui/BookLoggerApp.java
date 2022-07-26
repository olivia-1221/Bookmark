package ui;

import model.Book;
import model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

// Book logging application
public class BookLoggerApp {
    private static final String SIGNUP_COMMAND = "sign up";
    private static final String LOGIN_COMMAND = "login";
    private static final String LOG_BOOK_COMMAND = "log";
    private static final String VIEW_HISTORY_COMMAND = "view";
    private static final String VIEW_FILTERED_HISTORY_COMMAND = "filter";
    private static final String VIEW_STATISTICS_COMMAND = "stats";

    private static final DecimalFormat df = new DecimalFormat("0.00");
    // All uses of DecimalFormat adapted from
    // https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/#decimalformat000
    private Scanner input;
    private Reader reader;
    private Book book;
    private List<Reader> readerList;

    /*
     * EFFECTS: runs the logger application
     */
    public BookLoggerApp() {
        runLogger();
    }

    /*
     * MODIFIES: this
     * EFFECTS: displays intro. screen and processes user input
     */
    // Code adapted from FitLifeGymKiosk (edX practice problem)
    private void runLogger() {
        displayIntroduction();
        boolean keepGoing = true;
        String str;
        readerList = new ArrayList<>();

        input = new Scanner(System.in);

        while (keepGoing) {
            if (input.hasNext()) {
                str = input.nextLine();
                parseInput(str);
            }
        }
    }

    /*
     * EFFECTS: prints menu options and info depending on input str
     */
    // Code adapted from FitLifeGymKiosk (edX practice problem)
    private void parseInput(String str) {
        if (str.length() > 0) {
            switch (str) {
                case SIGNUP_COMMAND:
                    registerReader();
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
                case VIEW_STATISTICS_COMMAND:
                    viewStatistics();
            }
        }
    }

    /*
     * MODIFIES: readerList
     * EFFECTS: (if the name is available) creates a new Reader with the input name and adds the given name to
     * readerList; else prompts user to try a different name
     */
    private void registerReader() {
        System.out.println("\nWhat's your name?");
        String name = input.nextLine();
        if (!getReaderNames().contains(name)) {
            reader = new Reader(name);
            readerList.add(reader);
            System.out.println("\nHello, " + reader.getName() + " 📚"
                    + "\nType " + "'" + LOG_BOOK_COMMAND + "' to log a new book.");
        } else {
            System.out.println("\nUnfortunately that name isn't available — try adding a few numbers to the end 🔢");
        }
    }

    /*
     * EFFECTS: logs in with given username if it's in readerList, else prompts the user to register
     */
    private void logIn() {
        System.out.println("\nPlease enter the name you registered with:");
        String name = input.nextLine();
        if (getReaderNames().contains(name)) {
            System.out.println("\nHello again, " + name + " 📚");
            displayGeneralMenu();
        } else {
            System.out.println("\nHmm.. it doesn't seem like you're registered. To sign up, enter '"
                    + SIGNUP_COMMAND + "'.");
        }
    }

    /*
     * EFFECTS: returns the names of all registered readers
     */
    private List<String> getReaderNames() {
        List<String> names =
                readerList.stream()
                        .map(Reader::getName)
                        .collect(Collectors.toList());
        // Code adapted from
        // https://www.codegrepper.com/code-examples/java/java+stream+get+list+of+one+field
        return names;
    }

    /*
     * REQUIRES: user inputs for title and author have a non-zero length; 1 <= user input for rating <= 5
     * MODIFIES: the reader's history
     * EFFECTS: creates a new book in the user's history with given title, author, and rating
     */
    private void handleNewEntry() {
        System.out.println("\nThe book's title:");
        String title = input.nextLine();
        System.out.println("\nThe author's name:");
        String author = input.nextLine();
        System.out.println("\nEnter a numerical rating on the classic 1-5 ⭐️ scale:");
        int i = input.nextInt();
        book = new Book(title, author, i);
        reader.addBook(book);
        System.out.println("\nAwesome! New entry created in " + reader.getName() + "'s history:" + "\n"
                + book.getRating() + " ⭐ | ️" + book.getTitle() + " by " + book.getAuthor());
        displayGeneralMenu();
    }

    /*
     * EFFECTS: prints the reader's entire history (books and ratings)
     */
    private void printHistory() {
        System.out.println("\n🤓 " + reader.getName() + "'s reading history:");
        for (Book b : reader.getHistory()) {
            System.out.println("\t" + b.getRating() + " ⭐ | ️" + b.getTitle() + " by " + b.getAuthor());
        }
    }

    /*
     * REQUIRES: 0 < int <= 5
     * EFFECTS: prints the reader's history filtered according to a chosen # of stars
     */
    private void viewSelectHistory(int i) {
        System.out.println("\nAll your " + i + " ⭐ books:");
        for (Book b : reader.getHistory()) {
            if (b.getRating() == i) {
                System.out.println("\t" + b.getTitle() + " by " + b.getAuthor());
            }
        }
        displayGeneralMenu();
    }

    /*
     * EFFECTS: prints the reader's basic stats (# books read, average rating, % breakdown of ratings)
     */
    private void viewStatistics() {
        if (reader.getCount() == 1) {
            System.out.println("\nYou've logged " + reader.getCount() + " book so far and your average rating is ~"
                    + df.format(reader.averageRating()) + ".");
        } else {
            System.out.println("\nYou've logged " + reader.getCount() + " books so far and your average rating is ~"
                    + df.format(reader.averageRating()) + ".");
        }

        System.out.println("A breakdown of your ratings:");
        ArrayList<String> list = new ArrayList<>();
        for (Double d : calculateStarPercentages()) {
            String phrase = d.toString() + "%";
            list.add(phrase);
        }

        for (int i = 0; i <= 4; i++) {
            viewStarPercentages(i, list);
        }
    }

    /*
     * REQUIRES: int is >= 0
     * EFFECTS: prints the percentage breakdowns of the reader's ratings
     */
    private void viewStarPercentages(int i, ArrayList<String> percentageList) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("\t1 ⭐ ");
        list.add("\t2 ⭐️ ");
        list.add("\t3 ⭐️ ");
        list.add("\t4 ⭐ ");
        list.add("\t5 ⭐ ");
        System.out.println(list.get(i).concat(percentageList.get(i)));
    }

    /*
     * EFFECTS: returns the percentage breakdowns of the reader's ratings
     */
    private ArrayList<Double> calculateStarPercentages() {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            ArrayList<Book> booksWithThisRating = filterParticularStars(i);
            int count = booksWithThisRating.size();
            double percentage = (double) count / reader.getCount() * 100;
            result.add(percentage);
        }
        return result;
    }

    /*
     * REQUIRES: int > 0
     * EFFECTS: returns all int-⭐ books in the reader's history
     */
    private ArrayList<Book> filterParticularStars(int i) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : reader.getHistory()) {
            if (b.getRating() == i) {
                result.add(b);
            }
        }
        return result;
    }

    /*
     * EFFECTS: displays the introduction screen and options
     */
    private void displayIntroduction() {
        System.out.println("\n🔖💡 Welcome to Bookmark! "
                + "\nHere, you can log books you've read, rate them, and view statistics about your reading history."
                + "\nType '" + SIGNUP_COMMAND + "' to get a new account. Type '" + LOGIN_COMMAND
                + "' to login to an existing one.");
    }

    /*
     * EFFECTS: displays menu of options to user
     */
    private void displayGeneralMenu() {
        System.out.println("\nEnter '" + LOG_BOOK_COMMAND + "' to log a new book."
                + "\nEnter '" + VIEW_HISTORY_COMMAND + "' to view your reading history."
                + "\nEnter '" + VIEW_FILTERED_HISTORY_COMMAND + "' to filter your history according to a certain "
                + "star rating."
                + "\nEnter '" + VIEW_STATISTICS_COMMAND + "' to view your statistics."
                + "\nEnter '" + LOGIN_COMMAND + "' to login to a different account." + "\nEnter '" + SIGNUP_COMMAND
                + "' to register a new name.");
    }

    /*
     * EFFECTS: displays instructions to user for viewing a filtered history
     */
    private void displaySelectHistoryMenu() {
        System.out.println("\nEnter (as an integer) your desired number of ⭐s.");
    }
}