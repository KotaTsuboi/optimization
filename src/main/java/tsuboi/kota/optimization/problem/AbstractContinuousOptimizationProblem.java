package tsuboi.kota.optimization.problem;

import tsuboi.kota.optimization.function.AbstractContinuousFunction;
import tsuboi.kota.optimization.constraint.AbstractContinuousConstraint;

/**
 *
 * @author Kota
 */
abstract public class AbstractContinuousOptimizationProblem extends AbstractOptimizationProblem {

    public AbstractContinuousOptimizationProblem(boolean maximize, AbstractContinuousFunction continuousFunction, AbstractContinuousConstraint continuousConstraint) {
        super(maximize, continuousFunction, continuousConstraint);
    }
}
