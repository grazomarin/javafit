package ui;

import model.EventLog;
import model.Event;
import model.Exercise;
import model.Template;
import model.Set;
import persistance.JsonReader;
import persistance.JsonWriter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Represents the JavaFit GUI
public class JavaFitGUI extends JFrame {
    private static final String JSON_STORE = "./data/storage.json";
    private Template currentWorkout = null;
    private List<Template> templates = new ArrayList<Template>();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // UI Components
    private JPanel javaFit;

    //
    // Start of NavBar Panel
    private JPanel navBar;
    private JButton selectWorkoutButton;
    private JLabel currentWorkoutLabel;
    // End of NavBar Panel
    //

    private JPanel bodyPanel;

    //
    // Start of Template Panel
    private JPanel templatePanel;
    private JLabel bodyHeader;
    private JLabel emptyTemplatesStatusField;
    private JList listOfTemplates;

    // Template Form
    private JPanel createTemplateForm;
    private JTextField templateNameInput;
    private JTextArea templateExercisesTextArea;
    private JButton confirmCreateTemplateButton;
    // End of Template Form

    private JButton createTemplateButton;
    private JButton startWorkoutButton;
    // End of Template Panel
    //

    //
    // Start of Current Workout Panel
    private JPanel currentWorkoutContainerPanel;
    private JPanel currentWorkoutPanel;
    private JButton completeWorkoutButton;
    // End of Current Workout Panel
    //

    // EFFECTS: constructs the JavaFit GUI
    public JavaFitGUI() {
        super("JavaFit");
        setContentPane(javaFit);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        this.promptLoadingSavedData();
        setVisible(true);

        this.loadInitialLayout();
        this.setListeners();

    }

    // EFFECTS: prompts user to load saved data
    public void promptLoadingSavedData() {
        int confirm = JOptionPane.showConfirmDialog(this, "Would you like to load saved data?",
                "JavaFit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            loadTemplates();
            updateListOfTemplates();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads templates from JSON_STORE
    public void loadTemplates() {
        try {
            templates = jsonReader.read();
        } catch (Exception e) {
            displayErrorMessage("Unable to load data from " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves templates to JSON_STORE
    public void saveTemplates() {
        try {
            jsonWriter.open();
            jsonWriter.write(templates);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved data to " + JSON_STORE,
                    "JavaFit", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            displayErrorMessage("Unable to save data to " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the initial layout
    public void loadInitialLayout() {
        listOfTemplates.setVisible(!templates.isEmpty());
        createTemplateForm.setVisible(false);
        startWorkoutButton.setVisible(false);
        if (!templates.isEmpty()) {
            emptyTemplatesStatusField.setVisible(false);
        }
        currentWorkoutContainerPanel.setVisible(false);
    }

    // EFFECTS: displays an error message
    private void displayErrorMessage(String s) {
        JOptionPane.showMessageDialog(this, s,
                "JavaFit", JOptionPane.ERROR_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: updates the list of templates
    private void updateListOfTemplates() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Template template : templates) {
            listModel.addElement("- " + template.getName());
        }
        listOfTemplates.setModel(listModel);

        if (!listOfTemplates.isVisible()) {
            listOfTemplates.setVisible(true);
            emptyTemplatesStatusField.setVisible(false);
        }

        templatePanel.revalidate();
        templatePanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates a template with the given name and exercises
    public void createTemplate(String templateName, List<String> templateExercises) {
        Template newTemplate = new Template(templateName);
        for (String exercise : templateExercises) {
            newTemplate.addExercise(new Exercise(exercise));
        }
        templates.add(newTemplate);
    }

    // EFFECTS: parses exercises from input
    public static List<String> parseExercises(String input) {
        String[] lines = input.split("\\r?\\n");

        List<String> exercises = new ArrayList<>();
        for (String line : lines) {
            String cleanedLine = line.trim().replaceAll("\\s+", " ");
            if (!cleanedLine.isEmpty()) {
                exercises.add(cleanedLine);
            }
        }
        return exercises;
    }

    // MODIFIES: this
    // EFFECTS: sets listeners for the JavaFit GUI
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void setListeners() {
        // EFFECTS: sets listener for the window on load
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Show confirmation dialog
                int confirm = JOptionPane.showConfirmDialog(
                        JavaFitGUI.this,
                        "Would you like to save this session?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    saveTemplates();
                }

                printAllEvents();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dispose();
            }
        });

        // EFFECTS: sets listener for the create template button
        createTemplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTemplateForm.setVisible(true);
                createTemplateButton.setVisible(false);
                templatePanel.revalidate();
                templatePanel.repaint();
            }
        });

        // EFFECTS: sets listener for the confirm create template button
        confirmCreateTemplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String templateName = templateNameInput.getText().trim();
                List<String> templateExercises = parseExercises(templateExercisesTextArea.getText());

                if (templateName.isEmpty() || templateExercises.isEmpty()) {
                    displayErrorMessage("Please fill out all fields.");
                } else {
                    createTemplate(templateName, templateExercises);
                    updateListOfTemplates();
                    templateNameInput.setText("");
                    templateExercisesTextArea.setText("");
                    createTemplateForm.setVisible(false);
                    createTemplateButton.setVisible(true);
                }

                templatePanel.revalidate();
                templatePanel.repaint();
            }

        });

        // EFFECTS: sets listener for the select workout button
        selectWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (templates.isEmpty()) {
                    displayErrorMessage("Please create a template first.");
                } else {
                    bodyHeader.setText("Select a workout template:");
                    createTemplateButton.setVisible(false);
                    startWorkoutButton.setVisible(true);
                    createTemplateForm.setVisible(false);
                }

            }
        });


        // EFFECTS: sets listeners for the start workout button
        startWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listOfTemplates.getSelectedIndex();
                if (selectedIndex == -1) {
                    displayErrorMessage("Please select a template.");
                } else {
                    currentWorkout = templates.get(selectedIndex);
                    currentWorkoutLabel.setText(currentWorkout.getName());
                    startWorkoutButton.setVisible(false);
                    selectWorkoutButton.setVisible(false);
                    templatePanel.setVisible(false);
                    currentWorkoutContainerPanel.setVisible(true);
                    renderCurrentWorkoutPanel();
                }
            }
        });

        // EFFECTS: sets listeners for the current workout panel
        completeWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWorkout = null;
                currentWorkoutLabel.setText("Not Selected");
                templatePanel.setVisible(true);
                currentWorkoutContainerPanel.setVisible(false);
                bodyHeader.setText("My Templates:");
                createTemplateButton.setVisible(true);
                selectWorkoutButton.setVisible(true);
            }
        });
    }

    private void printAllEvents() {
        for (Event e: EventLog.getInstance()) {
            System.out.println(e.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: renders the current workout panel
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void renderCurrentWorkoutPanel() {
        currentWorkoutPanel.removeAll();
        currentWorkoutPanel.setLayout(new BoxLayout(currentWorkoutPanel, BoxLayout.Y_AXIS));
        currentWorkoutPanel.add(Box.createVerticalStrut(10));
        List<Exercise> currentExcercises = currentWorkout.getExercises();
        for (int i = 0; i < currentExcercises.size(); i++) {
            Exercise currentExercise = currentExcercises.get(i);
            List<Set> sets = currentExercise.getSets();
            JLabel exerciseLabel = new JLabel((i + 1) + ". " + currentExercise.getName());
            JList<String> setsList = new JList<>(sets.stream().map(Set::returnSet).toArray(String[]::new));

            JPanel setFormPanel = createSetForm(currentExercise, setsList);
            setFormPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


            currentWorkoutPanel.add(exerciseLabel);
            currentWorkoutPanel.add(setsList);
            currentWorkoutPanel.add(setFormPanel);



            setsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setsList.setAlignmentX(Component.LEFT_ALIGNMENT);
            exerciseLabel.setHorizontalAlignment(SwingConstants.LEFT);
            exerciseLabel.setForeground(Color.WHITE);
            setsList.setForeground(Color.WHITE);
            setsList.setBackground(new Color(23, 26, 31, 0));
            exerciseLabel.setFont(new Font("Calibri", Font.BOLD, 20));
            setsList.setFont(new Font("Calibri", Font.PLAIN, 18));

        }

        currentWorkoutPanel.revalidate();
        currentWorkoutPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates a set form
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private static JPanel createSetForm(Exercise currentExercise, JList<String> setsList) {
        JPanel setFormPanel = new JPanel();
        setFormPanel.setLayout(new FlowLayout());
        JLabel weightLabel = new JLabel("Weight (kg):");
        JLabel repsLabel = new JLabel("Reps:");
        JLabel rirLabel = new JLabel("RIR:");
        JSpinner weightInput = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        JSpinner repsInput = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JSpinner rirInput = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JButton addSetButton = new JButton("Add Set");


        addSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer weight = (Integer) weightInput.getValue();
                Integer reps = (Integer) repsInput.getValue();
                Integer rir = (Integer) rirInput.getValue();

                if (weight <= 0 || reps <= 0 || rir <= 0) {
                    return;
                }

                currentExercise.addSet(weight, reps, rir);
                setsList.setListData(currentExercise.getSets().stream().map(Set::returnSet).toArray(String[]::new));

                weightInput.setValue(0);
                repsInput.setValue(0);
                rirInput.setValue(0);

                setsList.revalidate();
                setsList.repaint();
            }
        });

        setFormPanel.setBackground(new Color(23, 26, 31));
        weightInput.setFont(new Font("Calibri", Font.PLAIN, 18));
        repsInput.setFont(new Font("Calibri", Font.PLAIN, 18));
        rirInput.setFont(new Font("Calibri", Font.PLAIN, 18));
        weightLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        repsLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        rirLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        weightLabel.setForeground(Color.WHITE);
        repsLabel.setForeground(Color.WHITE);
        rirLabel.setForeground(Color.WHITE);
        setFormPanel.add(weightLabel);
        setFormPanel.add(weightInput);
        setFormPanel.add(repsLabel);
        setFormPanel.add(repsInput);
        setFormPanel.add(rirLabel);
        setFormPanel.add(rirInput);
        setFormPanel.add(addSetButton);
        return setFormPanel;
    }

    // EFFECTS: starts the JavaFit GUI
    public static void main(String[] args) {
        new JavaFitGUI();
    }

}
