package persistance;

import model.Exercise;
import model.Set;
import model.Template;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            List<Template> templates = new ArrayList<Template>();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStorage() {
        try {
            List<Template> templates = new ArrayList<Template>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStorage.json");
            writer.open();
            writer.write(templates);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStorage.json");
            templates = reader.read();
            assertEquals(0, templates.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStorage() {
        try {
            List<Template> templates = new ArrayList<Template>();
            Template kamranTemplate = new Template("Kamran");
            kamranTemplate.addExercise(new Exercise("Bench Press"));
            kamranTemplate.addExercise(new Exercise("Hammer Curl"));
            kamranTemplate.addSet(1, 100, 10, 5);
            kamranTemplate.addSet(2, 10, 12, 6);
            kamranTemplate.addSet(2, 12, 11, 7);
            templates.add(kamranTemplate);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStorage.json");
            writer.open();
            writer.write(templates);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStorage.json");
            templates = reader.read();
            assertEquals(1, templates.size());
            assertEquals("Kamran", templates.get(0).getName());
            Template kamran = templates.get(0);
            assertEquals(2, kamran.getExercises().size());

            // Test For Bench Press
            Exercise benchPress = kamran.getExercises().get(0);
            assertEquals("Bench Press", benchPress.getName());
            assertEquals(1, benchPress.getSets().size());
            Set benchPressSet1 = benchPress.getSets().get(0);
            testSet(100, 10, 5, benchPressSet1);

            // Test For Hammer Curl
            Exercise hammerCurl = kamran.getExercises().get(1);
            assertEquals("Hammer Curl", hammerCurl.getName());
            assertEquals(2, hammerCurl.getSets().size());
            Set hammerCurlSet1 = hammerCurl.getSets().get(0);
            Set hammerCurlSet2 = hammerCurl.getSets().get(1);
            testSet(10, 12, 6, hammerCurlSet1);
            testSet(12, 11, 7, hammerCurlSet2);


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
