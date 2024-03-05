package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.*;

// Represents a template for a Workout
public class Template implements Writable {
    private String name;
    private List<Exercise> exercise;

    public Template(String name) {
        this.name = name;
        this.exercise = new ArrayList<Exercise>();
    }

    // MODIFIES: this
    // EFFECTS: adds an exercise to the template
    public void addExercise(Exercise exercise) {
        this.exercise.add(exercise);
    }

//    public void removeExercise(Exercise exercise) {
//        // TODO add ability to remove exercise
//    }

    public void addSet(int humanIndex, int weight, int reps, int rir) {
        exercise.get(humanIndex - 1).addSet(weight, reps, rir);
    }

    // REQUIRES: command is a valid index from 1 to exercises.size()
    // EFFECTS: returns true if the command is a valid index
    // TODO add try catch for invalid input
    public boolean validHumanIndex(String command) {
        int index = Integer.parseInt(command);
        return index > 0 && index <= exercise.size();
    }

    // REQUIRES: exerciseName has a non-zero length and has at least 1 exercise
    // EFFECTS: prints the template
    public String returnTemplate() {
        StringBuilder output = new StringBuilder(name + " template:\n");
        for (int i = 1; i <= exercise.size(); i++) {
            output.append(i).append(". ").append(exercise.get(i - 1).returnExercise());
        }
        return output.toString();
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExercise() {
        return exercise;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("exercises", exerciseToJson());
        return json;
    }

    // EFFECTS: returns exercises in this template as a JSON array
    private JSONArray exerciseToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : exercise) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
