package ui;

import model.History;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawGraph extends JFrame {
    private static final int TOTAL_LENGTH = 300;
    private static final int STARTING_X = 100;
    private static final int HEIGHT = 80;
    private static final int Y_POS = 100;
    private static final Color LIGHT_BLUE = new Color(114, 176, 212);
    private static final Color GRAY = new Color(175, 181, 186);
    private static final Color LIGHT_BEIGE = new Color(237, 222, 168);
    private static final Color LIGHT_PINK = new Color(240, 211, 222);
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private History history;
    private ArrayList<Color> colors = new ArrayList<>(Arrays.asList(LIGHT_BLUE,
            GRAY, Color.WHITE, LIGHT_BEIGE, LIGHT_PINK));

    public DrawGraph(History history) {
        this.history = history;
        this.setTitle("Rating Proportions");
    }

    public void paint(Graphics g) {
        int x = STARTING_X;
        int curr = 0;

        for (Double d : history.calculateStarPercentages()) {
            int width = (int) (d * TOTAL_LENGTH);
            g.setColor(getColor(colors, curr));
            g.fillRect(x, Y_POS, width, HEIGHT);
            String phrase = df.format(d * 100) + "%";
            g.setColor(Color.BLACK);
            if (width != 0) {
                g.drawString(phrase, (x + width / 2) - 20, (Y_POS + HEIGHT / 2));
            }
            x = (int) (x + (d * TOTAL_LENGTH));
            curr++;
        }

        for (int i = 0; i < 5; i++) {
            g.setColor(getColor(colors, i));
            g.drawString((i + 1) + " ★", STARTING_X + TOTAL_LENGTH / 2 - 30, HEIGHT + Y_POS + 50 + (20 * i));
        }

        g.setColor(Color.BLACK);
        g.drawString("KEY", STARTING_X + TOTAL_LENGTH / 2 + 15, HEIGHT + Y_POS + 90);
        g.drawRoundRect(STARTING_X + TOTAL_LENGTH / 2 - 35, HEIGHT + Y_POS + 30,
                40, 110, 15, 15);
    }

    public Color getColor(ArrayList<Color> colors, Integer i) {
        Color color = colors.get(i);
        return color;
    }
}
