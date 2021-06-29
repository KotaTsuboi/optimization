package tsuboi.kota.optimization.problem;

import tsuboi.kota.optimization.constraint.EquationSymbol;
import tsuboi.kota.optimization.constraint.LinearConstraint;
import tsuboi.kota.optimization.function.LinearFunction;

/**
 *
 * @author Kota
 */
public class LinearProgrammingProblem extends AbstractContinuousOptimizationProblem {

    private final LinearFunction linearFunction;
    private final LinearConstraint linearConstraint;

    public LinearProgrammingProblem(boolean maximize, LinearFunction linearFunction, LinearConstraint linearConstraint) {
        super(maximize, linearFunction, linearConstraint);
        this.linearFunction = linearFunction;
        this.linearConstraint = linearConstraint;
    }

    public double[][] getCoefficietns() {
        return linearConstraint.getCoefficients();
    }

    public double[] getConstants() {
        return linearConstraint.getConstants();
    }

    public double[] getCosts() {
        return linearFunction.getCoefficients();
    }

    public boolean isStandardForm() {
        if (isMaximize()) {
            for (EquationSymbol symbol : linearConstraint.getSymbols()) {
                if (!symbol.equals(EquationSymbol.LESS_EQUAL)) {
                    return false;
                }
            }

            for (boolean isNonnegative : linearConstraint.getNonnegative()) {
                if (!isNonnegative) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public LinearProgrammingProblem toStandardForm() {
        // ToDo: Make toStandardForm method.
        return null; 
    }
}
