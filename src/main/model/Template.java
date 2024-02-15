package model;

import java.util.*;

// Represents a template for a Workout
public class Template {
    private String name;
    private List<Excercise> excercises;

    public Template(String name) {
        this.name = name;
        this.excercises = new ArrayList<Excercise>();
    }

    public void addExcercise(String excerciseName) {
        excercises.add(new Excercise(excerciseName));
    }

    public void removeExcercise(Excercise excercise) {
        // TODO
    }

    public void addSet(int humanIndex, int weight, int reps, int rir) {
        excercises.get(humanIndex - 1).addSet(weight, reps, rir);
    }

    public String getName() {
        return name;
    }

    public void printTemplate() {
        System.out.println(name + " template:");
        for (int i = 1; i <= excercises.size(); i++) {
            System.out.print(i + ". ");
            excercises.get(i - 1).printExcercise();
        }
    }

    // REQUIRES: command is a valid index from 1 to exercises.size()
    public boolean validHumanIndex(String command) {
        int index = Integer.parseInt(command);
        return index > 0 && index <= excercises.size();
    }
}
