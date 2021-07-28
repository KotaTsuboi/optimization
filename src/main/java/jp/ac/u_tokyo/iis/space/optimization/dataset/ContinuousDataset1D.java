package jp.ac.u_tokyo.iis.space.optimization.dataset;

import java.util.Arrays;

/**
 *
 * @author Kota
 */
public class ContinuousDataset1D {

    private final double[] value;
    private final int size;

    public ContinuousDataset1D(double[] value) {
        this.value = value;
        this.size = value.length;
    }

    public double min() {
        return Arrays.stream(value).min().getAsDouble();
    }

    public double max() {
        return Arrays.stream(value).max().getAsDouble();
    }

    public double sum() {
        return Arrays.stream(value)
                .sum();
    }

    public double average() {
        return sum() / size;
    }

    public double variance() {
        double average = average();
        double sum = Arrays.stream(value)
                .map(d -> Math.pow(d - average, 2))
                .sum();
        return sum / size;
    }

    public double standardDeviation() {
        return Math.sqrt(variance());
    }

    public int size() {
        return size;
    }

    public double get(int i) {
        return value[i];
    }
}
