package jp.ac.u_tokyo.iis.space.optimization.constraint;

import java.util.stream.Stream;
import jp.ac.u_tokyo.iis.space.optimization.equation.LinearEquation;
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
public class LinearConstraintTest {

    /**
     * Test of toString method, of class LinearConstraint.
     */
    @ParameterizedTest
    @MethodSource
    public void testToString(LinearConstraint constraint, String expResult) {
        System.out.println("toString");
        String result = constraint.toString();
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testToString() {
        return Stream.of(
                arguments(
                        new LinearConstraint(
                                new LinearEquation(new double[]{1, -6, 0}, EquationSymbol.GREATER_EQUAL, 4),
                                new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.EQUAL, 10)
                        ),
                        "1.0 * X1-6.0 * X2 >= 4.0"
                        + "\n + 3.0 * X2 + 7.0 * X3 <= -5.0"
                        + "\n1.0 * X1 + 1.0 * X2 + 1.0 * X3 = 10.0"
                        + "\n"
                )
        );
    }

    /**
     * Test of equals method, of class LinearConstraint.
     */
    @ParameterizedTest
    @MethodSource
    public void testEquals(LinearConstraint constraint1, LinearConstraint constraint2, boolean expResult) {
        System.out.println("equals");
        boolean result = constraint1.equals(constraint2);
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testEquals() {
        return Stream.of(
                arguments(
                        new LinearConstraint(
                                new LinearEquation(new double[]{1, -6, 0}, EquationSymbol.GREATER_EQUAL, 4),
                                new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.EQUAL, 10)
                        ),
                        new LinearConstraint(
                                new LinearEquation(new double[]{1, -6, 0}, EquationSymbol.GREATER_EQUAL, 4),
                                new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.EQUAL, 10)
                        ),
                        true
                )
        );
    }

}
