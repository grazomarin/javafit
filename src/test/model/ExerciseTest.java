package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {
    private Exercise excercise;

    @BeforeEach
    public void setup() {
        excercise = new Exercise("Bench Press");
    }

    @Test
    public void addSetTest() {
        excercise.addSet(100, 20, 10);
        assertEquals(1, excercise.getSets().size());
        assertEquals("Bench Press", excercise.getName());
    }

    @Test
    public void printExcerciseTest() {
        assertEquals("Bench Press:\n    1: ???kg: ??? reps || ???RIR\n", excercise.returnExercise());
        excercise.addSet(100, 20, 10);
        excercise.addSet(120, 10, 10);
        assertEquals("Bench Press:\n    1: 100kg: 20 reps || 10RIR\n    2: 120kg: 10 reps || 10RIR\n", excercise.returnExercise());
    }

}
