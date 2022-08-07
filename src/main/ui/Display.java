package ui;

import model.Book;
import model.History;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Display extends JFrame implements ActionListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final int HGAP = 5;
    public static final int VGAP = 5;
    JButton button;
    JButton statsButton;
    JTextField f1;
    JLabel f2;
    JLabel f3;
    History history;
    String title = null;
    String author = null;
    Integer rating = null;
    private static final DecimalFormat df = new DecimalFormat("0.00");

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

        add(new JScrollPane(table)); //TODO: button helper, reorder
        statsButton = new JButton("View Statistics");
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
                + df.format(history.averageRating()) + ".            ";
//        ArrayList<String> list1 = new ArrayList<>();
//        ArrayList<String> list2 = new ArrayList<>();
//        for (Double d : history.calculateStarPercentages()) {
//            String phrase = d.toString() + "%";
//            list1.add(phrase);
//        }
//        for (int i = 0; i <= 4; i++) {
//            String result = viewStarPercentages(i, list1);
//            list2.add(result);
//        }
        f2.setText(summary);
        Icon image = null;
        image = new ImageIcon("data/ghost.png");
        f3.setIcon(image);
    }

    private String viewStarPercentages(int i, ArrayList<String> percentageList) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1 ★ ");
        list.add("2 ★ ");
        list.add("3 ★ ");
        list.add("4 ★ ");
        list.add("5 ★ ");
        String percentages = (list.get(i).concat(percentageList.get(i)));
        return percentages;
    }
}

//            l6.setText(f1.getText());

