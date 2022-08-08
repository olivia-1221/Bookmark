package ui;

import model.History;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Character.isLetter;

// Represents a class for receiving keyboard events to summon StatsBar
// Code learned from Java|Oracle at https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyAdapter.html
public class StatsKey extends KeyAdapter {
    private final History history;

    // EFFECTS: sets this.history as the given history so that keyPressed can access it
    public StatsKey(History history) {
        this.history = history;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: if the key pressed is a letter, StatsBar window appears
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();
        if (isLetter(ch)) {
            StatsBar graph = new StatsBar(history);
            graph.setSize(500, 500);
            graph.setVisible(true);
        }
    }

}
