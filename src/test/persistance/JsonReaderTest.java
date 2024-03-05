package persistance;

import model.Exercise;
import model.Set;
import model.Template;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ArrayList<Template> templates = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStorage() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStorage.json");
        try {
            ArrayList<Template> templates = reader.read();
            assertEquals(0, templates.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStorage() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStorage.json");
        try {
            ArrayList<Template> templates = reader.read();
            assertEquals(1, templates.size());
            assertEquals("Kamran", templates.get(0).getName());
            Template kamranTemplate = templates.get(0);
            assertEquals(2, kamranTemplate.getExercises().size());

            // Test For Bench Press
            Exercise benchPress = kamranTemplate.getExercises().get(0);
            assertEquals("Bench Press", benchPress.getName());
            assertEquals(2, benchPress.getSets().size());
            Set benchPressSet1 = benchPress.getSets().get(0);
            testSet(19, 19, 1, benchPressSet1);
            Set benchPressSet2 = benchPress.getSets().get(1);
            testSet(100, 10, 5, benchPressSet2);

            // Test For Squat
            Exercise squat = kamranTemplate.getExercises().get(1);
            assertEquals("Squat", squat.getName());
            assertEquals(1, squat.getSets().size());
            Set squatSet1 = squat.getSets().get(0);
            testSet(20, 20, 1, squatSet1);


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
