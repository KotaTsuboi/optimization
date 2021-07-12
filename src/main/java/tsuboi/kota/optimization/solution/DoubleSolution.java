package tsuboi.kota.optimization.solution;

/**
 *
 * @author Kota
 */
public class DoubleSolution extends AbstractContinuousSolution {

    private final double[] value;

    public DoubleSolution() {
        this.value = new double[]{};
    }

    public DoubleSolution(double... d) {
        this.value = d;
    }

    @Override
    public boolean isNull() {
        return !(value.length == 0);
    }

    @Override
    public double getValue(int i) {
        return value[i];
    }
}
