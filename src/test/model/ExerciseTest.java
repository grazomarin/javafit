package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {
    private Exercise exercise;

    @BeforeEach
    public void setup() {
        exercise = new Exercise("Bench Press");
    }

    @Test
    public void addSetTest() {
        exercise.addSet(100, 20, 10);
        assertEquals(1, exercise.getSets().size());
        assertEquals("Bench Press", exercise.getName());
    }

    @Test
    public void printExerciseTest() {
        assertEquals("Bench Press:\n    1: ???kg: ??? reps || ???RIR\n", exercise.returnExercise());
        exercise.addSet(100, 20, 10);
        exercise.addSet(120, 10, 10);
        assertEquals("Bench Press:\n    1: 100kg: 20 reps || 10RIR\n    2: 120kg: 10 reps || 10RIR\n", exercise.returnExercise());
    }

}
