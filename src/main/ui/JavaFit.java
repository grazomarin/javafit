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

        printIntro();
        while (keepGoing) {
            displayMainMenu();
            String command = scanner.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                handleMainMenuCommand(command);
            }
        }
    }

    // EFFECTS: displays the menu of options to the user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view all workout templates");
        System.out.println("\tc -> create a new workout template");
        System.out.println("\ts -> start a workout");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: processes user command
    private void handleMainMenuCommand(String command) {
        switch (command) {
            case "v":
                viewTemplates();
                break;
            case "c":
                createTemplate();
                break;
            case "s":
                selectWorkout();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // EFFECTS: displays all workout templates
    private void viewTemplates() {
        if (templates.isEmpty()) {
            System.out.println("You don't have any templates saved...");
        } else {
            for (int i = 1; i <= templates.size(); i++) {
                System.out.println("\t" + i + ": " + templates.get(i - 1).getName());
            }
        }
        // TODO add ability to view a template in detail
    }

    // REQUIRES: name has a non-zero length and is unique
    // EFFECTS: creates a new workout template
    private void createTemplate() {
        System.out.println("Enter the name of the template:");
        String name = scanner.next();
        Template template = new Template(name);
        templates.add(template);
        addExercises(template);
            System.out.println("Name cannot be empty...");
            Template template = new Template(name);
            templates.add(template);
            addExercises(template);
    }

    // MODIFIES: this
    // EFFECTS: selects a workout template to start
    private void selectWorkout() {
        if (templates.isEmpty()) {
            System.out.println("You don't have any templates saved...");
        } else {
            System.out.println("Select a template to start:");
            viewTemplates();
            int index = scanner.nextInt();
            if (index <= templates.size() && index > 0) {
                this.currentWorkout = templates.get(index - 1);
                startWorkout();
            } else {
                System.out.println("Invalid selection...");
                selectWorkout();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: starts the workout
    private void startWorkout() {
        boolean keepGoing = true;

        System.out.println("Let's start the workout!");
        System.out.println(this.currentWorkout.returnTemplate());

        while (keepGoing) {
            displayWorkoutMenu();
            String command = scanner.next().toLowerCase();
            handleWorkoutMenuCommand(command);

            if (command.equalsIgnoreCase("c")) {
                keepGoing = false;
            }
        }
    }


    // REQUIRES: exercise name has a non-zero length
    // MODIFIES: this
    // EFFECTS: adds exercises to the template
    private void addExercises(Template template) {
        int counter = 1;
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Enter the name of the exercise #" + counter + ": enter \"q\" to stop");
            String name = scanner.next();

            if (name.equalsIgnoreCase("q")) {
                if (counter == 1) {
                    System.out.println("You need to add at least one exercise...");
                } else {
                    keepGoing = false;
                }
            } else {
                template.addExcercise(name);
                counter++;
                    template.addExercise(name);
                    counter++;
            }
        }
        System.out.println("Template \"" + template.getName() + "\" was created!");
    }

    // MODIFIES: this
    // EFFECTS: adds a set to the workout
    private void addSet() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Enter the index of the exercise: enter \"q\" to cancel.");
            String command = scanner.next().toLowerCase();
            this.handlAddSetMenuCommand(command);
            System.out.println(this.currentWorkout.returnTemplate());

            if (command.equalsIgnoreCase("q")) {
                keepGoing = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add weight, reps, and RIR to a set
    private void handlAddSetMenuCommand(String command) {
        if (command.equalsIgnoreCase("q")) {
            System.out.println("Cancelled adding a set...");
            return;
        } else if (this.currentWorkout.validHumanIndex(command)) {
            System.out.println("Enter the weight (kg):");
            int weight = scanner.nextInt();
            System.out.println("Enter the reps:");
            int reps = scanner.nextInt();
            System.out.println("Enter the RIR (0-10):");
            int rir = scanner.nextInt();
            this.currentWorkout.addSet(Integer.parseInt(command), weight, reps, rir);
            System.out.println("Set added!");
        } else {
            System.out.println("Invalid selection...");
        }

    }

    // EFFECTS: displays the menu of options to the user
    private void displayWorkoutMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a set");
        // TODO add ability to remove a set
        // TODO add ability to change a set
        // TODO add ability to remove an exercise
        System.out.println("\tv -> view the workout");
        System.out.println("\tc -> complete the workout");
    }

    // EFFECTS: processes user command
    private void handleWorkoutMenuCommand(String command) {
        switch (command) {
            case "a":
                addSet();
                break;
            case "v":
                System.out.println(this.currentWorkout.returnTemplate());
                break;
            case "c":
                System.out.println("Workout completed! Template updated.");
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    @SuppressWarnings({"checkstyle:LineLength", "checkstyle:SuppressWarnings"})
    private void printIntro() {
        System.out.println(
                " __          __    _                                _                _                      ______  _  _   \n"
                        +
                        " \\ \\        / /   | |                              | |              | |                    |  ____|(_)| |  \n"
                        +
                        "  \\ \\  /\\  / /___ | |  ___  ___   _ __ ___    ___  | |_  ___        | |  __ _ __   __ __ _ | |__    _ | |_ \n"
                        +
                        "   \\ \\/  \\/ // _ \\| | / __|/ _ \\ | '_ ` _ \\  / _ \\ | __|/ _ \\   _   | | / _` |\\ \\ / // _` ||  __|  | || __|\n"
                        +
                        "    \\  /\\  /|  __/| || (__| (_) || | | | | ||  __/ | |_| (_) | | |__| || (_| | \\ V /| (_| || |     | || |_ \n"
                        +
                        "     \\/  \\/  \\___||_| \\___|\\___/ |_| |_| |_| \\___|  \\__|\\___/   \\____/  \\__,_|  \\_/  \\__,_||_|     |_| \\__|"
        );
    }


}
