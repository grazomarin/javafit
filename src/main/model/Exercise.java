package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.*;

// Represents an exercise in a workout
public class Exercise implements Writable {
    private String name;
    private List<Set> sets; // each element represents a weight in each set
    private Boolean completed;

    public Exercise(String name)  {
        this.name = name;
        this.sets = new ArrayList<Set>();
    }

    // REQUIRES: weight > 0, reps > 0, rir >= 0
    // MODIFIES: this
    // EFFECTS: adds a set to the exercise
    public void addSet(int weight, int reps, int rir) {
        sets.add(new Set(weight, reps, rir));
    }

    // EFFECTS: returns the exercise in the format "name:"
    //          if there are no sets, returns "    1: ???kg: ??? reps || ???RIR"
    //         otherwise, returns the exercise with each set
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

    // EFFECTS: returns Exercise as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sets", setsToJson());
        return json;
    }

    // EFFECTS: returns sets in this exercise as a JSON array
    private JSONArray setsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Set s : sets) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}

