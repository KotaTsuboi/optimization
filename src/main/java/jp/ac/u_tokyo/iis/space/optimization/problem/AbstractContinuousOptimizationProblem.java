package jp.ac.u_tokyo.iis.space.optimization.problem;

import jp.ac.u_tokyo.iis.space.optimization.function.AbstractContinuousFunction;
import jp.ac.u_tokyo.iis.space.optimization.constraint.AbstractContinuousConstraint;
import jp.ac.u_tokyo.iis.space.optimization.boundary.ContinuousBoundaryCondition;

/**
 *
 * @author Kota
 */
abstract public class AbstractContinuousOptimizationProblem extends AbstractOptimizationProblem {

    public AbstractContinuousOptimizationProblem(boolean maximize, AbstractContinuousFunction objectiveFunction, AbstractContinuousConstraint constraint, ContinuousBoundaryCondition boundary) {
        super(maximize, objectiveFunction, constraint, boundary);
    }

    public AbstractContinuousOptimizationProblem(boolean maximize, AbstractContinuousFunction objectiveFunction) {
        super(maximize, objectiveFunction);
    }
}
