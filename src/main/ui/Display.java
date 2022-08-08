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

public class Display extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/history.json";
    public static final int WIDTH = 750;
    public static final int HEIGHT = 700;
    public static final int HGAP = 5;
    public static final int VGAP = 5;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private JButton button;
    private JButton saveButton;
    private JButton loadButton;
    JTextField f1; //TODO: better names
    JLabel f2;
    JLabel f3;
    JLabel f4;
    History history;
    String title = null;
    String author = null;
    Integer rating = null;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    String[] columns = new String[]{
            "Title", "Author", "Rating"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0);
    JTable table = new JTable(model);

    public Display() throws FileNotFoundException { //TODO: rename class and some methods; reorder
        super("Story Graph 🔖💡");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new FlowLayout(FlowLayout.CENTER, HGAP, VGAP));
        add(new JScrollPane(table));
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        history = new History("My Reading History");

        //TODO: initializeButtons() helper
        saveButton = new JButton("Save Data");
        saveButton.setActionCommand("saveButton");
        saveButton.addActionListener(this);
        loadButton = new JButton("Load Data");
        loadButton.setActionCommand("loadButton");
        loadButton.addActionListener(this);
        JButton statsButton = new JButton("View Statistics");
        statsButton.setActionCommand("statsButton");
        statsButton.addActionListener(this);
        button = new JButton("Create New Entry");
        button.setActionCommand("entryButton");
        button.addActionListener(this);
        add(saveButton);
        add(loadButton);
        add(statsButton);
        add(button);

        f2 = new JLabel("");
        f3 = new JLabel("");
        f4 = new JLabel("");
        add(f2);
        add(f3);
        add(f4);
        pack();
        setVisible(true);
        setResizable(false);
    }

    public void createEntry() {
        f1 = new JTextField(8);
        add(f1);
        f2.setText("");
        f3.setIcon(null);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void actionPerformed(ActionEvent e) { //TODO: helpers
        if (e.getActionCommand().equals("entryButton")) {
            button.setText("Enter title:");
            button.setActionCommand("titleButton");
            createEntry();
            pack();
            model.addRow(new Object[]{
            });
        }
        if (e.getActionCommand().equals("titleButton")) {
            update(title, 0, "Enter author:", "authorButton");
        }
        if (e.getActionCommand().equals("authorButton")) {
            update(author, 1, "Enter rating:", "ratingButton");
        }
        if (e.getActionCommand().equals("ratingButton")) {
            rating = Integer.valueOf(f1.getText());
            model.setValueAt(rating, history.numBooks(), 2);
            button.setText("Click to Finalize Input");
            button.setActionCommand("finalize");
            remove(f1);
        }
        if (e.getActionCommand().equals("finalize")) { //TODO: make uneditable
            title = (String) model.getValueAt(history.numBooks(), 0);
            author = (String) model.getValueAt(history.numBooks(), 1);
            rating = (Integer) model.getValueAt(history.numBooks(), 2);
            Book book = new Book(title, author, rating);
            history.addBook(book);
            button.setText("Create New Entry");
            button.setActionCommand("entryButton");
        }
        if (e.getActionCommand().equals("statsButton")) {
            viewStatistics();
        }
        if (e.getActionCommand().equals("saveButton")) {
            saveHistory();
        }
        if (e.getActionCommand().equals("loadButton")) {
            loadHistory();
        }
    }

    public void update(String s, Integer column, String text, String command) {
        s = f1.getText();
        model.setValueAt(s, history.numBooks(), column);
        button.setText(text);
        button.setActionCommand(command);
    }

    public void viewStatistics() {
        String summary;
        summary = "You've logged " + history.numBooks() + " book(s) and your average rating is ~"
                + df.format(history.averageRating()) + ". To visualize your rating proportions, type any"
                + " letter below.";
        f2.setText(summary);
        Icon image = new ImageIcon("data/ghost.png");
        f3.setIcon(image);
        viewGraph();
    }

    public void viewGraph() {
        JTextField textField = new JTextField(10);
        textField.addKeyListener(new ViewGraph(history));
        add(textField);
    }

    private void saveHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(history);
            jsonWriter.close();
            f4.setText("Saved to " + JSON_STORE + ".");
        } catch (FileNotFoundException e) {
            f4.setText("Unable to write to file: " + JSON_STORE + ".");
        }
    }

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
            f4.setText("Loaded from " + JSON_STORE + ".");
        } catch (IOException e) {
            f4.setText("Unable to read to file: " + JSON_STORE + ".");
        }
    }
}

