package tsuboi.kota.optimization.constraint;

/**
 *
 * @author Kota
 */
public class LinearEquation extends AbstractEquation {

    private final double[] coefficients;
    private final EquationSymbol symbol;
    private final double constant;

    public LinearEquation(double[] coefficients, EquationSymbol symbol, double constant) {
        this.coefficients = coefficients;
        this.symbol = symbol;
        this.constant = constant;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public EquationSymbol getSymbol() {
        return symbol;
    }

    public double getConstant() {
        return constant;
    }

    public int getNumVariable() {
        return coefficients.length;
    }
}
