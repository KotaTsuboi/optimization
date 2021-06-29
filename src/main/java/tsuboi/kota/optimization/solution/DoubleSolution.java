package tsuboi.kota.optimization.solution;

/**
 *
 * @author Kota
 */
public class DoubleSolution extends AbstractContinuousSolution {

    private final double[] value;

    public DoubleSolution(double... d) {
        this.value = d;
    }

    @Override
    public double getValue(int i) {
        return value[i];
    }
}
