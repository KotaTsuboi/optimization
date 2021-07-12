package tsuboi.kota.optimization.constraint;

/**
 *
 * @author Kota
 */
public enum EquationSymbol {
    /**
     * =
     */
    EQUAL,
    /**
     * &lt;=
     */
    LESS_EQUAL,
    /**
     * &gt;=
     */
    GREATER_EQUAL,
    /**
     * &lt;
     */
    LESS,
    /**
     * &gt;
     */
    GREATER;

    @Override
    public String toString() {
        switch (this) {
            case EQUAL:
                return " = ";
            case LESS_EQUAL:
                return " <= ";
            case GREATER_EQUAL:
                return " >= ";
            case LESS:
                return " < ";
            case GREATER:
                return " > ";
            default:
                return super.toString();
        }
    }
}
