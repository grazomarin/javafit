package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TemplateTest {
    private Template template;

    @BeforeEach
    public void setup() {
        template = new Template("Test");
        template.addExercise(new Exercise("Bench Press"));
        template.addExercise(new Exercise("Leg Press"));
    }

    @Test
    public void addExerciseTest() {
        assertEquals(2, template.getExercise().size());
    }

    @Test
    public void validHumanIndexTest() {
        assertFalse(template.validHumanIndex("0"));
        assertTrue(template.validHumanIndex("1"));
        assertTrue(template.validHumanIndex("2"));
        assertFalse(template.validHumanIndex("3"));
    }

    @Test
    public void addSetTest() {
        template.addSet(1, 100, 20, 10);
        template.addSet(1, 120, 20, 10);
        template.addSet(2, 140, 20, 9);
        assertEquals(2, template.getExercise().get(0).getSets().size());
        assertEquals(1, template.getExercise().get(1).getSets().size());
    }

    @Test
    public void printTemplateTest() {
        assertEquals("Test", template.getName());
        StringBuilder expected = new StringBuilder("Test template:\n");
        expected.append("1. ").append(template.getExercise().get(0).returnExercise());
        expected.append("2. ").append(template.getExercise().get(1).returnExercise());
        assertEquals(expected.toString(), template.returnTemplate());
    }

}
