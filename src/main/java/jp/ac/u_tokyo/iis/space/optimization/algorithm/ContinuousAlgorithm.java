package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.problem.ContinuousProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.ContinuousSolution;

/**
 *
 * @author Kota
 */
abstract public class ContinuousAlgorithm extends OptimizationAlgorithm {

    public ContinuousAlgorithm(ContinuousProblem problem) {
        super(problem);
    }

    @Override
    abstract ContinuousSolution solve() throws UnboundedException, UnfeasibleException;

}
