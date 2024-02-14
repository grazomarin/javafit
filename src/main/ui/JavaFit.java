package ui;

import model.Template;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// JavaFit application
public class JavaFit {
    private Template currentWorkout = null;
    private List<Template> templates = new ArrayList<Template>();
    private Scanner scanner;

    public JavaFit() {
        run();
    }

    // EFFECTS: runs the JavaFit application
    private void run() {
        scanner = new Scanner(System.in).useDelimiter("\n");
        boolean keepGoing = true;

        while (keepGoing) {
            displayMenu();
            String command = scanner.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    // EFFECTS: processes user command
    private void processCommand(String command) {
       // TODO
    }

    // EFFECTS: displays the menu of options to the user
    private void displayMenu() {
       // TODO
    }

}
