package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import java.util.stream.Stream;
import jp.ac.u_tokyo.iis.space.optimization.boundary.NonnegativeCondition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import jp.ac.u_tokyo.iis.space.optimization.equation.EquationSymbol;
import jp.ac.u_tokyo.iis.space.optimization.constraint.LinearConstraint;
import jp.ac.u_tokyo.iis.space.optimization.equation.LinearEquation;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.function.LinearFunction;
import jp.ac.u_tokyo.iis.space.optimization.problem.LinearProgrammingProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.AbstractContinuousSolution;
import jp.ac.u_tokyo.iis.space.optimization.solution.DoubleSolution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.*;

/**
 *
 * @author Kota
 */
public class SimplexMethodTest {

    public SimplexMethodTest() {
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
     * Test of run method, of class SimplexMethod.
     *
     * @param lp
     * @param expResult
     */
    @ParameterizedTest
    @MethodSource
    public void testRun(LinearProgrammingProblem lp, DoubleSolution expResult) {
        System.out.println("run");
        System.out.println(lp.toString());
        SimplexMethod instance = new SimplexMethod(lp);
        AbstractContinuousSolution result;
        try {
            result = instance.run();
        } catch (UnboundedException ex) {
            result = new DoubleSolution(-9999);
        } catch (UnfeasibleException ex) {
            result = new DoubleSolution(-99999);
        }
        double delta = 1e6;

        for (int i = 0; i < result.size(); i++) {
            assertEquals(expResult.getValue(i), result.getValue(i), delta);
        }
    }

    public static Stream<Arguments> testRun() {
        return Stream.of(
                arguments(
                        new LinearProgrammingProblem(
                                true,
                                new LinearFunction(1, 2),
                                new LinearConstraint(
                                        new LinearEquation(new double[]{1, 1}, EquationSymbol.LESS_EQUAL, 6),
                                        new LinearEquation(new double[]{1, 3}, EquationSymbol.LESS_EQUAL, 12),
                                        new LinearEquation(new double[]{2, 1}, EquationSymbol.LESS_EQUAL, 10)
                                ),
                                new NonnegativeCondition(new int[]{0, 1})
                        ),
                        new DoubleSolution(3, 3)
                ),
                arguments(
                        new LinearProgrammingProblem(
                                true,
                                new LinearFunction(4, 8, 10),
                                new LinearConstraint(
                                        new LinearEquation(new double[]{1, 1, 1}, EquationSymbol.LESS_EQUAL, 20),
                                        new LinearEquation(new double[]{3, 4, 6}, EquationSymbol.LESS_EQUAL, 100),
                                        new LinearEquation(new double[]{4, 5, 3}, EquationSymbol.LESS_EQUAL, 100)
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2})
                        ),
                        new DoubleSolution(0, 10, 10)
                ),
                arguments(
                        new LinearProgrammingProblem(
                                true,
                                new LinearFunction(1, 3, -1),
                                new LinearConstraint(
                                        new LinearEquation(new double[]{2, 2, -1}, EquationSymbol.LESS_EQUAL, 10),
                                        new LinearEquation(new double[]{3, -2, 1}, EquationSymbol.LESS_EQUAL, 10),
                                        new LinearEquation(new double[]{1, -3, 1}, EquationSymbol.LESS_EQUAL, 10)
                                ),
                                new NonnegativeCondition(new int[]{0, 1, 2})
                        ),
                        new DoubleSolution(-9999)
                ),
                arguments(
                        new LinearProgrammingProblem(
                                true,
                                new LinearFunction(10, 1),
                                new LinearConstraint(
                                        new LinearEquation(new double[]{1, 0}, EquationSymbol.LESS_EQUAL, 1),
                                        new LinearEquation(new double[]{20, 1}, EquationSymbol.LESS_EQUAL, 100)
                                ),
                                new NonnegativeCondition(new int[]{0, 1})
                        ),
                        new DoubleSolution(0, 100)
                )
        );
    }

}
