package jp.ac.u_tokyo.iis.space.optimization.function;

/**
 *
 * @author Kota
 */
abstract public class DifferentiableFunction extends ContinuousFunction {

    abstract public ContinuousFunction differential(int x);

}
