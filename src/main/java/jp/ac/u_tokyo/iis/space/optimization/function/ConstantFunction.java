package jp.ac.u_tokyo.iis.space.optimization.function;

/**
 *
 * @author Kota
 */
public class ConstantFunction extends DifferentiableFunction {

    private final double value;

    public ConstantFunction(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int getNumVariable() {
        return 0;
    }

    @Override
    public ContinuousFunction differential(int x) {
        return new ConstantFunction(0);
    }

}
