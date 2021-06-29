package jp.ac.u_tokyo.iis.space.optimization.problem;

import java.util.Objects;
import jp.ac.u_tokyo.iis.space.optimization.boundary.BoundaryCondition;
import jp.ac.u_tokyo.iis.space.optimization.constraint.Constraint;
import jp.ac.u_tokyo.iis.space.optimization.function.ObjectiveFunction;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;

/**
 *
 * @author Kota
 */
abstract public class OptimizationProblem {

    protected final Orientation orientation;

    protected final ObjectiveFunction objectiveFunction;
    protected final Constraint constraint;
    protected final BoundaryCondition boundary;

    public OptimizationProblem(Orientation orientation, ObjectiveFunction objectiveFunction, Constraint constraint, BoundaryCondition boundary) {
        Objects.requireNonNull(objectiveFunction);

        this.orientation = orientation;

        if (objectiveFunction.getNumVariable() != constraint.getNumVariable()) {
            throw new IllegalArgumentException("変数の次元数が目的関数と制約条件で異なります。");
        }

        this.objectiveFunction = objectiveFunction;
        this.constraint = constraint;
        this.boundary = boundary;
    }

    public OptimizationProblem(Orientation orientation, ObjectiveFunction objectiveFunction) {
        this(orientation, objectiveFunction, null, null);
    }

    public ObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
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

    public boolean isMaximize() {
        return orientation.isMaximize();
    }

    @Override
    abstract public String toString();
}
