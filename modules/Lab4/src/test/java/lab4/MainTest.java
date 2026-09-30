package lab4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

class MainTest {
    @Test
    void find_bugs() throws Exception {
        var res = Main.analyze(Path.of("data", "find_bugs", "app", "src", "main").toString());
        assertNotNull(res);
        assertTrue(res.contains("UnsynchronizedStaticFormatter:"));
    }
    @Test
    void library() throws Exception {
        var res = Main.analyze(Path.of("data", "library", "src").toString());
        assertNotNull(res);
        assertTrue(res.contains("ShortClassName:"));
    }
    @Test
    void colt() throws Exception {
        var res = Main.analyze(Path.of("data", "colt", "src").toString());
        assertNotNull(res);
        assertTrue(res.contains("MissingOverride:"));
        assertTrue(res.contains("CloneMethodReturnTypeMustMatchClassName:"));
        assertTrue(res.contains("CloneMethodMustImplementCloneable:"));
    }
}
