package ui;

import model.Book;
import model.History;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Display extends JFrame implements ActionListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final int HGAP = 5;
    public static final int VGAP = 5;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    JButton button;
    JTextField f1; //TODO: better names
    JLabel f2;
    JLabel f3;
    History history;
    String title = null;
    String author = null;
    Integer rating = null;

    String[] columns = new String[]{
            "Title", "Author", "Rating"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0);
    JTable table = new JTable(model);

    //TODO: HELPERS/shorten; rename class and some methods; reorder
    public Display() {
        super("Bookmark 🔖💡");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new FlowLayout(FlowLayout.CENTER, HGAP, VGAP));
        add(new JScrollPane(table));

        history = new History("My Reading History");

        //TODO: initializeButtons() helper
        JButton statsButton = new JButton("View Statistics");
        statsButton.setActionCommand("statsButton");
        statsButton.addActionListener(this);
        button = new JButton("Create New Entry");
        button.setActionCommand("entryButton");
        button.addActionListener(this);
        add(statsButton);
        add(button);

        f2 = new JLabel("");
        f3 = new JLabel("");
        add(f2);
        add(f3);
        pack();
        setVisible(true);
        setResizable(true);
    }

    public void createEntry() {
        f1 = new JTextField(8);
        add(f1);
        f2.setText("");
        f3.setIcon(null);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void actionPerformed(ActionEvent e) {
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
    }

    public void update(String s, Integer column, String text, String command) {
        s = f1.getText();
        model.setValueAt(s, history.numBooks(), column);
        button.setText(text);
        button.setActionCommand(command);
    }

    private void viewStatistics() {
        String summary;
        summary = "You've logged " + history.numBooks() + " book(s) so far and your average rating is ~"
                + df.format(history.averageRating()) + ". To visualize your rating proportions, type any"
                + " letter in the text field."; //TODO: move 'to visualize' to separate Jlabel
        f2.setText(summary);
        Icon image = new ImageIcon("data/ghost.png");
        f3.setIcon(image);
        viewGraph();
    }

    private void viewGraph() {
        JTextField textField = new JTextField(10);
        textField.addKeyListener(new ViewGraph(history));
        add(textField);
    }
}

