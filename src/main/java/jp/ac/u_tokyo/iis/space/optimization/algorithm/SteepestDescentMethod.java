package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.problem.UnconstrainedContinuousOptimizationProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.ContinuousSolution;
import jp.ac.u_tokyo.iis.space.optimization.solution.DoubleSolution;

/**
 *
 * @author Kota
 */
public class SteepestDescentMethod extends ContinuousAlgorithm {

    private static final int INFINITY = 100000;
    private static final double DELTA = 0.1;

    public SteepestDescentMethod(UnconstrainedContinuousOptimizationProblem problem, double[] x0) {
        super(problem);
    }

    @Override
    ContinuousSolution solve() throws UnboundedException, UnfeasibleException {
        int step = 0;

        while (step < INFINITY) {
            if (hasConverged()) {
                return new DoubleSolution();
            }
        }

        return new DoubleSolution();
    }

    private boolean armijoCondition() {
        throw new UnsupportedOperationException();
    }

    private double backtrackingLineSearch() {
        throw new UnsupportedOperationException();
    }

    private boolean hasConverged() {
        throw new UnsupportedOperationException();
    }

    private double norm(double[] v) {
        double sum = 0;

        for (double x : v) {
            sum += Math.pow(x, 2);
        }

        return Math.sqrt(sum);
    }

}
