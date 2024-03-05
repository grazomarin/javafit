package ui;

import model.Exercise;
import model.Template;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// JavaFit application
public class JavaFit {
    private static final String JSON_STORE = "./data/storage.json";
    private Template currentWorkout = null;
    private List<Template> templates = new ArrayList<Template>();
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    public JavaFit() {
        scanner = new Scanner(System.in).useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        printIntro();
        promptLoadingSavedData();
        launchApplication();
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to load saved data
    private void promptLoadingSavedData() {
        System.out.println("Would you like to load saved data? (y/n)");
        String command = scanner.next().toLowerCase();
        if (command.equals("y")) {
            loadTemplates();
        } else if (command.equals("n")) {
            System.out.println("No saved data loaded...");
        } else {
            System.out.println("Invalid selection...");
            promptLoadingSavedData();
        }

    }

    private void loadTemplates() {
        try {
            templates = jsonReader.read();
            System.out.println("Loaded saved data!");
        } catch (Exception e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: runs the JavaFit application
    private void launchApplication() {
        boolean keepGoing = true;

        while (keepGoing) {
            displayMainMenu();
            String command = scanner.next().toLowerCase();

            if (command.equals("q")) {
                promptSavingData();
                keepGoing = false;
            } else {
                handleMainMenuCommand(command);
            }
        }
    }

    private void promptSavingData() {
        System.out.println("Would you like to save your data? (y/n)");
        String command = scanner.next().toLowerCase();
        if (command.equals("y")) {
            saveTemplates();
        } else if (command.equals("n")) {
            System.out.println("No data saved...");
        } else {
            System.out.println("Invalid selection...");
            promptSavingData();
        }
    }

    private void saveTemplates() {
        try {
            jsonWriter.open();
            jsonWriter.write(templates);
            jsonWriter.close();
            System.out.println("Saved " + templates.size() + " templates to " + JSON_STORE);
        } catch (Exception e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
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

    // MODIFIES: this
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

    // EFFECTS: creates a new workout template
    private void createTemplate() {
        System.out.println("Enter the name of the template:");
        String name = scanner.next();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty...");
            createTemplate();
        } else {
            Template template = new Template(name);
            templates.add(template);
            addExercises(template);
        }
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
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty...");
                } else {
                    template.addExercise(new Exercise(name));
                    counter++;
                }
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
            this.handleAddSetMenuCommand(command);
            System.out.println(this.currentWorkout.returnTemplate());

            if (command.equalsIgnoreCase("q")) {
                keepGoing = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add weight, reps, and RIR to a set
    private void handleAddSetMenuCommand(String command) {
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

    // MODIFIES: this
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
                        "     \\/  \\/  \\___||_| \\___|\\___/ |_| |_| |_| \\___|  \\__|\\___/   \\____/  \\__,_|  \\_/  \\__,_||_|     |_| \\__|\n\n"
        );
    }


}
