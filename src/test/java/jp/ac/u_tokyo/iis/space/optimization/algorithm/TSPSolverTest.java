package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import java.util.stream.Stream;
import jp.ac.u_tokyo.iis.space.optimization.function.DistanceCalculator;
import jp.ac.u_tokyo.iis.space.optimization.problem.TravelingSalesmanProblem;
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
public class TSPSolverTest {

    /**
     * Test of solve method, of class TSPSolver.
     */
    @ParameterizedTest
    @MethodSource
    public void testSolve(TSPSolver instance, PermutationSolution expResult) throws Exception {
        System.out.println("solve");
        PermutationSolution result = instance.solve();

        System.out.println("result: ");
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.getOrder(i) + ", ");
        }
        System.out.println("");

        for (int i = 0; i < result.size(); i++) {
            assertEquals(expResult.getOrder(i), result.getOrder(i));
        }
    }

    public static Stream<Arguments> testSolve() {
        return Stream.of(
                arguments(
                        new TSPSolver(
                                new TravelingSalesmanProblem(
                                        new DistanceCalculator(
                                                new double[][]{
                                                    {0, 0},
                                                    {1, 0},
                                                    {1, 1},
                                                    {0, 1}
                                                }
                                        )
                                ),
                                new PermutationSolution(
                                        new int[]{0, 2, 1, 3}
                                )
                        ),
                        new PermutationSolution(
                                new int[]{2, 1, 0, 3}
                        )
                )
        );
    }

}
