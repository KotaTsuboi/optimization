package jp.ac.u_tokyo.iis.space.optimization.function;

import jp.ac.u_tokyo.iis.space.optimization.solution.PermutationSolution;

/**
 *
 * @author Kota
 */
public class DistanceCalculator extends ObjectiveFunction {

    private final double[][] xyList;

    public DistanceCalculator(double[][] xyList) {
        this.xyList = xyList;
    }

    @Override
    public int getNumVariable() {
        return xyList.length;
    }

    public double calculate(PermutationSolution solution) {
        double distance = 0;

        for (int i = 0; i < getNumVariable(); i++) {
            double[] xy1 = xyList[solution.getOrder(i)];
            double x1 = xy1[0];
            double y1 = xy1[1];

            double[] xy2 = xyList[solution.getOrder((i + 1) % solution.size())];
            double x2 = xy2[0];
            double y2 = xy2[1];

            distance += Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }

        return distance;
    }

}
