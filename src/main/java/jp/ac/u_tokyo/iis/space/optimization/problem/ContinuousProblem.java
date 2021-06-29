package jp.ac.u_tokyo.iis.space.optimization.problem;

import jp.ac.u_tokyo.iis.space.optimization.boundary.ContinuousBoundaryCondition;
import jp.ac.u_tokyo.iis.space.optimization.constraint.ContinuousConstraint;
import jp.ac.u_tokyo.iis.space.optimization.function.ContinuousFunction;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;

/**
 *
 * @author Kota
 */
abstract public class ContinuousProblem extends OptimizationProblem {

    public ContinuousProblem(Orientation orientation, ContinuousFunction objectiveFunction, ContinuousConstraint constraint, ContinuousBoundaryCondition boundary) {
        super(orientation, objectiveFunction, constraint, boundary);
    }

    public ContinuousProblem(Orientation orientation, ContinuousFunction objectiveFunction) {
        super(orientation, objectiveFunction);
    }
}
