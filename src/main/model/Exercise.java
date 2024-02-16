package model;

import java.util.*;

// Represents an excercise in a workout
public class Exercise {
    private String name;
    private List<Set> sets; // each element represents a weight in each set
    private Boolean completed;

    public Exercise(String name) {
        this.name = name;
        this.sets = new ArrayList<Set>();
    }

    // REQUIRES: weight > 0, reps > 0, rir >= 0
    // MODIFIES: this
    // EFFECTS: adds a set to the excercise
    public void addSet(int weight, int reps, int rir) {
        sets.add(new Set(weight, reps, rir));
    }

    public String returnExercise() {
        StringBuilder output = new StringBuilder(name + ":\n");
        if (sets.isEmpty()) {
            return output.append("    1: ???kg: ??? reps || ???RIR\n").toString();
        } else {
            for (int i = 1; i <= sets.size(); i++) {
                output.append("    ").append(i).append(": ").append(sets.get(i - 1).returnSet()).append("\n");
            }
            return output.toString();
        }
    }

    public String getName() {
        return name;
    }

    public List<Set> getSets() {
        return sets;
    }
}

