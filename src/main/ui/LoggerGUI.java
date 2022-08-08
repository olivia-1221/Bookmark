package ui;

import model.Book;
import model.History;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

// Represents the main window for 'Story Graph' application
public class LoggerGUI extends JFrame implements ActionListener {

    private static final int WIDTH = 780;
    private static final int HEIGHT = 700;
    private static final int H_GAP = 5;
    private static final int V_GAP = 5;
    private static final String JSON_STORE = "./data/history.json";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final JButton button;
    private JLabel statsSummary;
    private JLabel statsIcon;
    private JLabel loadAndSave;
    private JTextField createEntry;
    private JTextField statsBar;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private History history;

    String[] columns = new String[]{
            "Title", "Author", "Rating (1-5★)"
    };
    DefaultTableModel model = new DefaultTableModel(columns, 0);
    JTable table = new JTable(model);

    // EFFECTS: constructs main window with table, buttons, text fields, labels
    // Code adapted from LabelChanger
    public LoggerGUI() throws FileNotFoundException {
        super("Story Graph 💡");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        add(new JScrollPane(table));
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        history = new History("My Reading History");
        button = new JButton("Create New Entry");

        initializeButtons();
        initializeLabels();
        pack();
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: sets up main buttons (save, load, stats, entry)
    public void initializeButtons() {
        JButton saveButton = new JButton("Save Data");
        saveButton.setActionCommand("saveButton");
        saveButton.addActionListener(this);
        JButton loadButton = new JButton("Load Data");
        loadButton.setActionCommand("loadButton");
        loadButton.addActionListener(this);
        JButton statsButton = new JButton("View Statistics");
        statsButton.setActionCommand("statsButton");
        statsButton.addActionListener(this);
        button.setActionCommand("entryButton");
        button.addActionListener(this);
        add(saveButton);
        add(loadButton);
        add(statsButton);
        add(button);
    }

    // MODIFIES: this
    // EFFECTS: sets up main text labels and fields
    public void initializeLabels() {
        statsSummary = new JLabel("");
        statsIcon = new JLabel("");
        loadAndSave = new JLabel("");
        statsBar = new JTextField();
        add(statsSummary);
        add(statsIcon);
        add(loadAndSave);
        add(statsBar);
        statsBar.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: changes the state of the program according to the button pressed
    // Code adapted from LabelChanger
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("entryButton")) {
            initializeEntry();
        } else if (e.getActionCommand().equals("titleButton")) {
            inputBook(0, "Enter author:", "authorButton");
        } else if (e.getActionCommand().equals("authorButton")) {
            inputBook(1, "Enter rating:", "ratingButton");
        } else if (e.getActionCommand().equals("ratingButton")) {
            inputRating();
        } else if (e.getActionCommand().equals("finalize")) {
            finalizeEntry();
        } else if (e.getActionCommand().equals("statsButton")) {
            viewSummary();
        } else if (e.getActionCommand().equals("saveButton")) {
            saveHistory();
        } else if (e.getActionCommand().equals("loadButton")) {
            loadHistory();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up a new entry by adding a row to the table and updating the main button
    public void initializeEntry() {
        createEntry();
        model.addRow(new Object[]{
        });
        button.setText("Enter title:");
        button.setActionCommand("titleButton");
    }

    // MODIFIES: this
    // EFFECTS: makes a text field where user can type book info;
    //          makes other fields invisible because they're not in use
    public void createEntry() {
        createEntry = new JTextField(8);
        add(createEntry);
        statsSummary.setText("");
        statsIcon.setIcon(null);
        loadAndSave.setText("");
        statsBar.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: sets title & author of a new table entry and updates main button
    public void inputBook(Integer column, String text, String command) {
        String s = createEntry.getText();
        model.setValueAt(s, history.numBooks(), column);
        button.setText(text);
        button.setActionCommand(command);
    }

    // REQUIRES: user input is an integer from 1-5
    // MODIFIES: this
    // EFFECTS: sets title & author of the current entry and updates main button
    public void inputRating() {
        Integer rating = Integer.valueOf(createEntry.getText());
        model.setValueAt(rating, history.numBooks(), 2);
        button.setText("Click to Finalize Input");
        button.setActionCommand("finalize");
        remove(createEntry);
    }

    // REQUIRES: specified cells have non-empty values
    // MODIFIES: this
    // EFFECTS: adds finished entry to reading history and updates main button
    public void finalizeEntry() {
        String t = (String) model.getValueAt(history.numBooks(), 0);
        String a = (String) model.getValueAt(history.numBooks(), 1);
        Integer r = (Integer) model.getValueAt(history.numBooks(), 2);
        Book book = new Book(t, a, r);
        history.addBook(book);
        button.setText("Create New Entry");
        button.setActionCommand("entryButton");
    }

    // MODIFIES: this
    // EFFECTS: displays basic statistics (# books, average rating) on screen
    public void viewSummary() {
        String summary;
        summary = "You've logged " + history.numBooks() + " book(s) and your average rating is ~"
                + df.format(history.averageRating()) + " ★ To see a stacked bar of your ratings, type any"
                + " letter below.";
        statsSummary.setText(summary);
        Icon image = new ImageIcon("data/ghost.png");
        statsIcon.setIcon(image);
        viewBar();
    }

    // MODIFIES: this
    // EFFECTS: adds finished entry to reading history and updates main button
    public void viewBar() {
        statsBar.setColumns(10);
        statsBar.setVisible(true);
        statsBar.addKeyListener(new StatsKey(history));
    }

    // EFFECTS: saves history to file
    // Code adapted from JsonSerializationDemo
    private void saveHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(history);
            jsonWriter.close();
            loadAndSave.setText("Saved to " + JSON_STORE + ".");
        } catch (FileNotFoundException e) {
            loadAndSave.setText("Unable to write to file: " + JSON_STORE + ".");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads history from file, displaying the entries on the table
    // Code adapted from JsonSerializationDemo
    private void loadHistory() {
        try {
            history = jsonReader.read();
            for (int i = 0; i < history.numBooks(); i++) {
                model.addRow(new Object[]{
                });
            }
            int index = 0;
            for (Book b : history.getBooks()) {
                model.setValueAt(b.getTitle(), index, 0);
                model.setValueAt(b.getAuthor(), index, 1);
                model.setValueAt(b.getRating(), index, 2);
                index++;
            }
            loadAndSave.setText("Loaded from " + JSON_STORE + ".");
        } catch (IOException e) {
            loadAndSave.setText("Unable to read to file: " + JSON_STORE + ".");
        }
    }
}

