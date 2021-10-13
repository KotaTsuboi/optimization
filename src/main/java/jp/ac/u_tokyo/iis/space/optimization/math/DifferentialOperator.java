package jp.ac.u_tokyo.iis.space.optimization.math;

import jp.ac.u_tokyo.iis.space.optimization.function.ContinuousFunction;
import jp.ac.u_tokyo.iis.space.optimization.function.DifferentiableFunction;

/**
 *
 * @author Kota
 */
public class DifferentialOperator {

    private DifferentialOperator() {
    }

    public static ContinuousFunction gradient(DifferentiableFunction function, int x) {
        return function.differential(x);
    }

}
