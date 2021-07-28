package jp.ac.u_tokyo.iis.space.optimization.problem;

import java.util.stream.Stream;
import jp.ac.u_tokyo.iis.space.optimization.boundary.NonnegativeCondition;
import jp.ac.u_tokyo.iis.space.optimization.constraint.LinearConstraint;
import jp.ac.u_tokyo.iis.space.optimization.equation.EquationSymbol;
import jp.ac.u_tokyo.iis.space.optimization.equation.LinearEquation;
import jp.ac.u_tokyo.iis.space.optimization.function.LinearFunction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.*;

/**
 *
 * @author Kota
 */
public class LinearProgrammingProblemTest {

    public LinearProgrammingProblemTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of equals method, of class LinearProgrammingProblem.
     *
     * @param instance
     * @param instance2
     * @param expResult
     */
    @ParameterizedTest
    @MethodSource
    public void testEquals(LinearProgrammingProblem instance, LinearProgrammingProblem instance2, boolean expResult) {
        boolean result = instance.equals(instance2);
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testEquals() {
        return Stream.of(
                arguments(
                        new LinearProgrammingProblem(
                                false,
                                new LinearFunction(16, 2, -3),
                                new LinearConstraint(
                                        new LinearEquation[]{
                                            new LinearEquation(new double[]{1, -6, 0}, EquationSymbol.GREATER_EQUAL, 4),
                                            new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                            new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.EQUAL, 10)
                                        }
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2})
                        ),
                        new LinearProgrammingProblem(
                                false,
                                new LinearFunction(16, 2, -3),
                                new LinearConstraint(
                                        new LinearEquation[]{
                                            new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                            new LinearEquation(new double[]{1, -6, 0}, EquationSymbol.GREATER_EQUAL, 4),
                                            new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.EQUAL, 10)
                                        }
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2})
                        ),
                        true
                )
        );
    }

    /**
     * Test of isStandardForm method, of class LinearProgrammingProblem.
     *
     * @param instance
     * @param expResult
     */
    @ParameterizedTest
    @MethodSource
    public void testIsStandardForm(LinearProgrammingProblem instance, boolean expResult) {
        System.out.println("isStandardForm");
        boolean result = instance.isStandardForm();
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testIsStandardForm() {
        return Stream.of(
                arguments(
                        new LinearProgrammingProblem(
                                false,
                                new LinearFunction(16, 2, -3),
                                new LinearConstraint(
                                        new LinearEquation[]{
                                            new LinearEquation(new double[]{1, -6, 0}, EquationSymbol.GREATER_EQUAL, 4),
                                            new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                            new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.EQUAL, 10)
                                        }
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2})
                        ),
                        false
                ),
                arguments(
                        new LinearProgrammingProblem(
                                true,
                                new LinearFunction(-16, -2, +3),
                                new LinearConstraint(
                                        new LinearEquation[]{
                                            new LinearEquation(new double[]{-1, 6, 0}, EquationSymbol.LESS_EQUAL, -4),
                                            new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                            new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.LESS_EQUAL, 10),
                                            new LinearEquation(new double[]{-1, -1, -1}, EquationSymbol.LESS_EQUAL, -10)
                                        }
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2})
                        ),
                        true
                )
        );
    }

    /**
     * Test of toStandardForm method, of class LinearProgrammingProblem.
     *
     * @param instance
     * @param expResult
     */
    @ParameterizedTest
    @MethodSource
    public void testToStandardForm(LinearProgrammingProblem instance, LinearProgrammingProblem expResult) {
        System.out.println("toStandardForm");
        LinearProgrammingProblem result = instance.toStandardForm();
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testToStandardForm() {
        return Stream.of(
                arguments(
                        new LinearProgrammingProblem(
                                false,
                                new LinearFunction(16, 2, -3),
                                new LinearConstraint(
                                        new LinearEquation[]{
                                            new LinearEquation(new double[]{1, -6, 0}, EquationSymbol.GREATER_EQUAL, 4),
                                            new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                            new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.EQUAL, 10)
                                        }
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2})
                        ),
                        new LinearProgrammingProblem(
                                true,
                                new LinearFunction(-16, -2, +3),
                                new LinearConstraint(
                                        new LinearEquation[]{
                                            new LinearEquation(new double[]{-1, 6, 0}, EquationSymbol.LESS_EQUAL, -4),
                                            new LinearEquation(new double[]{0, 3, 7}, EquationSymbol.LESS_EQUAL, -5),
                                            new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.LESS_EQUAL, 10),
                                            new LinearEquation(new double[]{-1, -1, -1}, EquationSymbol.LESS_EQUAL, -10)
                                        }
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2})
                        )
                ),
                arguments(
                        new LinearProgrammingProblem(
                                true,
                                new LinearFunction(5, 6, 3),
                                new LinearConstraint(
                                        new LinearEquation[]{
                                            new LinearEquation(new double[]{1, 0, -1}, EquationSymbol.GREATER_EQUAL, -10),
                                            new LinearEquation(new double[]{1, 0, -1}, EquationSymbol.LESS_EQUAL, 10),
                                            new LinearEquation(new double[]{10, 7, 4}, EquationSymbol.LESS_EQUAL, 50),
                                            new LinearEquation(new double[]{2, 0, -11}, EquationSymbol.GREATER_EQUAL, 15)
                                        }
                                ),
                                new NonnegativeCondition(new int[]{0, 2})
                        ),
                        new LinearProgrammingProblem(
                                true,
                                new LinearFunction(5, 6, -6, 3),
                                new LinearConstraint(
                                        new LinearEquation[]{
                                            new LinearEquation(new double[]{1, 0, 0, -1}, EquationSymbol.LESS_EQUAL, 10),
                                            new LinearEquation(new double[]{-1, 0, 0, 1}, EquationSymbol.LESS_EQUAL, 10),
                                            new LinearEquation(new double[]{10, 7, -7, 4}, EquationSymbol.LESS_EQUAL, 50),
                                            new LinearEquation(new double[]{-2, 0, 0, 11}, EquationSymbol.LESS_EQUAL, -15)
                                        }
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2, 3})
                        )
                )
        );
    }

}
