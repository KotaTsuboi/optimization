package jp.ac.u_tokyo.iis.space.optimization.equation;

import java.util.stream.Stream;
import jp.ac.u_tokyo.iis.space.optimization.symbol.EquationSymbol;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.*;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Kota
 */
public class LinearEquationTest {

    /**
     * Test of toString method, of class LinearEquation.
     */
    @ParameterizedTest
    @MethodSource
    public void testToString(LinearEquation equation, String expResult) {
        System.out.println("toString");
        LinearEquation instance = equation;
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testToString() {
        return Stream.of(
                arguments(
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        "1.0 * X1 + 2.0 * X2 + 3.0 * X3 = 0.0"
                )
        );
    }

    /**
     * Test of equals method, of class LinearEquation.
     */
    @ParameterizedTest
    @MethodSource
    public void testEquals(LinearEquation equation1, LinearEquation equation2, boolean expResult) {
        System.out.println("equals");
        boolean result = equation1.equals(equation2);
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testEquals() {
        return Stream.of(
                arguments(
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        true
                ),
                arguments(
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        new LinearEquation(new double[]{-1, 2, 3}, EquationSymbol.EQUAL, 0),
                        false
                ),
                arguments(
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.LESS_EQUAL, 0),
                        false
                ),
                arguments(
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, -1),
                        false
                )
        );
    }

    /**
     * Test of compareTo method, of class LinearEquation.
     */
    @ParameterizedTest
    @MethodSource
    public void testCompareTo(LinearEquation equation1, LinearEquation equation2, int expResult) {
        System.out.println("compareTo");
        int result = equation1.compareTo(equation2);
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testCompareTo() {
        return Stream.of(
                arguments(
                        new LinearEquation(new double[]{-1, 2, 3}, EquationSymbol.EQUAL, 0),
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        -1
                ),
                arguments(
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        0
                ),
                arguments(
                        new LinearEquation(new double[]{2, 2, 3}, EquationSymbol.EQUAL, 0),
                        new LinearEquation(new double[]{1, 2, 3}, EquationSymbol.EQUAL, 0),
                        1
                )
        );
    }

}
