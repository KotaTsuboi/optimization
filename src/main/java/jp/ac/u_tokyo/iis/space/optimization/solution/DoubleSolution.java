package jp.ac.u_tokyo.iis.space.optimization.solution;

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
        return value.length == 0;
    }

    @Override
    public double getValue(int i) {
        return value[i];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\\(");

        for (int j = 0; j < value.length; j++) {
            sb.append("x_{");
            sb.append(j + 1);
            sb.append("}");
            sb.append("=");
            sb.append(value[j]);
            sb.append("<br>");
        }

        sb.append("\\)");
        return sb.toString();
    }

    @Override
    public int size() {
        return value.length;
    }
}
