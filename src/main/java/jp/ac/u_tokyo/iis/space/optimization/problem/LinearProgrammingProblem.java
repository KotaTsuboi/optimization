package jp.ac.u_tokyo.iis.space.optimization.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jp.ac.u_tokyo.iis.space.optimization.boundary.NonnegativeCondition;
import jp.ac.u_tokyo.iis.space.optimization.constraint.LinearConstraint;
import jp.ac.u_tokyo.iis.space.optimization.equation.LinearEquation;
import jp.ac.u_tokyo.iis.space.optimization.function.LinearFunction;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;
import jp.ac.u_tokyo.iis.space.optimization.symbol.EquationSymbol;

/**
 *
 * @author Kota
 */
public class LinearProgrammingProblem extends ContinuousProblem {

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

    public LinearProgrammingProblem(Orientation orientation, LinearFunction objectiveFunction, LinearConstraint constraint, NonnegativeCondition boundary) {
        super(orientation, objectiveFunction, constraint, boundary);
    }

    public LinearProgrammingProblem(Orientation orientation, LinearFunction objectiveFunction) {
        super(orientation, objectiveFunction, new LinearConstraint(), new NonnegativeCondition(new int[]{}));
    }

    public double getCost(int i) {
        return ((LinearFunction) objectiveFunction).getCost(i);
    }

    public double getCoefficient(int i, int j) {
        return ((LinearConstraint) constraint).getCoefficient(i, j);
    }

    public double getConstant(int j) {
        return ((LinearConstraint) constraint).getConstant(j);
    }

    public boolean isNonnegative(int i) {
        return ((NonnegativeCondition) boundary).isNonnegative(i);
    }

    public boolean isStandardForm() {
        if (this.isMaximize()) {
            for (int i = 0; i < getNumEquation(); i++) {
                if (((LinearConstraint) constraint).getSymbol(i) != EquationSymbol.LESS_EQUAL) {
                    return false;
                }
            }

            for (int j = 0; j < getNumVariable(); j++) {
                if (!((NonnegativeCondition) boundary).isNonnegative(j)) {
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
        System.out.println("toMaximize");
        double[] costs = new double[getNumVariable()];

        for (int j = 0; j < getNumVariable(); j++) {
            if (this.isMaximize()) {
                costs[j] = getCost(j);
            } else {
                costs[j] = -getCost(j);
            }
        }

        LinearFunction functionStandard = new LinearFunction(costs);
        LinearProgrammingProblem lp = new LinearProgrammingProblem(Orientation.MAXIMIZE, functionStandard, (LinearConstraint) constraint, (NonnegativeCondition) boundary);
        return lp;
    }

    private LinearProgrammingProblem toAllNonnegative() {
        System.out.println("toAllNonnegative");
        int numVariableStandard = 0;

        List<Double> costsList = new ArrayList<>();
        List<List<Double>> coefficientsList = new ArrayList<>();
        List<Integer> variableIndexList = new ArrayList<>();

        for (int i = 0; i < getNumEquation(); i++) {
            coefficientsList.add(new ArrayList<>());
        }

        for (int j = 0; j < getNumVariable(); j++) {
            costsList.add(getCost(j));

            for (int i = 0; i < getNumEquation(); i++) {
                coefficientsList.get(i).add(getCoefficient(i, j));
            }

            variableIndexList.add(numVariableStandard);
            numVariableStandard++;

            if (!((NonnegativeCondition) boundary).isNonnegative(j)) {
                costsList.add(-getCost(j));

                for (int i = 0; i < getNumEquation(); i++) {
                    coefficientsList.get(i).add(-getCoefficient(i, j));
                }

                variableIndexList.add(numVariableStandard);
                numVariableStandard++;
            }
        }

        LinearFunction functionStandard = new LinearFunction(costsList.stream().mapToDouble(d -> d).toArray());
        LinearEquation[] equations = new LinearEquation[getNumEquation()];

        for (int i = 0; i < getNumEquation(); i++) {
            equations[i] = new LinearEquation(
                    coefficientsList.get(i).stream()
                            .mapToDouble(d -> d)
                            .toArray(),
                    ((LinearConstraint) constraint).getSymbol(i),
                    getConstant(i)
            );
        }

        NonnegativeCondition boundaryStandard = new NonnegativeCondition(variableIndexList.stream().mapToInt(i -> i).toArray());

        LinearConstraint constraintStandard = new LinearConstraint(equations);
        LinearProgrammingProblem lp = new LinearProgrammingProblem(orientation, functionStandard, constraintStandard, boundaryStandard);
        return lp;
    }

    private LinearProgrammingProblem toNoEqual() {
        System.out.println("toNoEqual");
        int numEquationStandard = getNumEquation();
        List<LinearEquation> equationList = new ArrayList<>();
        LinearConstraint linearConstraint = (LinearConstraint) constraint;

        for (int i = 0; i < getNumEquation(); i++) {
            if ((linearConstraint).getSymbol(i) == EquationSymbol.EQUAL) {
                double[] coefficients = new double[getNumVariable()];

                for (int j = 0; j < coefficients.length; j++) {
                    coefficients[j] = linearConstraint.getCoefficient(i, j);
                }

                equationList.add(new LinearEquation(
                        coefficients,
                        EquationSymbol.GREATER_EQUAL,
                        linearConstraint.getConstant(i)
                ));
                equationList.add(new LinearEquation(
                        coefficients,
                        EquationSymbol.LESS_EQUAL,
                        linearConstraint.getConstant(i)
                ));
                numEquationStandard++;
            } else {
                equationList.add(linearConstraint.getEquation(i));
            }
        }

        LinearEquation[] equations = new LinearEquation[numEquationStandard];

        for (int i = 0; i < numEquationStandard; i++) {
            equations[i] = equationList.get(i);
        }

        LinearConstraint constraintStandard = new LinearConstraint(equations);
        LinearProgrammingProblem lp = new LinearProgrammingProblem(orientation, (LinearFunction) objectiveFunction, constraintStandard, (NonnegativeCondition) boundary);
        return lp;
    }

    private LinearProgrammingProblem toAllLess() {
        System.out.println("toAllLess");
        LinearEquation[] equations = new LinearEquation[getNumEquation()];

        for (int i = 0; i < getNumEquation(); i++) {
            double[] coefficients = new double[getNumVariable()];

            for (int j = 0; j < coefficients.length; j++) {
                coefficients[j] = getCoefficient(i, j);
            }

            double constant = getConstant(i);
            EquationSymbol symbol = ((LinearConstraint) constraint).getSymbol(i);

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
                    for (int j = 0; j < getNumVariable(); j++) {
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
        LinearProgrammingProblem lp = new LinearProgrammingProblem(orientation, (LinearFunction) objectiveFunction, constraintStandard, (NonnegativeCondition) boundary);
        return lp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.isMaximize()) {
            sb.append(MAXIMIZE);
        } else {
            sb.append(MINIMIZE);
        }

        sb.append(RETURN);
        sb.append(objectiveFunction.toString());
        sb.append(RETURN);
        sb.append(SUBJECT_TO);
        sb.append(RETURN);

        for (int i = 0; i < getNumEquation(); i++) {
            sb.append(((LinearConstraint) constraint).getEquation(i).toString());
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

            if (this.isMaximize() ^ aLP.isMaximize()) {
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
