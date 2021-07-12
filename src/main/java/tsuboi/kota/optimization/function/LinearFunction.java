package tsuboi.kota.optimization.function;

import java.util.Objects;

/**
 *
 * @author Kota
 */
public class LinearFunction extends AbstractContinuousFunction {

    private static final String PLUS;
    private static final String TIMES;
    private static final String VARIABLE;

    static {
        PLUS = " + ";
        TIMES = " * ";
        VARIABLE = "X";
    }

    private final double[] costs;
    private final int numVariable;

    public LinearFunction(double... costs) {
        Objects.requireNonNull(costs);

        this.costs = costs;
        this.numVariable = costs.length;
    }

    public double getValue(double[] variable) {
        if (variable.length != numVariable) {
            throw new IllegalArgumentException("変数の次元数が不適切です。");
        }

        double sum = 0;

        for (int j = 0; j < numVariable; j++) {
            sum += costs[j] * variable[j];
        }

        return sum;
    }

    public double[] getCosts() {
        return costs;
    }

    @Override
    public int getNumVariable() {
        return numVariable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < numVariable; j++) {
            if (j != 0 && costs[j] > 0) {
                sb.append(PLUS);
            }

            if (costs[j] != 0) {
                sb.append(costs[j]);
                sb.append(TIMES);
                sb.append(VARIABLE);
                sb.append(j + 1);
            }
        }

        return sb.toString();
    }
}
