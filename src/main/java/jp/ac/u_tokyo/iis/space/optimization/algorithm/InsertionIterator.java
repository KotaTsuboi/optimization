package jp.ac.u_tokyo.iis.space.optimization.algorithm;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import jp.ac.u_tokyo.iis.space.optimization.solution.PermutationSolution;

/**
 *
 * @author Kota
 */
public class InsertionIterator extends NeighborhoodIterator {

    private final PermutationSolution solution;
    private int i;
    private int j;

    public InsertionIterator(PermutationSolution solution) {
        this.solution = solution;
        i = 0;
        j = 0;
    }

    @Override
    public boolean hasNext() {
        if (j >= solution.size() - 2) {
            return i < solution.size() - 1;
        }
        return i < solution.size();
    }

    @Override
    public PermutationSolution next() {
        if (j >= solution.size()) {
            j = 0;
            i++;
        }

        if (j == i - 1) {
            j += 2;
        }

        if (j == i) {
            j++;
        }

        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        ArrayList<Integer> order = new ArrayList<>();

        for (int k = 0; k < solution.size(); k++) {
            order.add(solution.getOrder(k));
        }

        order.remove(i);
        order.add(j, solution.getOrder(i));

        j++;

        return new PermutationSolution(order.stream().mapToInt(i -> i).toArray());
    }

}
