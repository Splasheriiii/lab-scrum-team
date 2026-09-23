package lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void helloWorks() {
        assertEquals("Hello from Lab2!", Main.hello());
    }
}
