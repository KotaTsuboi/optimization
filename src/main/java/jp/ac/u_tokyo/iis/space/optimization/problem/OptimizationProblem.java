package jp.ac.u_tokyo.iis.space.optimization.problem;

import java.util.Objects;
import jp.ac.u_tokyo.iis.space.optimization.function.ObjectiveFunction;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;

/**
 *
 * @author Kota
 */
abstract public class OptimizationProblem {

    protected final Orientation orientation;
    protected final ObjectiveFunction objectiveFunction;

    public OptimizationProblem(Orientation orientation, ObjectiveFunction objectiveFunction) {
        Objects.requireNonNull(objectiveFunction);
        this.orientation = orientation;
        this.objectiveFunction = objectiveFunction;
    }

    public ObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
    }

    public boolean isMaximize() {
        return orientation.isMaximize();
    }

    @Override
    abstract public String toString();
}
