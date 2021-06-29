package jp.ac.u_tokyo.iis.space.optimization.function;

/**
 *
 * @author Kota
 */
abstract public class ContinuousFunction extends ObjectiveFunction {

    abstract public double getValue(double[] variable);

    abstract public double[] getGradient(double[] variable);

}
