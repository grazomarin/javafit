package model;

import java.util.*;

// Represents an excercise in a workout
public class Excercise {
    private String name;
    private List<Set> sets; // each element represents a weight in each set
    private Boolean completed;

    public Excercise(String name, List<Set> sets) {
        this.name = name;
        this.sets = sets;
    }

    public Excercise(String name) {
        this.name = name;
        this.sets = new ArrayList<Set>();
    }

    // REQUIRES: weight > 0, reps > 0, rir >= 0
    // MODIFIES: this
    // EFFECTS: adds a set to the excercise
    public void addSet(int weight, int reps, int rir) {
        // TODO
    }

    public String printExcercise() {
        return name;
    }

}

