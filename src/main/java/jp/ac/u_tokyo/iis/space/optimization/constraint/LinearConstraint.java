package jp.ac.u_tokyo.iis.space.optimization.constraint;

import java.util.Arrays;
import jp.ac.u_tokyo.iis.space.optimization.equation.LinearEquation;
import jp.ac.u_tokyo.iis.space.optimization.symbol.EquationSymbol;

/**
 *
 * @author Kota
 */
public class LinearConstraint extends ContinuousConstraint {

    private static final String RETURN;

    static {
        RETURN = "\n";
    }

    public LinearConstraint() {
        equations = new LinearEquation[]{};
        numEquation = 0;
    }

    public LinearConstraint(LinearEquation... equations) {
        super(equations);
    }

    public double getCoefficient(int i, int j) {
        return ((LinearEquation) equations[i]).getCoefficient(j);
    }

    public EquationSymbol getSymbol(int j) {
        return ((LinearEquation) equations[j]).getSymbol();
    }

    public double getConstant(int j) {
        return ((LinearEquation) equations[j]).getConstant();
    }

    public LinearEquation getEquation(int i) {
        return (LinearEquation) equations[i];
    }

    @Override
    public int getNumEquation() {
        return numEquation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getNumEquation(); i++) {
            sb.append(getEquation(i).toString());
            sb.append(RETURN);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (anObject instanceof LinearConstraint) {
            LinearConstraint aConstraint = (LinearConstraint) anObject;

            if (this.getNumEquation() == aConstraint.getNumEquation()) {
                this.sort();
                aConstraint.sort();

                for (int i = 0; i < getNumEquation(); i++) {
                    if (!this.getEquation(i).equals(aConstraint.getEquation(i))) {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }

    private void sort() {
        Arrays.sort(equations);
    }

}
