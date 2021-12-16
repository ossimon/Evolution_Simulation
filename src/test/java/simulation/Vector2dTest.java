package simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    public void precedesTest() {
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(2, 2);
        Vector2d v3 = new Vector2d(0, 0);
        Vector2d v4 = new Vector2d(0, 1);
        Vector2d v5 = new Vector2d(1, 0);
        Vector2d v6 = new Vector2d(2, 0);
        Vector2d v7 = new Vector2d(2, 2);

        Assertions.assertTrue(v1.precedes(v1), "TEST1");
        Assertions.assertTrue(v1.precedes(v2), "TEST2");
        Assertions.assertFalse(v1.precedes(v3), "TEST3");
        Assertions.assertFalse(v1.precedes(v4), "TEST4");
        Assertions.assertFalse(v1.precedes(v5), "TEST5");
        Assertions.assertFalse(v1.precedes(v6), "TEST6");
        Assertions.assertTrue(v1.precedes(v7), "TEST7");
    }
}
