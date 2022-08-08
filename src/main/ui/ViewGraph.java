package ui;

import model.History;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Character.isLetter;

// Represents a class for receiving keyboard events, in this case to summon // TODO
public class ViewGraph extends KeyAdapter {
    private History history;

    // EFFECTS: sets this.history as the given history so that keyPressed can access it
    public ViewGraph(History history) {
        this.history = history;
    }

    @Override
    // EFFECTS: if the pressed key is a letter, the // TODO window appears
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();
        if (isLetter(ch)) {
            DrawGraph graph = new DrawGraph(history);
            graph.setSize(500, 500);
            graph.setVisible(true);
        }
    }

}
