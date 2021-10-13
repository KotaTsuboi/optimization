package jp.ac.u_tokyo.iis.space.optimization.problem;

import jp.ac.u_tokyo.iis.space.optimization.function.DistanceCalculator;
import jp.ac.u_tokyo.iis.space.optimization.orientation.Orientation;
import jp.ac.u_tokyo.iis.space.optimization.solution.PermutationSolution;

/**
 *
 * @author Kota
 */
public class TravelingSalesmanProblem extends DiscretizedProblem {

    public TravelingSalesmanProblem(DistanceCalculator function) {
        super(Orientation.MINIMIZE, function);
    }

    public double distance(PermutationSolution solution) {
        return ((DistanceCalculator) objectiveFunction).calculate(solution);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
