package persistance;

import model.Template;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of list of templates to file
    public void write(List<Template> templates) {
        JSONObject json = new JSONObject();
        json.put("templates", templatesToJson(templates));
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: returns templates in this workroom as a JSON array
    private JSONArray templatesToJson(List<Template> templates) {
        JSONArray jsonArray = new JSONArray();

        for (Template t : templates) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
