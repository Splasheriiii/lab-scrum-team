package lab5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void helloWorks() {
        assertEquals("Hello from Lab5!", Main.hello());
    }
}
