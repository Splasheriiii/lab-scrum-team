package lab4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void helloWorks() {
        assertEquals("Hello from Lab4!", Main.hello());
    }
}
