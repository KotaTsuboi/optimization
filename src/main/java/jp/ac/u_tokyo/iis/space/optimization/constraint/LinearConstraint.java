package jp.ac.u_tokyo.iis.space.optimization.constraint;

import java.util.Arrays;
import jp.ac.u_tokyo.iis.space.optimization.equation.EquationSymbol;
import jp.ac.u_tokyo.iis.space.optimization.equation.LinearEquation;
import java.util.Objects;

/**
 *
 * @author Kota
 */
public class LinearConstraint extends AbstractContinuousConstraint {

    private static final String RETURN;

    static {
        RETURN = "\n";
    }

    private final LinearEquation[] equations;
    private final double[][] coefficients;
    private final EquationSymbol[] symbols;
    private final double[] constants;
    private final int numEquation;
    private final int numVariable;

    public LinearConstraint(int numVariable) {
        equations = new LinearEquation[]{};
        coefficients = new double[][]{};
        symbols = new EquationSymbol[]{};
        constants = new double[]{};
        numEquation = 0;
        this.numVariable = numVariable;
    }

    public LinearConstraint(LinearEquation... equations) {
        Objects.requireNonNull(equations);

        this.equations = equations;
        numEquation = equations.length;
        numVariable = equations[0].getNumVariable();

        coefficients = new double[numEquation][];
        symbols = new EquationSymbol[numEquation];
        constants = new double[numEquation];

        for (int i = 0; i < numEquation; i++) {
            if (equations[i].getNumVariable() != numVariable) {
                throw new IllegalArgumentException("係数ベクトルの長さが異なります。");
            } else {
                coefficients[i] = equations[i].getCoefficients();
                symbols[i] = equations[i].getSymbol();
                constants[i] = equations[i].getConstant();
            }
        }
    }

    public LinearEquation[] getEquations() {
        return equations;
    }

    public double[][] getCoefficients() {
        return coefficients;
    }

    public EquationSymbol[] getSymbols() {
        return symbols;
    }

    public double[] getConstants() {
        return constants;
    }

    @Override
    public int getNumEquation() {
        return numEquation;
    }

    @Override
    public int getNumVariable() {
        return numVariable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (LinearEquation eq : equations) {
            sb.append(eq.toString());
            sb.append(RETURN);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (anObject instanceof LinearConstraint) {
            LinearConstraint aConstraint = (LinearConstraint) anObject;

            if (getNumEquation() == aConstraint.getNumEquation()) {
                Arrays.sort(getEquations());
                Arrays.sort(aConstraint.getEquations());

                for (int i = 0; i < getNumEquation(); i++) {
                    if (!getEquations()[i].equals(aConstraint.getEquations()[i])) {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }

}
