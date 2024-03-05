package persistance;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads list of templates from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Template> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTemplateList(jsonObject.getJSONArray("templates"));
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses a list of templates from JSON array and returns it
    private ArrayList<Template> parseTemplateList(JSONArray jsonArray) {
        ArrayList<Template> templates = new ArrayList<>();

        for (Object json : jsonArray) {
            JSONObject nextTemplate = (JSONObject) json;
            addTemplate(templates, nextTemplate);
        }

        return templates;
    }

    // MODIFIES: templates
    // EFFECTS: parses template from JSON object and adds it to templates
    private void addTemplate(ArrayList<Template> templates, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray exercises = jsonObject.getJSONArray("exercises");
        Template template = new Template(name);

        for (Object json : exercises) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(nextExercise, template);
        }

        templates.add(template);
    }

    // MODIFIES: template
    // EFFECTS: parses exercise from JSON object and adds it to template
    private void addExercise(JSONObject jsonObject, Template template) {
        String name = jsonObject.getString("name");
        JSONArray sets = jsonObject.getJSONArray("sets");
        Exercise exercise = new Exercise(name);

        for (Object json : sets) {
            JSONObject nextSet = (JSONObject) json;
            addSet(nextSet, exercise);
        }

        template.addExercise(exercise);
    }

    // MODIFIES: exercise
    // EFFECTS: parses set from JSON object and adds it to exercise
    private void addSet(JSONObject jsonObject, Exercise exercise) {
        int weight = jsonObject.getInt("weight");
        int reps = jsonObject.getInt("reps");
        int rir = jsonObject.getInt("rir");
        exercise.addSet(weight, reps, rir);
    }
}
