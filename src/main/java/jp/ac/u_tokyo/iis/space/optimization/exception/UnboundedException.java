package jp.ac.u_tokyo.iis.space.optimization.exception;

/**
 * Exception for optimization problems which are unbounded.
 *
 * @author Kota
 */
public class UnboundedException extends Exception {

    public UnboundedException(String msg) {
        super(msg);
    }

}
