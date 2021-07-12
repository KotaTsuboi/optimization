package tsuboi.kota.optimization.problem;

import tsuboi.kota.optimization.function.AbstractContinuousFunction;
import tsuboi.kota.optimization.constraint.AbstractContinuousConstraint;

/**
 *
 * @author Kota
 */
abstract public class AbstractContinuousOptimizationProblem extends AbstractOptimizationProblem {

    public AbstractContinuousOptimizationProblem(boolean maximize, AbstractContinuousFunction objectiveFunction, AbstractContinuousConstraint constraint) {
        super(maximize, objectiveFunction, constraint);
    }

    public AbstractContinuousOptimizationProblem(boolean maximize, AbstractContinuousFunction objectiveFunction) {
        super(maximize, objectiveFunction);
    }
}
