package model;

import org.junit.jupiter.api.Test;

public class SetTest {
    private Set set;

    @Test
    public void testReturnSet() {
        set = new Set(100, 20, 10);
        assert set.returnSet().equals("100kg: 20 reps || 10RIR");
    }
}
