package tsuboi.kota.optimization.constraint;

import java.util.Objects;

/**
 *
 * @author Kota
 */
public class LinearEquation extends AbstractEquation {

    private static final String PLUS;
    private static final String TIMES;
    private static final String VARIABLE;

    static {
        PLUS = " + ";
        TIMES = " * ";
        VARIABLE = "X";
    }

    private final double[] coefficients;
    private final EquationSymbol symbol;
    private final double constant;
    private final int numVariable;

    public LinearEquation(double[] coefficients, EquationSymbol symbol, double constant) {
        Objects.requireNonNull(coefficients);
        Objects.requireNonNull(symbol);

        this.coefficients = coefficients;
        this.symbol = symbol;
        this.constant = constant;
        numVariable = coefficients.length;
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
        return numVariable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < numVariable; j++) {
            if (j != 0 && coefficients[j] > 0) {
                sb.append(PLUS);
            }

            if (coefficients[j] != 0) {
                sb.append(coefficients[j]);
                sb.append(TIMES);
                sb.append(VARIABLE);
                sb.append(j + 1);
            }
        }

        sb.append(symbol.toString());
        sb.append(constant);
        return sb.toString();
    }
}
