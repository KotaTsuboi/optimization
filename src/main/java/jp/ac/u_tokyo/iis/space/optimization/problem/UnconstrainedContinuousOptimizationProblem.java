package jp.ac.u_tokyo.iis.space.optimization.problem;

import jp.ac.u_tokyo.iis.space.optimization.boundary.ContinuousBoundaryCondition;
import jp.ac.u_tokyo.iis.space.optimization.constraint.ContinuousConstraint;
import jp.ac.u_tokyo.iis.space.optimization.function.ContinuousFunction;
import jp.ac.u_tokyo.iis.space.optimization.function.DifferentiableFunction;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;

/**
 *
 * @author Kota
 */
public class UnconstrainedContinuousOptimizationProblem extends ContinuousProblem {

    public UnconstrainedContinuousOptimizationProblem(Orientation orientation, ContinuousFunction objectiveFunction, ContinuousConstraint constraint, ContinuousBoundaryCondition boundary) {
        super(orientation, objectiveFunction, constraint, boundary);
    }

    public UnconstrainedContinuousOptimizationProblem(Orientation orientation, ContinuousFunction objectiveFunction) {
        super(orientation, objectiveFunction);
    }

    public ContinuousFunction getGradient(int x) {
        DifferentiableFunction differentiableFunction = (DifferentiableFunction) objectiveFunction;
        return differentiableFunction.differential(x);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
