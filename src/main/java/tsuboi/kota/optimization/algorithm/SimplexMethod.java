package tsuboi.kota.optimization.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import org.ejml.simple.SimpleMatrix;
import tsuboi.kota.optimization.problem.LinearProgrammingProblem;
import tsuboi.kota.optimization.solution.AbstractContinuousSolution;
import tsuboi.kota.optimization.solution.DoubleSolution;

/**
 *
 * @author Kota
 */
public class SimplexMethod extends AbstractOptimizationAlgorithm {

    private final SimpleMatrix B;
    private final SimpleMatrix N;
    private final SimpleMatrix b;
    private final SimpleMatrix cb;
    private final SimpleMatrix cn;

    private SimpleMatrix bBar;
    private SimpleMatrix reducedCost;

    private final int m;
    private final int n;

    private final ArrayList<Integer> variableIndexes;

    public SimplexMethod(LinearProgrammingProblem lp) {
        if (!lp.isStandardForm()) {
            throw new IllegalArgumentException("標準形のみサポートしています。");
        }

        N = new SimpleMatrix(lp.getCoefficietns());

        m = lp.getNumEquation();
        n = lp.getNumVariable();

        double[][] constantsArray = new double[m][1];
        for (int i = 0; i < m; i++) {
            constantsArray[i] = new double[]{lp.getConstants()[i]};
        }
        b = new SimpleMatrix(constantsArray);

        double[][] costsArray = new double[n][1];
        for (int i = 0; i < n; i++) {
            costsArray[i] = new double[]{lp.getCosts()[i]};
        }
        cn = new SimpleMatrix(costsArray);

        B = SimpleMatrix.identity(m);
        cb = new SimpleMatrix(m, 1);

        variableIndexes = new ArrayList<>();

        for (int i = n; i < n + m; i++) {
            variableIndexes.add(i);
        }

        for (int i = 0; i < n; i++) {
            variableIndexes.add(i);
        }
    }

    @Override
    public AbstractContinuousSolution run() {
        System.out.println("最大係数規則で計算します。");
        boolean firstTime = true;
        DoubleSolution solution = calculate(firstTime);

        if (solution == null) {
            System.out.println("巡回しました。最小添字規則に変更します。");
            firstTime = false;
            solution = calculate(firstTime);

            if (solution == null) {
                System.out.println("最小添字規則でも最適解が見つかりませんでした。");
                System.exit(1);
            }
        }

        return solution;
    }

    private DoubleSolution calculate(boolean firstTime) {
        int step = 0;
        int upperBoundary = 100000;

        while (step < upperBoundary) {
            System.out.println("step = " + step);
            bBar = B.solve(b);

            if (step == 0) {
                for (int i = 0; i < m; i++) {
                    if (bBar.get(i) < 0) {
                        System.out.println("非負制約を満たさないため、実行可能基底解ではありません。");
                        System.exit(1);
                    }
                }
            }

            reducedCost = cn.minus(N.transpose().mult(B.invert().transpose().mult(cb)));

            DoubleSolution optimalSolution;
            if (firstTime) {
                optimalSolution = largestCoefficientRule();
            } else {
                optimalSolution = smallestScriptRule();
            }

            if (optimalSolution != null) {
                return optimalSolution;
            }

            step++;
        }

        return null;
    }

    private DoubleSolution largestCoefficientRule() {
        double max = 0;
        int kMax = 0;

        for (int k = 0; k < n; k++) {
            if (reducedCost.get(k) > max) {
                max = reducedCost.get(k);
                kMax = k;
            }
        }

        if (max > 0) {
            swap(kMax);
        } else {
            return new DoubleSolution(extractOptimalSolution());
        }

        return null;
    }

    private DoubleSolution smallestScriptRule() {
        boolean finish = true;

        for (int k = 0; k < n; k++) {
            if (reducedCost.get(k) > 0) {
                swap(k);
                finish = false;
                break;
            }
        }

        if (finish) {
            return new DoubleSolution(extractOptimalSolution());
        }

        return null;
    }

    private double[] extractOptimalSolution() {
        double[] optimalSolution = new double[n];

        for (int i = 0; i < n; i++) {
            if (variableIndexes.indexOf(i) < m) {
                optimalSolution[i] = bBar.get(variableIndexes.indexOf(i));
            } else {
                optimalSolution[i] = 0;
            }
        }

        return optimalSolution;
    }

    private void swap(int k) {
        SimpleMatrix aBarK = B.invert().mult(N.extractVector(false, k));

        double min = -1;
        int iSwap = -1;
        boolean first = true;

        for (int i = 0; i < m; i++) {
            if (aBarK.get(i) > 0) {
                double theta = bBar.get(i) / aBarK.get(i);

                if (theta < min || first) {
                    min = theta;
                    iSwap = i;
                    first = false;
                }
            }
        }

        SimpleMatrix ai = B.extractVector(false, iSwap);
        double[] aiArray = new double[m];

        for (int i = 0; i < m; i++) {
            aiArray[i] = ai.get(i);
        }

        SimpleMatrix ak = N.extractVector(false, k);
        double[] akArray = new double[m];

        for (int i = 0; i < m; i++) {
            akArray[i] = ak.get(i);
        }

        B.setColumn(iSwap, 0, akArray);
        N.setColumn(k, 0, aiArray);

        SimpleMatrix cbi = cb.extractVector(true, iSwap);
        SimpleMatrix cnk = cn.extractVector(true, k);

        cb.setRow(iSwap, 0, cnk.get(0));
        cn.setRow(k, 0, cbi.get(0));

        Collections.swap(variableIndexes, m + k, iSwap);
    }
}
