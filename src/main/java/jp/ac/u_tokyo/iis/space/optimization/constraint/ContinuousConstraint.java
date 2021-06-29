package jp.ac.u_tokyo.iis.space.optimization.constraint;

import jp.ac.u_tokyo.iis.space.optimization.equation.ContinuousEquation;

/**
 *
 * @author Kota
 */
abstract public class ContinuousConstraint extends Constraint {

    public ContinuousConstraint(ContinuousEquation... equations) {
        super(equations);
    }

}
