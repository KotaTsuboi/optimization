package jp.ac.u_tokyo.iis.space.optimization.problem;

import jp.ac.u_tokyo.iis.space.optimization.boundary.BoundaryCondition;
import jp.ac.u_tokyo.iis.space.optimization.boundary.ContinuousBoundaryCondition;
import jp.ac.u_tokyo.iis.space.optimization.constraint.Constraint;
import jp.ac.u_tokyo.iis.space.optimization.constraint.ContinuousConstraint;
import jp.ac.u_tokyo.iis.space.optimization.function.ContinuousFunction;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;

/**
 *
 * @author Kota
 */
abstract public class ContinuousProblem extends OptimizationProblem {

    protected final Constraint constraint;
    protected final BoundaryCondition boundary;

    public ContinuousProblem(Orientation orientation, ContinuousFunction objectiveFunction, ContinuousConstraint constraint, ContinuousBoundaryCondition boundary) {
        super(orientation, objectiveFunction);

        if (objectiveFunction.getNumVariable() != constraint.getNumVariable()) {
            throw new IllegalArgumentException("変数の次元数が目的関数と制約条件で異なります。");
        }

        this.constraint = constraint;
        this.boundary = boundary;
    }

    public ContinuousProblem(Orientation orientation, ContinuousFunction objectiveFunction) {
        super(orientation, objectiveFunction);
        this.constraint = null;
        this.boundary = null;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public BoundaryCondition getBoundary() {
        return boundary;
    }

    public int getNumEquation() {
        return constraint.getNumEquation();
    }

    public int getNumVariable() {
        return constraint.getNumVariable();
    }

    public int getNumBoundary() {
        return boundary.getNumBoundary();
    }

}
