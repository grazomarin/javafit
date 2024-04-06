package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.*;

// Represents a template for a Workout
public class Template implements Writable {
    private String name;
    private List<Exercise> exercises;

    public Template(String name) {
        this.name = name;
        this.exercises = new ArrayList<Exercise>();
        EventLog.getInstance().logEvent(new Event("Created template: " + name));
    }

    // MODIFIES: this
    // EFFECTS: adds an exercise to the template
    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        EventLog.getInstance().logEvent(new Event("Added exercise: " + exercise.getName() + " to template: " + name));
    }

//    public void removeExercise(Exercise exercise) {
//        // TODO add ability to remove exercise
//    }

    // REQUIRES: weight > 0, reps > 0, 10 >= rir >= 0
    // MODIFIES: this
    public void addSet(int humanIndex, int weight, int reps, int rir) {
        exercises.get(humanIndex - 1).addSet(weight, reps, rir);
    }

    // REQUIRES: command is a valid index from 1 to exercises.size()
    // EFFECTS: returns true if the command is a valid index
    public boolean validHumanIndex(String command) {
        try {
            int index = Integer.parseInt(command);
            return index > 0 && index <= exercises.size();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // REQUIRES: exerciseName has a non-zero length and has at least 1 exercise
    // EFFECTS: prints the template
    public String returnTemplate() {
        StringBuilder output = new StringBuilder(name + " template:\n");
        for (int i = 1; i <= exercises.size(); i++) {
            output.append(i).append(". ").append(exercises.get(i - 1).returnExercise());
        }
        return output.toString();
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    // EFFECTS: returns Template as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: returns exercises in this template as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
