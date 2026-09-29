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
        assertEquals(2885511.556, Main.task1(30,26,12,3,1.8));
    }
    @Test
    void task2() {
        assertEquals(2885511.556, Main.task1(30,26,12,3,1.8));
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
                1);
        if(rate == -1)
            assertTrue(res.Rate() < 0);
        else {
            assertEquals(res.Errors(), errors);
            assertEquals(res.Rate(), rate);
        }
    }

    static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(1, -1,         0),
                Arguments.of(2, -1,         0),
                Arguments.of(3, 3254.164,   8.8938)
        );
    }
}