package model;

// Represents a set of an Exercise
public class Set {
    private int reps;
    private int weight; // in kg
    private int rir; // from 0 to 10


    public Set(int weight, int reps, int rir) {
        this.reps = reps;
        this.weight = weight;
        this.rir = rir;
    }

    public Set(int weight) {
        this.weight = weight;
    }

    // REQUIRES: weight > 0
    // MODIFIES: this
    // EFFECTS: updates the weight
    public void setWeight(int weight) {
        this.weight = weight;
    }

    // REQUIRES: reps > 0
    // MODIFIES: this
    // EFFECTS: updates the reps
    public void setReps(int reps) {
        this.reps = reps;
    }

    // REQUIRES: rir > 0
    // MODIFIES: this
    // EFFECTS: updates the rir
    public void setRir(int weight) {
        this.weight = weight;
    }

    public String printSet() {
        return weight + "kg: " + reps + " reps || " + rir + "RIR \n";
    }
}
