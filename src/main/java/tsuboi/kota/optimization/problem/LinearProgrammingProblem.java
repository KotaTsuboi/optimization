package tsuboi.kota.optimization.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import tsuboi.kota.optimization.constraint.EquationSymbol;
import tsuboi.kota.optimization.constraint.LinearConstraint;
import tsuboi.kota.optimization.constraint.LinearEquation;
import tsuboi.kota.optimization.function.LinearFunction;

/**
 *
 * @author Kota
 */
public class LinearProgrammingProblem extends AbstractContinuousOptimizationProblem {

    private static final String MAXIMIZE;
    private static final String MINIMIZE;
    private static final String SUBJECT_TO;
    private static final String TAB1;
    private static final String TAB2;
    private static final String TAB_FOR_SUBJECT_TO;
    private static final String RETURN;

    static {
        MAXIMIZE = "maximize";
        MINIMIZE = "minimize";
        SUBJECT_TO = "subject to";
        TAB1 = "      ";
        TAB2 = "    ";
        TAB_FOR_SUBJECT_TO = "          ";
        RETURN = "\n";
    }

    private final LinearFunction objectiveFunction;
    private final LinearConstraint constraint;

    public LinearProgrammingProblem(boolean maximize, LinearFunction objectiveFunction, LinearConstraint constraint) {
        super(maximize, objectiveFunction, constraint);
        this.objectiveFunction = objectiveFunction;
        this.constraint = constraint;

    }

    public LinearProgrammingProblem(boolean maximize, LinearFunction objectiveFunction) {
        super(maximize, objectiveFunction);
        this.objectiveFunction = objectiveFunction;
        this.constraint = new LinearConstraint(numVariable);
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

    public boolean isStandardForm() {
        if (isMaximize()) {
            for (EquationSymbol symbol : constraint.getSymbols()) {
                if (symbol != EquationSymbol.LESS_EQUAL) {
                    return false;
                }
            }

            for (boolean isNonnegative : constraint.getNonnegative()) {
                if (!isNonnegative) {
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
                .toAllGreater();
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
        LinearProgrammingProblem lp = new LinearProgrammingProblem(true, functionStandard, constraint);
        return lp;
    }

    private LinearProgrammingProblem toAllNonnegative() {
        int numVariableStandard = numVariable;

        List<Double> costsList = new ArrayList<>();
        List<List<Double>> coefficientsList = new ArrayList<>();
        List<Boolean> nonnegativeList = new ArrayList<>();

        for (int i = 0; i < numEquation; i++) {
            coefficientsList.add(new ArrayList<>());
        }

        for (int j = 0; j < numVariable; j++) {
            costsList.add(getCosts()[j]);

            for (int i = 0; i < numEquation; i++) {
                coefficientsList.get(i).add(getCoefficietns()[i][j]);
            }

            nonnegativeList.add(true);

            if (!constraint.getNonnegative()[j]) {
                costsList.add(-getCosts()[j]);

                for (int i = 0; i < numEquation; i++) {
                    coefficientsList.get(i).add(-getCoefficietns()[i][j]);
                }

                nonnegativeList.add(true);
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

        boolean[] nonnegatives = new boolean[numVariableStandard];

        for (int j = 0; j < numVariableStandard; j++) {
            nonnegatives[j] = nonnegativeList.get(j);
        }

        LinearConstraint constraintStandard = new LinearConstraint(equations, nonnegatives);
        LinearProgrammingProblem lp = new LinearProgrammingProblem(maximize, functionStandard, constraintStandard);
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

        LinearConstraint constraintStandard = new LinearConstraint(equations, constraint.getNonnegative());
        LinearProgrammingProblem lp = new LinearProgrammingProblem(maximize, objectiveFunction, constraintStandard);
        return lp;
    }

    private LinearProgrammingProblem toAllGreater() {
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
                    for (int j = 0; j < numVariable; j++) {
                        coefficients[j] *= -1;
                    }
                    constant *= -1;
                    break;
                case GREATER_EQUAL:
                    break;
                default:
                    System.out.println("対応していない記号です。");
                    System.exit(1);
            }

            equations[i] = new LinearEquation(coefficients, EquationSymbol.GREATER_EQUAL, constant);
        }

        LinearConstraint constraintStandard = new LinearConstraint(equations, constraint.getNonnegative());
        LinearProgrammingProblem lp = new LinearProgrammingProblem(maximize, objectiveFunction, constraintStandard);
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

        sb.append(TAB1);
        sb.append(objectiveFunction.toString());
        sb.append(RETURN);

        for (int i = 0; i < numEquation; i++) {
            if (i == 0) {
                sb.append(SUBJECT_TO);
            } else {
                sb.append(TAB_FOR_SUBJECT_TO);
            }

            sb.append(TAB2);
            sb.append(constraint.getEquations()[i].toString());
            sb.append(RETURN);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (anObject instanceof LinearProgrammingProblem) {
            LinearProgrammingProblem aLP = (LinearProgrammingProblem) anObject;

            if (toString().equals(aLP.toString())) {
                return true;
            }
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
