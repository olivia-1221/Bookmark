package ui;

import model.Book;
import model.History;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends JFrame implements ActionListener {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 500;
    public static final int HGAP = 5;
    public static final int VGAP = 5;
    JButton button;
    JTextField f1;
    History history;
    String title = null;
    String author = null;
    Integer rating = null;

    String[] columns = new String[]{
            "Title", "Author", "Rating"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0);
    JTable table = new JTable(model);

    //TODO: HELPERS/shorten; stats + save + load buttons; rename class
    public Display() {
        super("Bookmark 🔖💡");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new FlowLayout(FlowLayout.CENTER, HGAP, VGAP));

        history = new History("My Reading History");

        add(new JScrollPane(table));
        button = new JButton("Create New Entry");
        button.setActionCommand("entryButton");
        button.addActionListener(this);
        add(button);
        pack();
        setVisible(true);
        setResizable(true);
    }

    public void createEntry() {
        f1 = new JTextField(8);
        add(f1);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("entryButton")) {
            button.setText("Enter title:");
            button.setActionCommand("titleButton");
            createEntry();
            model.addRow(new Object[] {
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
    }

    public void update(String s, Integer column, String text, String command) {
        s = f1.getText();
        model.setValueAt(s, history.numBooks(), column);
        button.setText(text);
        button.setActionCommand(command);
    }

    public void displayStatistics() {

    }
}

//            l6.setText(f1.getText());

