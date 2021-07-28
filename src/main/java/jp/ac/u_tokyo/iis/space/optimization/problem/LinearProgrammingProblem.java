package jp.ac.u_tokyo.iis.space.optimization.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jp.ac.u_tokyo.iis.space.optimization.equation.EquationSymbol;
import jp.ac.u_tokyo.iis.space.optimization.constraint.LinearConstraint;
import jp.ac.u_tokyo.iis.space.optimization.equation.LinearEquation;
import jp.ac.u_tokyo.iis.space.optimization.boundary.NonnegativeCondition;
import jp.ac.u_tokyo.iis.space.optimization.function.LinearFunction;

/**
 *
 * @author Kota
 */
public class LinearProgrammingProblem extends AbstractContinuousOptimizationProblem {

    private static final String MAXIMIZE;
    private static final String MINIMIZE;
    private static final String SUBJECT_TO;
    private static final String BOUND;
    private static final String RETURN;

    static {
        MAXIMIZE = "MAXIMIZE";
        MINIMIZE = "MINIMIZE";
        SUBJECT_TO = "SUBJECT TO";
        BOUND = "BOUND";
        RETURN = "\n";
    }

    private final LinearFunction objectiveFunction;
    private final LinearConstraint constraint;
    private final NonnegativeCondition boundary;

    public LinearProgrammingProblem(boolean maximize, LinearFunction objectiveFunction, LinearConstraint constraint, NonnegativeCondition boundary) {
        super(maximize, objectiveFunction, constraint, boundary);
        this.objectiveFunction = objectiveFunction;
        this.constraint = constraint;
        this.boundary = boundary;
    }

    public LinearProgrammingProblem(boolean maximize, LinearFunction objectiveFunction) {
        super(maximize, objectiveFunction);
        this.objectiveFunction = objectiveFunction;
        this.constraint = new LinearConstraint(numVariable);
        this.boundary = new NonnegativeCondition(new int[]{});
    }

    public double[][] getCoefficietns() {
        return constraint.getCoefficients();
    }

    public double[] getConstants() {
        return constraint.getConstants();
    }

    public double[] getCosts() {
        return objectiveFunction.getCosts();
    }

    public LinearFunction getObjectiveFunction() {
        return objectiveFunction;
    }

    public LinearConstraint getConstraint() {
        return constraint;
    }

    public NonnegativeCondition getBoundary() {
        return boundary;
    }

    public boolean isStandardForm() {
        if (isMaximize()) {
            for (EquationSymbol symbol : constraint.getSymbols()) {
                if (symbol != EquationSymbol.LESS_EQUAL) {
                    return false;
                }
            }

            for (int j = 0; j < numVariable; j++) {
                if (!boundary.isNonnegative(j)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public LinearProgrammingProblem toStandardForm() {
        return toMaximize()
                .toAllNonnegative()
                .toNoEqual()
                .toAllLess();
    }

    private LinearProgrammingProblem toMaximize() {
        double[] costs = new double[numVariable];

        for (int j = 0; j < numVariable; j++) {
            if (maximize) {
                costs[j] = getCosts()[j];
            } else {
                costs[j] = -getCosts()[j];
            }
        }

        LinearFunction functionStandard = new LinearFunction(costs);
        LinearProgrammingProblem lp = new LinearProgrammingProblem(true, functionStandard, constraint, boundary);
        return lp;
    }

    private LinearProgrammingProblem toAllNonnegative() {
        int numVariableStandard = 0;

        List<Double> costsList = new ArrayList<>();
        List<List<Double>> coefficientsList = new ArrayList<>();
        List<Integer> variableIndexList = new ArrayList<>();

        for (int i = 0; i < numEquation; i++) {
            coefficientsList.add(new ArrayList<>());
        }

        for (int j = 0; j < numVariable; j++) {
            costsList.add(getCosts()[j]);

            for (int i = 0; i < numEquation; i++) {
                coefficientsList.get(i).add(getCoefficietns()[i][j]);
            }

            variableIndexList.add(numVariableStandard);
            numVariableStandard++;

            if (!boundary.isNonnegative(j)) {
                costsList.add(-getCosts()[j]);

                for (int i = 0; i < numEquation; i++) {
                    coefficientsList.get(i).add(-getCoefficietns()[i][j]);
                }

                variableIndexList.add(numVariableStandard);
                numVariableStandard++;
            }
        }

        LinearFunction functionStandard = new LinearFunction(costsList.stream().mapToDouble(d -> d).toArray());
        LinearEquation[] equations = new LinearEquation[numEquation];

        for (int i = 0; i < numEquation; i++) {
            equations[i] = new LinearEquation(
                    coefficientsList.get(i).stream()
                            .mapToDouble(d -> d)
                            .toArray(),
                    constraint.getSymbols()[i],
                    getConstants()[i]
            );
        }

        NonnegativeCondition boundaryStandard = new NonnegativeCondition(variableIndexList.stream().mapToInt(i -> i).toArray());

        LinearConstraint constraintStandard = new LinearConstraint(equations);
        LinearProgrammingProblem lp = new LinearProgrammingProblem(maximize, functionStandard, constraintStandard, boundaryStandard);
        return lp;
    }

    private LinearProgrammingProblem toNoEqual() {
        int numEquationStandard = numEquation;
        List<LinearEquation> equationList = new ArrayList<>();

        for (int i = 0; i < numEquation; i++) {
            if (constraint.getSymbols()[i] == EquationSymbol.EQUAL) {
                equationList.add(new LinearEquation(
                        constraint.getCoefficients()[i],
                        EquationSymbol.GREATER_EQUAL,
                        constraint.getConstants()[i]
                ));
                equationList.add(new LinearEquation(
                        constraint.getCoefficients()[i],
                        EquationSymbol.LESS_EQUAL,
                        constraint.getConstants()[i]
                ));
                numEquationStandard++;
            } else {
                equationList.add(constraint.getEquations()[i]);
            }
        }

        LinearEquation[] equations = new LinearEquation[numEquationStandard];

        for (int i = 0; i < numEquationStandard; i++) {
            equations[i] = equationList.get(i);
        }

        LinearConstraint constraintStandard = new LinearConstraint(equations);
        LinearProgrammingProblem lp = new LinearProgrammingProblem(maximize, objectiveFunction, constraintStandard, boundary);
        return lp;
    }

    private LinearProgrammingProblem toAllLess() {
        LinearEquation[] equations = new LinearEquation[numEquation];

        for (int i = 0; i < numEquation; i++) {
            double[] coefficients = new double[numVariable];
            System.arraycopy(getCoefficietns()[i], 0, coefficients, 0, numVariable);
            double constant = getConstants()[i];
            EquationSymbol symbol = constraint.getSymbols()[i];

            switch (symbol) {
                case LESS:
                case GREATER:
                    System.out.println("制約条件に\">\"または\"<\"が含まれているため、標準形に変換できません。");
                    System.exit(1);
                case EQUAL:
                    System.out.println("制約条件に\"=\"が含まれています。");
                    System.exit(1);
                case LESS_EQUAL:
                    break;
                case GREATER_EQUAL:
                    for (int j = 0; j < numVariable; j++) {
                        coefficients[j] *= -1;
                    }
                    constant *= -1;
                    break;
                default:
                    System.out.println("対応していない記号です。");
                    System.exit(1);
            }

            equations[i] = new LinearEquation(coefficients, EquationSymbol.LESS_EQUAL, constant);
        }

        LinearConstraint constraintStandard = new LinearConstraint(equations);
        LinearProgrammingProblem lp = new LinearProgrammingProblem(maximize, objectiveFunction, constraintStandard, boundary);
        return lp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (maximize) {
            sb.append(MAXIMIZE);
        } else {
            sb.append(MINIMIZE);
        }

        sb.append(RETURN);
        sb.append(objectiveFunction.toString());
        sb.append(RETURN);
        sb.append(SUBJECT_TO);
        sb.append(RETURN);

        for (int i = 0; i < numEquation; i++) {
            sb.append(constraint.getEquations()[i].toString());
            sb.append(RETURN);
        }

        sb.append(BOUND);
        sb.append(RETURN);
        sb.append(boundary.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (anObject instanceof LinearProgrammingProblem) {
            LinearProgrammingProblem aLP = (LinearProgrammingProblem) anObject;

            if (isMaximize() ^ aLP.isMaximize()) {
                return false;
            }

            if (!objectiveFunction.equals(aLP.getObjectiveFunction())) {
                return false;
            }

            if (!constraint.equals(aLP.getConstraint())) {
                return false;
            }

            if (!boundary.equals(aLP.getBoundary())) {
                return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.objectiveFunction);
        hash = 37 * hash + Objects.hashCode(this.constraint);
        return hash;
    }
}
