package find_bugs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void ternarySmell() {
        boolean res = false;
        try {
            App.ternarySmell(false, false);
        } catch (Exception e) {
            res = true;
        }
        assertTrue(res);
    }
    @Test void arraySmell() {
        boolean res = false;
        try {
            App.arraySmell(3);
        } catch (Exception e) {
            res = true;
        }
        assertTrue(res);
    }
    @Test void decimalSmell() {
        assertFalse(App.decimalSmell());
    }
    @Test void stringSmell() {
        assertFalse(App.stringSmell());
    }
}
