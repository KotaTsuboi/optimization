package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.problem.OptimizationProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.Solution;

/**
 *
 * @author Kota
 */
abstract public class OptimizationAlgorithm {

    protected OptimizationProblem problem;

    public OptimizationAlgorithm(OptimizationProblem problem) {
        this.problem = problem;
    }

    abstract Solution solve() throws UnboundedException, UnfeasibleException;

}
