package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.problem.LinearProgrammingProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.DoubleSolution;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author Kota
 */
public class SimplexMethod extends ContinuousAlgorithm {

    private final SimpleMatrix B;
    private final SimpleMatrix N;
    private final SimpleMatrix b;
    private final SimpleMatrix cb;
    private final SimpleMatrix cn;

    private SimpleMatrix bBar;
    private SimpleMatrix reducedCost;

    private final int m;
    private final int n;

    private final int initialNumVariable;
    private final int[] nonnegativeIndexes;
    private final boolean isStandardForm;

    private final ArrayList<Integer> variableIndexes;

    public SimplexMethod(LinearProgrammingProblem lp) {
        super(lp);
        Objects.requireNonNull(lp);

        initialNumVariable = lp.getNumVariable();
        nonnegativeIndexes = new int[lp.getNumBoundary()];

        int count = 0;
        for (int i = 0; i < lp.getNumVariable(); i++) {
            if (lp.isNonnegative(i)) {
                nonnegativeIndexes[count] = i;
                count++;
            }
        }

        if (!lp.isStandardForm()) {
            lp = lp.toStandardForm();
            isStandardForm = false;
        } else {
            isStandardForm = true;
        }

        m = lp.getNumEquation();
        n = lp.getNumVariable();

        double[][] coefficients = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                coefficients[i][j] = lp.getCoefficient(i, j);
            }
        }
        N = new SimpleMatrix(coefficients);

        double[][] constantsArray = new double[m][1];

        for (int j = 0; j < m; j++) {
            constantsArray[j] = new double[]{lp.getConstant(j)};
        }

        b = new SimpleMatrix(constantsArray);

        double[][] costsArray = new double[n][1];

        for (int i = 0; i < n; i++) {
            costsArray[i] = new double[]{lp.getCost(i)};
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
    public DoubleSolution solve() throws UnboundedException, UnfeasibleException {
        System.out.println("Largest coefficient rule.");
        boolean firstTime = true;
        DoubleSolution solution = calculate(firstTime);

        if (solution.isNull()) {
            System.out.println("Cycling occured. Smallest script rule.");
            firstTime = false;
            solution = calculate(firstTime);

            if (solution.isNull()) {
                System.out.println("Optimum solution not found.");
                solution = new DoubleSolution();
            }
        }

        return rebuildSolution(solution);
    }

    private DoubleSolution rebuildSolution(DoubleSolution solution) {
        if (solution.isNull()) {
            return solution;
        }

        if (isStandardForm) {
            return solution;
        }

        double[] solutionArray = new double[initialNumVariable];
        int j = 0;
        int i = 0;

        while (j < initialNumVariable) {
            if (contains(nonnegativeIndexes, j)) {
                solutionArray[j] = solution.getValue(i);
            } else {
                solutionArray[j] = solution.getValue(i) - solution.getValue(i + 1);
                i++;
            }
            j++;
            i++;
        }
        DoubleSolution solutionNew = new DoubleSolution(solutionArray);
        return solutionNew;
    }

    private boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    private DoubleSolution calculate(boolean firstTime) throws UnboundedException, UnfeasibleException {
        int step = 0;
        int upperBoundary = 100000;

        while (step < upperBoundary) {
            bBar = B.solve(b);

            if (step == 0) {
                for (int i = 0; i < m; i++) {
                    if (bBar.get(i) < 0) {
                        throw new UnfeasibleException("This is not feasible solution.");
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

            if (!optimalSolution.isNull()) {
                return optimalSolution;
            }

            step++;
        }

        return new DoubleSolution();
    }

    private DoubleSolution largestCoefficientRule() throws UnboundedException {
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

        return new DoubleSolution();
    }

    private DoubleSolution smallestScriptRule() throws UnboundedException {
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

        return new DoubleSolution();
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

    private void swap(int k) throws UnboundedException {
        SimpleMatrix aBarK = B.invert().mult(N.extractVector(false, k));

        double min = -1;
        int iSwap = -1;
        boolean first = true;
        boolean bounded = false;

        for (int i = 0; i < m; i++) {
            if (aBarK.get(i) > 0) {
                bounded = true;
                double theta = bBar.get(i) / aBarK.get(i);

                if (theta < min || first) {
                    min = theta;
                    iSwap = i;
                    first = false;
                }
            }
        }

        if (!bounded) {
            throw new UnboundedException("The optimization problem is unbounded.");
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
