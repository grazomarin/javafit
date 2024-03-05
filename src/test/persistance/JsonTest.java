package persistance;

import model.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    public void testSet(int weight, int reps, int rir, Set set) {
        assertEquals(weight, set.getWeight());
        assertEquals(reps, set.getReps());
        assertEquals(rir, set.getRir());
    }
}
