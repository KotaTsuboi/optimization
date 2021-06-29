package jp.ac.u_tokyo.iis.space.optimization.constraint;

import java.util.Objects;
import jp.ac.u_tokyo.iis.space.optimization.equation.Equation;

/**
 *
 * @author Kota
 */
abstract public class Constraint {

    Equation[] equations;
    int numEquation;

    public Constraint(Equation... equations) {
        Objects.requireNonNull(equations);
        this.equations = equations;
        numEquation = equations.length;

        int numVariable = -1;

        for (Equation eq : equations) {
            if (numVariable == -1) {
                numVariable = eq.getNumVariable();
            }

            if (eq.getNumVariable() != numVariable) {
                throw new IllegalArgumentException("変数の数が異なります。");
            }
        }
    }

    abstract public int getNumEquation();

    public int getNumVariable() {
        return equations[0].getNumVariable();
    }

}
