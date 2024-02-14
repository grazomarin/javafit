package model;

import java.util.*;

// Represents a template for a Workout
public class Template {
    private String name;
    private List<String> excerciseNames;

    public Template(String name, List<String> excerciseNames) {
        this.name = name;
        this.excerciseNames = excerciseNames;
    }

    public void addExcercise(Excercise excercise) {
        // TODO
    }

    public void removeExcercise(Excercise excercise) {
        // TODO
    }

    public String getName() {
        return name;
    }

    public String printTemplate() {
        return name + "List of Excercises";
    }
}
