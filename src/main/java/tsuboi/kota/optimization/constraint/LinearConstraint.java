package tsuboi.kota.optimization.constraint;

/**
 *
 * @author Kota
 */
public class LinearConstraint extends AbstractContinuousConstraint {

    private final double[][] coefficients;
    private final EquationSymbol[] symbols;
    private final double[] constants;
    private final boolean[] nonnegative;
    private final int numRow;
    private final int numCol;

    public LinearConstraint(LinearEquation[] linearEquations, boolean[] nonnegative) {
        numRow = linearEquations.length;
        numCol = linearEquations[0].getNumVariable();

        coefficients = new double[numRow][];
        symbols = new EquationSymbol[numRow];
        constants = new double[numRow];

        for (int i = 0; i < numRow; i++) {
            if (linearEquations[i].getNumVariable() != numCol) {
                throw new IllegalArgumentException("係数ベクトルの長さが異なります。");
            } else {
                coefficients[i] = linearEquations[i].getCoefficients();
                symbols[i] =linearEquations[i].getSymbol();
                constants[i] = linearEquations[i].getConstant();
            }
        }

        if (nonnegative.length != numCol) {
            throw new IllegalArgumentException("非負制約boolean配列の長さと変数の数が異なります。");
        }

        this.nonnegative = nonnegative;
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

    public boolean[] getNonnegative() {
        return nonnegative;
    }

    @Override
    public int getNumEquation() {
        return numRow;
    }

    @Override
    public int getNumVariable() {
        return numCol;
    }
}
