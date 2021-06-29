package jp.ac.u_tokyo.iis.space.optimization.orientation;

/**
 *
 * @author Kota
 */
public enum Orientation {
    MAXIMIZE,
    MINIMIZE;

    public boolean isMaximize() {
        return this == MAXIMIZE;
    }
}
