package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.solution.AbstractContinuousSolution;

/**
 *
 * @author Kota
 */
abstract public class AbstractOptimizationAlgorithm {

    abstract AbstractContinuousSolution run() throws UnboundedException, UnfeasibleException;
}
