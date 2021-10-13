package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.problem.OptimizationProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.DiscretizedSolution;

/**
 *
 * @author Kota
 */
abstract public class DiscretizedAlgorithm extends OptimizationAlgorithm {

    public DiscretizedAlgorithm(OptimizationProblem problem) {
        super(problem);
    }

    @Override
    abstract DiscretizedSolution solve() throws UnboundedException, UnfeasibleException;

}
