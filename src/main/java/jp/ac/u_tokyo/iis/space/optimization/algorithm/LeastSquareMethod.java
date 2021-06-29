package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import jp.ac.u_tokyo.iis.space.optimization.dataset.ContinuousDataset2D;
import jp.ac.u_tokyo.iis.space.optimization.solution.ContinuousSolution;
import jp.ac.u_tokyo.iis.space.optimization.solution.DoubleSolution;

/**
 *
 * @author Kota
 */
public class LeastSquareMethod {

    ContinuousDataset2D dataset;

    public LeastSquareMethod(ContinuousDataset2D dataset) {
        this.dataset = dataset;
    }

    public ContinuousSolution solve() {
        double a = dataset.covariance() / dataset.varianceX();
        double b = dataset.averageY() - a * dataset.averageX();
        return new DoubleSolution(a, b);
    }
}
