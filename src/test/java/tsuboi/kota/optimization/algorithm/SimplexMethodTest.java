package tsuboi.kota.optimization.algorithm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tsuboi.kota.optimization.constraint.EquationSymbol;
import tsuboi.kota.optimization.constraint.LinearConstraint;
import tsuboi.kota.optimization.constraint.LinearEquation;
import tsuboi.kota.optimization.function.LinearFunction;
import tsuboi.kota.optimization.problem.LinearProgrammingProblem;
import tsuboi.kota.optimization.solution.AbstractContinuousSolution;
import tsuboi.kota.optimization.solution.DoubleSolution;

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
     */
    @Test
    public void testRun() {
        System.out.println("run");
        LinearFunction linearFunction = new LinearFunction(new double[]{1, 2});
        LinearEquation[] linearEquations = new LinearEquation[]{
            new LinearEquation(new double[]{1, 1}, EquationSymbol.LESS_EQUAL, 6),
            new LinearEquation(new double[]{1, 3}, EquationSymbol.LESS_EQUAL, 12),
            new LinearEquation(new double[]{2, 1}, EquationSymbol.LESS_EQUAL, 10),
        };
        boolean[] nonnegative = new boolean[]{true, true};
        LinearConstraint linearConstraint = new LinearConstraint(linearEquations, nonnegative);
        LinearProgrammingProblem standardForm = new LinearProgrammingProblem(true, linearFunction, linearConstraint);
        SimplexMethod instance = new SimplexMethod(standardForm);
        AbstractContinuousSolution expResult = new DoubleSolution(3, 3);
        AbstractContinuousSolution result = instance.run();
        double delta = 1e6;
        for (int i = 0; i < 2; i++) {
            assertEquals(expResult.getValue(i), result.getValue(i), delta);
        }
    }

}
