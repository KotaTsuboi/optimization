package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import java.util.Arrays;
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
public class InsertionIteratorTest {

    public InsertionIteratorTest() {
    }

    /**
     * Test of hasNext method, of class InsertionIterator.
     */
    @ParameterizedTest
    @MethodSource
    public void testHasNext(int[] order, boolean expResult) {
        System.out.println("hasNext");
        PermutationSolution solution = new PermutationSolution(order);
        InsertionIterator instance = new InsertionIterator(solution);
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> testHasNext() {
        return Stream.of(
                arguments(
                        new int[]{0, 1, 2, 3, 4},
                        true
                )
        );
    }

    /**
     * Test of next method, of class InsertionIterator.
     */
    @ParameterizedTest
    @MethodSource
    public void testNext(int[] order, int[][] expResults) {
        System.out.println("next");
        PermutationSolution solution = new PermutationSolution(order);
        InsertionIterator instance = new InsertionIterator(solution);
        PermutationSolution result;

        for (int[] expResult : expResults) {
            result = instance.next();
            System.out.println(Arrays.toString(expResult));
            for (int i = 0; i < solution.size(); i++) {
                System.out.print(result.getOrder(i) + ", ");
            }
            System.out.println("");
            for (int i = 0; i < solution.size(); i++) {
                assertEquals(expResult[i], result.getOrder(i));
            }
        }
    }

    public static Stream<Arguments> testNext() {
        return Stream.of(
                arguments(
                        new int[]{0, 1, 2, 3},
                        new int[][]{
                            {1, 0, 2, 3},
                            {1, 2, 0, 3},
                            {1, 2, 3, 0},
                            {0, 2, 1, 3},
                            {0, 2, 3, 1},
                            {2, 0, 1, 3},
                            {0, 1, 3, 2},
                            {3, 0, 1, 2},
                            {0, 3, 1, 2}
                        }
                )
        );
    }

}
