package jp.ac.u_tokyo.iis.space.optimization.function;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.*;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Kota
 */
public class LinearFunctionTest {

    /**
     * Test of toString method, of class LinearFunction.
     */
    @ParameterizedTest
    @MethodSource
    public void testToString(LinearFunction function, String expResult) {
        System.out.println("toString");
        String result = function.toString();
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testToString() {
        return Stream.of(
                arguments(
                        new LinearFunction(16, 2, -3),
                        "16.0 * X1 + 2.0 * X2-3.0 * X3"
                )
        );
    }

    /**
     * Test of equals method, of class LinearFunction.
     */
    @ParameterizedTest
    @MethodSource
    public void testEquals(LinearFunction function1, LinearFunction function2, boolean expResult) {
        boolean result = function1.equals(function2);
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testEquals() {
        return Stream.of(
                arguments(
                        new LinearFunction(16, 2, -3),
                        new LinearFunction(16, 2, -3),
                        true
                )
        );
    }

}
