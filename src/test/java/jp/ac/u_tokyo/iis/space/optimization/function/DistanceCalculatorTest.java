package jp.ac.u_tokyo.iis.space.optimization.function;

import java.util.stream.Stream;
import jp.ac.u_tokyo.iis.space.optimization.solution.PermutationSolution;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.*;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Kota
 */
public class DistanceCalculatorTest {

    public DistanceCalculatorTest() {
    }

    /**
     * Test of getNumVariable method, of class DistanceCalculator.
     */
    @ParameterizedTest
    @MethodSource
    public void testGetNumVariable(DistanceCalculator instance, int expResult) {
        System.out.println("getNumVariable");
        int result = instance.getNumVariable();
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testGetNumVariable() {
        return Stream.of(
                arguments(
                        new DistanceCalculator(
                                new double[][]{
                                    {0, 0},
                                    {1, 0},
                                    {0, 1}
                                }
                        ),
                        3
                )
        );
    }

    /**
     * Test of calculate method, of class DistanceCalculator.
     */
    @ParameterizedTest
    @MethodSource
    public void testCalculate(DistanceCalculator instance, PermutationSolution solution, double expResult) {
        System.out.println("calculate");
        double result = instance.calculate(solution);
        assertEquals(expResult, result, 1e-3);
    }

    public static Stream<Arguments> testCalculate() {
        return Stream.of(
                arguments(
                        new DistanceCalculator(
                                new double[][]{
                                    {0, 0},
                                    {1, 0},
                                    {1, 1},
                                    {0, 1}
                                }
                        ),
                        new PermutationSolution(
                                new int[]{
                                    0, 1, 2, 3
                                }
                        ),
                        4
                )
        );
    }

}
