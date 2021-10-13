package jp.ac.u_tokyo.iis.space.optimization.solution;

import jp.ac.u_tokyo.iis.space.optimization.algorithm.InsertionIterator;

/**
 *
 * @author Kota
 */
public class PermutationSolution extends DiscretizedSolution {

    private final int[] order;

    public PermutationSolution(int[] order) {
        this.order = order;
    }

    public InsertionIterator iterator() {
        return new InsertionIterator(this);
    }

    public int size() {
        return order.length;
    }

    public int getOrder(int i) {
        return order[i];
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (anObject instanceof PermutationSolution) {
            PermutationSolution aSolution = (PermutationSolution) anObject;

            if (this.size() != aSolution.size()) {
                return false;
            }

            for (int i = 0; i < this.size(); i++) {
                if (this.getOrder(i) != aSolution.getOrder(i)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

}
