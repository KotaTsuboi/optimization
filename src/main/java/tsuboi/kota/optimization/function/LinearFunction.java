/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsuboi.kota.optimization.function;

/**
 *
 * @author Kota
 */
public class LinearFunction extends AbstractContinuousFunction {

    private final double[] coefficients;
    private final int numVariable;

    public LinearFunction(double[] coefficients) {
        this.coefficients = coefficients;
        this.numVariable = coefficients.length;
    }

    public double getValue(double[] variable) {
        if (variable.length != coefficients.length) {
            throw new IllegalArgumentException("変数の次元数が不適切です。");
        }

        double sum = 0;

        for (int i = 0; i < variable.length; i++) {
            sum += coefficients[i] * variable[i];
        }

        return sum;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public int getNumVariable() {
        return numVariable;
    }
}
