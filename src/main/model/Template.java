package model;

import java.util.*;

// Represents a template for a Workout
public class Template {
    private String name;
    private List<Exercise> excercises;

    public Template(String name) {
        this.name = name;
        this.excercises = new ArrayList<Exercise>();
    }

    public void addExercise(String excerciseName) {
        excercises.add(new Exercise(excerciseName));
    }

//    public void removeExcercise(Excercise excercise) {
//        // TODO add ability to remove excercise
//    }

    public void addSet(int humanIndex, int weight, int reps, int rir) {
        excercises.get(humanIndex - 1).addSet(weight, reps, rir);
    }

    // REQUIRES: command is a valid index from 1 to exercises.size()
    public boolean validHumanIndex(String command) {
        int index = Integer.parseInt(command);
        return index > 0 && index <= excercises.size();
    }

    // REQUIRES: excerciseName has a non-zero length and has at least 1 exercise
    // EFFECTS: prints the template
    public String returnTemplate() {
        StringBuilder output = new StringBuilder(name + " template:\n");
        for (int i = 1; i <= excercises.size(); i++) {
            output.append(i).append(". ").append(excercises.get(i - 1).returnExercise());
        }
        return output.toString();
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExcercises() {
        return excercises;
    }
}
