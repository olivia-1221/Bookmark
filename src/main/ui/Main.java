package ui;

import java.io.FileNotFoundException;

// Runs Story Graph
public class Main {

    public static void main(String[] args) {
        try {
            new LoggerGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
