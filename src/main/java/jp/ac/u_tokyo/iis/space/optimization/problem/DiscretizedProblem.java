package jp.ac.u_tokyo.iis.space.optimization.problem;

import jp.ac.u_tokyo.iis.space.optimization.function.ObjectiveFunction;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;

/**
 *
 * @author Kota
 */
abstract public class DiscretizedProblem extends OptimizationProblem {

    public DiscretizedProblem(Orientation orientation, ObjectiveFunction objectiveFunction) {
        super(orientation, objectiveFunction);
    }

}
