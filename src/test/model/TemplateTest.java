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
        assertEquals(2, template.getExercises().size());
    }

    @Test
    public void validHumanIndexTest() {
        assertFalse(template.validHumanIndex("0"));
        assertFalse(template.validHumanIndex("3"));
        assertFalse(template.validHumanIndex("a"));

        assertTrue(template.validHumanIndex("1"));
        assertTrue(template.validHumanIndex("2"));
    }


    @Test
    public void addSetTest() {
        template.addSet(1, 100, 20, 10);
        template.addSet(1, 120, 20, 10);
        template.addSet(2, 140, 20, 9);
        assertEquals(2, template.getExercises().get(0).getSets().size());
        assertEquals(1, template.getExercises().get(1).getSets().size());
    }

    @Test
    public void printTemplateTest() {
        assertEquals("Test", template.getName());
        StringBuilder expected = new StringBuilder("Test template:\n");
        expected.append("1. ").append(template.getExercises().get(0).returnExercise());
        expected.append("2. ").append(template.getExercises().get(1).returnExercise());
        assertEquals(expected.toString(), template.returnTemplate());
    }

}
