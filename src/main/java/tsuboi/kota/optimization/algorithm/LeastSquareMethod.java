package tsuboi.kota.optimization.algorithm;

import tsuboi.kota.optimization.dataset.ContinuousDataset2D;
import tsuboi.kota.optimization.solution.AbstractContinuousSolution;
import tsuboi.kota.optimization.solution.DoubleSolution;

/**
 *
 * @author Kota
 */
public class LeastSquareMethod extends AbstractOptimizationAlgorithm {

    ContinuousDataset2D dataset;

    public LeastSquareMethod(ContinuousDataset2D dataset) {
        this.dataset = dataset;
    }

    @Override
    public AbstractContinuousSolution run() {
        double a = dataset.covariance() / dataset.varianceX();
        double b = dataset.averageY() - a * dataset.averageX();
        return new DoubleSolution(a, b);
    }}

