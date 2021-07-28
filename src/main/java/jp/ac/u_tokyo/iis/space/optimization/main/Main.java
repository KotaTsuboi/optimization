package jp.ac.u_tokyo.iis.space.optimization.main;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.u_tokyo.iis.space.optimization.dataset.ContinuousDataset2D;
import jp.ac.u_tokyo.iis.space.optimization.algorithm.LeastSquareMethod;
import jp.ac.u_tokyo.iis.space.optimization.solution.AbstractContinuousSolution;

/**
 *
 * @author Kota
 */
public class Main {
    public static void main(String[] args) {
        int n = 1000;
        double aExpect = 1;
        double bExpect = 2;
        Random random = new Random();
        double[] x = random.doubles().limit(n).toArray();
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            y[i] = aExpect * x[i] + bExpect + random.nextGaussian();
        }

        ContinuousDataset2D dataset = new ContinuousDataset2D(x, y);
        LeastSquareMethod lsm = new LeastSquareMethod(dataset);
        AbstractContinuousSolution solution = lsm.run();
        double a = solution.getValue(0);
        double b = solution.getValue(1);

        int nRegression = 100;
        double[] xExpect = new double[nRegression];
        double[] yExpect = new double[nRegression];
        double[] xRegression = new double[nRegression];
        double[] yRegression = new double[nRegression];

        double x0 = 0;
        for (int i = 0; i < nRegression; i++) {
            xExpect[i] = x0 + i / (double) nRegression;
            yExpect[i] = aExpect * xExpect[i] + bExpect;
            xRegression[i] = xExpect[i];
            yRegression[i] = a * xRegression[i] + b;
        }

        ScatterAndLineChart chart = new ScatterAndLineChart("Least Square Method (n = " + n + ")", "x", "y");
        chart.addData("raw", x, y);
        chart.addData("expected", xExpect, yExpect);
        chart.addData("regression", xRegression, yRegression);
        chart.createChart();
        try {
            chart.saveChartAsSVG("./lsm.svg", 1600, 900);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
