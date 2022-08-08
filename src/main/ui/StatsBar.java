package ui;

import model.History;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

// Represents the pop-up window for a visualization of the history's rating proportions
public class StatsBar extends JFrame {
    private static final int BAR_LENGTH = 300;
    private static final int STARTING_X = 100;
    private static final int HEIGHT = 80;
    private static final int Y_POS = 100;
    private static final Color BLUE = new Color(114, 176, 212);
    private static final Color GRAY = new Color(175, 181, 186);
    private static final Color BEIGE = new Color(237, 222, 168);
    private static final Color PINK = new Color(240, 211, 222);
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final ArrayList<Color> colors = new ArrayList<>(Arrays.asList(BLUE,
            GRAY, Color.WHITE, BEIGE, PINK));
    private final History history;

    // EFFECTS: constructs a new window containing given history data
    public StatsBar(History history) {
        this.history = history;
        this.setTitle("★ Rating Proportions ★");
    }

    // MODIFIES: this
    // EFFECTS: renders a stacked bar of history's rating proportions, with a color key
    // Code learned from Java|Oracle at https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
    public void paint(Graphics g) {
        int x = STARTING_X;
        int curr = 0;

        for (Double d : history.calculateStarPercentages()) {
            int width = (int) (d * BAR_LENGTH);
            g.setColor(getColor(colors, curr));
            g.fillRect(x, Y_POS, width, HEIGHT);
            String phrase = df.format(d * 100) + "%";
            g.setColor(Color.BLACK);
            if (width != 0) {
                g.drawString(phrase, (x + width / 2) - 20, (Y_POS + HEIGHT / 2));
            }
            x = (int) (x + (d * BAR_LENGTH));
            curr++;
        }

        for (int i = 0; i < 5; i++) {
            g.setColor(getColor(colors, i));
            g.drawString((i + 1) + " ★", STARTING_X + BAR_LENGTH / 2 - 30, HEIGHT + Y_POS + 50 + (20 * i));
        }

        g.setColor(Color.BLACK);
        g.drawString("KEY", STARTING_X + BAR_LENGTH / 2 + 15, HEIGHT + Y_POS + 90);
        g.drawRoundRect(STARTING_X + BAR_LENGTH / 2 - 35, HEIGHT + Y_POS + 30,
                40, 110, 15, 15);
    }

    // Getter
    public Color getColor(ArrayList<Color> colors, Integer i) {
        return colors.get(i);
    }
}
