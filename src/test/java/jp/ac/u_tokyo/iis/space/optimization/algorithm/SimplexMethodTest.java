package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import java.util.stream.Stream;
import jp.ac.u_tokyo.iis.space.optimization.boundary.NonnegativeCondition;
import jp.ac.u_tokyo.iis.space.optimization.constraint.LinearConstraint;
import jp.ac.u_tokyo.iis.space.optimization.equation.LinearEquation;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.function.LinearFunction;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;
import jp.ac.u_tokyo.iis.space.optimization.problem.LinearProgrammingProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.ContinuousSolution;
import jp.ac.u_tokyo.iis.space.optimization.solution.DoubleSolution;
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
public class SimplexMethodTest {

    /**
     * Test of solve method, of class SimplexMethod.
     *
     * @param lp
     * @param expResult
     */
    @ParameterizedTest
    @MethodSource
    public void testSolve(LinearProgrammingProblem lp, DoubleSolution expResult) {
        System.out.println("solve");
        System.out.println(lp.toString());
        SimplexMethod instance = new SimplexMethod(lp);
        ContinuousSolution result;
        try {
            result = instance.solve();
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

    public static Stream<Arguments> testSolve() {
        return Stream.of(
                arguments(
                        new LinearProgrammingProblem(
                                Orientation.MAXIMIZE,
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
                                Orientation.MAXIMIZE,
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
                                Orientation.MAXIMIZE,
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
                                Orientation.MAXIMIZE,
                                new LinearFunction(10, 1),
                                new LinearConstraint(
                                        new LinearEquation(new double[]{1, 0}, EquationSymbol.LESS_EQUAL, 1),
                                        new LinearEquation(new double[]{20, 1}, EquationSymbol.LESS_EQUAL, 100)
                                ),
                                new NonnegativeCondition(new int[]{0, 1})
                        ),
                        new DoubleSolution(0, 100)
                ),
                 arguments(
                        new LinearProgrammingProblem(
                                Orientation.MAXIMIZE,
                                new LinearFunction(1),
                                new LinearConstraint(
                                        new LinearEquation(new double[]{1}, EquationSymbol.EQUAL, 1)
                                ),
                                new NonnegativeCondition(new int[]{})
                        ),
                        new DoubleSolution(1)
                )
        );
    }

}
