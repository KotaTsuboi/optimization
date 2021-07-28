package jp.ac.u_tokyo.iis.space.optimization.solution;

/**
 *
 * @author Kota
 */
abstract public class AbstractContinuousSolution {

    abstract public double getValue(int i);

    abstract public boolean isNull();

    abstract public int size();
}
