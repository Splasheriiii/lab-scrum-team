package lab3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
      @Test
    void task1() {
        assertEquals(Math.round(2885511.556), Math.round(Main.task1(30,26,12,3,1.8)));
    }
    @Test
    void task2() {
        var re2 = Main.task2(
                30,
                26,
                12,
                3
        );
        assertEquals(Math.round(9450), Math.round(re2.n2()));
        assertEquals(Math.round(1181.250), Math.round(re2.k()));
        assertEquals(Math.round(5.402), Math.round(re2.i()));
        assertEquals(Math.round(1328.906), Math.round(re2.K()));
        assertEquals(Math.round(306148.138), Math.round(re2.N()));
        assertEquals(Math.round(1632816.146), Math.round(re2.V()));
        assertEquals(Math.round(114805.552), Math.round(re2.P()));
        assertEquals(Math.round(1148.056), Math.round(re2.TkDays()));
        assertEquals(Math.round(9184.444), Math.round(re2.TkHours()));
        assertEquals(Math.round(544.272), Math.round(re2.B()));
        assertEquals(Math.round(728.988), Math.round(re2.tn()));
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void task3(int variant, double rate,double errors) {
        var res = Main.task3_1(3000,
                1.8,
                5,
                new double[]{2,3,4,5,6},
                new int[]{0,0,3,4,3},
                16,
                variant);
        if(rate == -1)
            assertTrue(res.Rate() < 0);
        else {
            assertEquals(Math.round(res.Errors()), Math.round(errors));
            assertEquals(Math.round(res.Rate()), Math.round(rate));
        }
    }

    static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(1, -1,         0),
                Arguments.of(2, -1,         0),
                Arguments.of(3, 3254.164,   8,894)
        );
    }
}