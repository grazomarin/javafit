package ui;

import javax.swing.*;
import java.awt.event.*;

public class JavaFitGUI extends JFrame {
    private JPanel javaFit;
    private JPanel navBar;
    private JButton selectWorkoutButton;

    private JPanel bodyPanel;

    //
    // Start of Template Panel

    private JPanel templatePanel;
    private JList listOfTemplates;

    // Template Form
    private JPanel createTemplateForm;
    private JTextField templateNameInput;
    private JTextArea templateExercisesTextArea;
    private JButton confirmCreateTemplateButton;
    // End of Template Form

    private JButton createTemplateButton;

    // End of Template Panel
    //


    public JavaFitGUI() {
        super("JavaFit");
        setContentPane(javaFit);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        this.promptLoadingSavedData();
        setVisible(true);
        this.loadInitialLayout();
        this.setListeners();

    }

    public void promptLoadingSavedData() {
        JOptionPane.showConfirmDialog(this, "Would you like to load saved data?",
                "JavaFit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public void loadInitialLayout() {
        createTemplateForm.setVisible(false);
    }

    public void setListeners() {
        createTemplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTemplateForm.setVisible(true);
                createTemplateButton.setVisible(false);
                templatePanel.revalidate();
                templatePanel.repaint();
            }
        });

        confirmCreateTemplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String templateName = templateNameInput.getText();
                String templateExercises = templateExercisesTextArea.getText();

                System.out.println("Template Name: " + templateName);
                System.out.println("Template Name: " + templateExercises);

                // Save template to database
                createTemplateForm.setVisible(false);
                createTemplateButton.setVisible(true);
                templatePanel.revalidate();
                templatePanel.repaint();
            }
        });


    }

    public static void main(String[] args) {
        new JavaFitGUI();
    }

}
