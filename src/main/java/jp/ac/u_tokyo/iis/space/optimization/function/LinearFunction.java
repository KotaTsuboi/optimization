package jp.ac.u_tokyo.iis.space.optimization.function;

import java.util.Objects;

/**
 *
 * @author Kota
 */
public class LinearFunction extends DifferentiableFunction {

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

    private void checkNumVariable(int n) {
        if (n != numVariable) {
            throw new IllegalArgumentException("変数の次元数が不適切です。");
        }
    }

    public double getCost(int i) {
        return costs[i];
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

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (anObject instanceof LinearFunction) {
            LinearFunction aFunction = (LinearFunction) anObject;

            if (toString().equals(aFunction.toString())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ContinuousFunction differential(int x) {
        return new ConstantFunction(costs[x]);
    }

}
