package lab5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Random;

class MainTest {

    private static final double EPS = 0.000001;

    @Test
    void testH0401() {
        double result = Main.calculateH0401(8, 1400);

        assertEquals(
                0.994285714,
                result,
                EPS
        );
    }

    @Test
    void testH0501WhenRecoveryTimeExceedsAllowed() {
        double result = Main.calculateH0501(
                0.9303,
                0.75
        );

        assertEquals(
                0.806191551,
                result,
                EPS
        );
    }

    @Test
    void testH0501WhenRecoveryTimeIsAllowed() {
        double result = Main.calculateH0501(
                0.70,
                0.75
        );

        assertEquals(
                1.0,
                result,
                EPS
        );
    }

    @Test
    void testH0502WhenAllTimesAreAllowed() {

        double[] processingTimes = {
                6.0,
                7.5,
                8.0,
                9.2,
                10.0
        };

        double result = Main.calculateH0502(
                processingTimes,
                16.0
        );

        assertEquals(
                1.0,
                result,
                EPS
        );
    }

    @Test
    void testH0502WhenSomeTimeExceedsAllowed() {

        double[] processingTimes = {
                8.0,
                20.0
        };

        /*
         * Для 8 секунд:
         * 8 <= 16, поэтому оценка = 1.
         *
         * Для 20 секунд:
         * оценка = 16 / 20 = 0.8.
         *
         * Среднее:
         * (1 + 0.8) / 2 = 0.9.
         */

        double result = Main.calculateH0502(
                processingTimes,
                16.0
        );

        assertEquals(
                0.9,
                result,
                EPS
        );
    }

    @Test
    void testAverage() {

        double[] values = {
                0.5,
                0.7,
                0.9,
                1.1,
                1.3
        };

        double result = Main.average(values);

        assertEquals(
                0.9,
                result,
                EPS
        );
    }

    @Test
    void testGeneratedRecoverySample() {

        Random random = new Random(895L);

        double[] sample =
                Main.generateUniformSample(
                        random,
                        100,
                        0.5,
                        1.3
                );

        assertEquals(
                100,
                sample.length
        );

        for (double value : sample) {

            assertTrue(
                    value >= 0.5,
                    "Значение меньше нижней границы"
            );

            assertTrue(
                    value <= 1.3,
                    "Значение больше верхней границы"
            );
        }
    }

    @Test
    void testVariant4RecoverySampleAverage() {

        Random random = new Random(895L);

        double[] recoveryTimes =
                Main.generateUniformSample(
                        random,
                        100,
                        0.5,
                        1.3
                );

        double average =
                Main.average(recoveryTimes);

        assertEquals(
                0.930333813,
                average,
                EPS
        );
    }
}
