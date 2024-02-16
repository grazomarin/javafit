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

    // EFFECTS: returns the set in the format "weightkg: reps reps || rirRIR"
    public String returnSet() {
        return weight + "kg: " + reps + " reps || " + rir + "RIR";
    }
}
