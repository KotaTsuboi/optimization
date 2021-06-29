package tsuboi.kota.optimization.dataset;

/**
 *
 * @author Kota
 */
public class ContinuousDataset2D extends ContinuousDataset {

    private final ContinuousDataset1D x;
    private final ContinuousDataset1D y;
    private final int n;

    public ContinuousDataset2D(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Length of x and y differ.");
        }
        this.x = new ContinuousDataset1D(x);
        this.y = new ContinuousDataset1D(y);
        this.n = x.length;
    }

    public double minX() {
        return x.min();
    }

    public double maxX() {
        return x.max();
    }

    public double minY() {
        return y.min();
    }

    public double maxY() {
        return y.max();
    }

    public double sumX() {
        return x.sum();
    }

    public double sumY() {
        return y.sum();
    }

    public double averageX() {
        return x.average();
    }

    public double averageY() {
        return y.average();
    }

    public double varianceX() {
        return x.variance();
    }

    public double varianceY() {
        return y.variance();
    }

    public double covariance() {
        double averageX = averageX();
        double averageY = averageY();
        double sum = 0;

        for (int i = 0; i < n; i++) {
            sum += (x.get(i) - averageX) * (y.get(i) - averageY);
        }

        sum /= n;
        return sum;
    }
}
