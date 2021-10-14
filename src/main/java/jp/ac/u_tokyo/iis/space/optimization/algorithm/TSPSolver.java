package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import jp.ac.u_tokyo.iis.space.optimization.exception.UnboundedException;
import jp.ac.u_tokyo.iis.space.optimization.exception.UnfeasibleException;
import jp.ac.u_tokyo.iis.space.optimization.problem.TravelingSalesmanProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.PermutationSolution;

/**
 *
 * @author Kota
 */
public class TSPSolver extends LocalSearchMethod {

    public TSPSolver(TravelingSalesmanProblem problem, PermutationSolution initialSolution) {
        super(problem, initialSolution);
    }

    @Override
    public PermutationSolution solve() throws UnboundedException, UnfeasibleException {
        TravelingSalesmanProblem tsp = (TravelingSalesmanProblem) problem;
        PermutationSolution optimalSolution = (PermutationSolution) initialSolution;
        double optimalDistance = tsp.distance(optimalSolution);

        InsertionIterator iterator = optimalSolution.iterator();

        while (true) {
            PermutationSolution previousSolution = optimalSolution;

            while (iterator.hasNext()) {
                PermutationSolution solution = iterator.next();
                double distance = tsp.distance(solution);

                if (distance < optimalDistance) {
                    optimalDistance = distance;
                    optimalSolution = solution;
                }
            }

            if (optimalSolution.equals(previousSolution)) {
                break;
            }
        }

        return optimalSolution;
    }

}
