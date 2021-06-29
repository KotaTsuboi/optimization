package tsuboi.kota.optimization.problem;

import tsuboi.kota.optimization.function.AbstractObjectiveFunction;
import tsuboi.kota.optimization.constraint.AbstractConstraint;

/**
 *
 * @author Kota
 */
abstract public class AbstractOptimizationProblem {

    protected final boolean maximize;

    protected final AbstractObjectiveFunction objectiveFunction;
    protected final AbstractConstraint constraint;

    protected final int numEquation;
    protected final int numVariable;

    public AbstractOptimizationProblem(boolean maximize, AbstractObjectiveFunction objectiveFunction, AbstractConstraint constraint) {
        this.maximize = maximize;
        this.objectiveFunction = objectiveFunction;
        this.constraint = constraint;

        if (objectiveFunction.getNumVariable() != constraint.getNumVariable()) {
            throw new IllegalArgumentException("変数の次元数が目的関数と制約条件で異なります。");
        }

        this.numEquation = constraint.getNumEquation();
        this.numVariable = constraint.getNumVariable();
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
}
