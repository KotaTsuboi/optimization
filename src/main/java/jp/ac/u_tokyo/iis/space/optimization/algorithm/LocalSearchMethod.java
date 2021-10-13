package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import jp.ac.u_tokyo.iis.space.optimization.problem.OptimizationProblem;
import jp.ac.u_tokyo.iis.space.optimization.solution.DiscretizedSolution;

/**
 *
 * @author Kota
 */
abstract public class LocalSearchMethod extends DiscretizedAlgorithm {

    protected final DiscretizedSolution initialSolution;

    public LocalSearchMethod(OptimizationProblem problem, DiscretizedSolution initialSolution) {
        super(problem);
        this.initialSolution = initialSolution;
    }

}
