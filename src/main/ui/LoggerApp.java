//package ui;
//
//import model.Account;
//
//import java.util.Scanner;
//
//public class LoggerApp {
//    Account account;
//    private Scanner input;
//
//    // TODO: add model specs
//
//    public LoggerApp() {
//        runLogger();
//    }
//
//    private void runLogger() {
//        boolean keepGoing = true;
//        String command = null;
//
//        init();
//
//        while (keepGoing) {
//            displayMenu1();
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//
//        System.out.println("\nThank you for using Bookmark!");
//    }
//
//    private void processCommand(String command) {
//        if (command.equals("r")) {
//            System.out.println("\nPlease enter the username you want to have:");
//            // String account.name = input.next()
//        } else if (command.equals("l")) {
//            doAddition();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    private void doAddition() {
//    }
//
//    private void init() {
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
//    }
//
//    private void displayMenu1() {
//        System.out.println("\nSelect from:");
//        System.out.println("\tr -> register");
//        System.out.println("\tl -> log-in");
//    }
//
//}
