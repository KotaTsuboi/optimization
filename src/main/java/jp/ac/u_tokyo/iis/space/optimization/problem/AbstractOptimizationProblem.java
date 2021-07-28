package jp.ac.u_tokyo.iis.space.optimization.problem;

import java.util.Objects;
import jp.ac.u_tokyo.iis.space.optimization.function.AbstractObjectiveFunction;
import jp.ac.u_tokyo.iis.space.optimization.constraint.AbstractConstraint;
import jp.ac.u_tokyo.iis.space.optimization.boundary.AbstractBoundaryCondition;

/**
 *
 * @author Kota
 */
abstract public class AbstractOptimizationProblem {

    protected final boolean maximize;

    protected final int numEquation;
    protected final int numVariable;

    public AbstractOptimizationProblem(boolean maximize, AbstractObjectiveFunction objectiveFunction, AbstractConstraint constraint, AbstractBoundaryCondition boundary) {
        Objects.requireNonNull(objectiveFunction);

        this.maximize = maximize;

        if (objectiveFunction.getNumVariable() != constraint.getNumVariable()) {
            throw new IllegalArgumentException("変数の次元数が目的関数と制約条件で異なります。");
        }

        this.numEquation = constraint.getNumEquation();
        this.numVariable = constraint.getNumVariable();
    }

    public AbstractOptimizationProblem(boolean maximize, AbstractObjectiveFunction objectiveFunction) {
        this(maximize, objectiveFunction, null, null);
    }

    public int getNumEquation() {
        return numEquation;
    }

    public int getNumVariable() {
        return numVariable;
    }

    public boolean isMaximize() {
        return maximize;
    }

    @Override
    abstract public String toString();
}
