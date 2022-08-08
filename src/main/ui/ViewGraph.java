package ui;

import model.History;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.lang.Character.isLetter;

public class ViewGraph extends KeyAdapter {
    private History history;

    public ViewGraph(History history) {
        this.history = history;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();
        if (isLetter(ch)) {
            DrawGraph graph = new DrawGraph(history);
            graph.setSize(500, 500);
            graph.setVisible(true);
        }
    }

}
