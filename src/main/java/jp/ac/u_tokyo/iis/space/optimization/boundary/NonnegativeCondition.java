package jp.ac.u_tokyo.iis.space.optimization.boundary;

import jp.ac.u_tokyo.iis.space.optimization.equation.EquationSymbol;

/**
 *
 * @author Kota
 */
public class NonnegativeCondition extends ContinuousBoundaryCondition {

    public NonnegativeCondition(int[] variableIndexes) {
        super(variableIndexes, new EquationSymbol[variableIndexes.length], new double[variableIndexes.length]);
    }

    public boolean isNonnegative(int i) {
        for (int index : variableIndexes) {
            if (index == i) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        boolean nonnegativeExists = false;

        for (int j : variableIndexes) {
            if (isFirst) {
                nonnegativeExists = true;
                isFirst = false;
            } else {
                sb.append(", ");
            }
            sb.append("X");
            sb.append(j + 1);
        }

        if (nonnegativeExists) {
            sb.append(">= 0");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (anObject instanceof NonnegativeCondition) {
            NonnegativeCondition aCondition = (NonnegativeCondition) anObject;

            if (toString().equals(aCondition.toString())) {
                return true;
            }
        }

        return false;
    }
        
}
