package lab3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void helloWorks() {
        assertEquals("Hello from Lab3!", Main.hello());
    }
}
