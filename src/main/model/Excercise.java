package model;

import java.util.*;

// Represents an excercise in a workout
public class Excercise {
    private String name;
    private List<Set> sets; // each element represents a weight in each set
    private Boolean completed;

    public Excercise(String name) {
        this.name = name;
        this.sets = new ArrayList<Set>();
    }

    // REQUIRES: weight > 0, reps > 0, rir >= 0
    // MODIFIES: this
    // EFFECTS: adds a set to the excercise
    public void addSet(int weight, int reps, int rir) {
        sets.add(new Set(weight, reps, rir));
    }

    public void printExcercise() {
        System.out.println(name + ":");
        if (sets.isEmpty()) {
            System.out.println("    1: ???kg: ??? reps || ???RIR");
        } else {
            for (int i = 1; i <= sets.size(); i++) {
                System.out.println("    " + i + ": " + sets.get(i - 1).returnSet());
            }
        }
    }

}

