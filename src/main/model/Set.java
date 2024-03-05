package model;

import org.json.JSONObject;
import persistance.Writable;

// Represents a set of an Exercise
public class Set implements Writable {
    private int reps;
    private int weight; // in kg
    private int rir; // from 0 to 10


    public Set(int weight, int reps, int rir) {
        this.reps = reps;
        this.weight = weight;
        this.rir = rir;
    }

    // EFFECTS: returns the set in the format "weight kg: reps reps || rirRIR"
    public String returnSet() {
        return weight + "kg: " + reps + " reps || " + rir + "RIR";
    }

    // EFFECTS: returns Set as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("weight", weight);
        json.put("reps", reps);
        json.put("rir", rir);
        return json;
    }
}
