package jp.ac.u_tokyo.iis.space.optimization.equation;

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
    
    public static EquationSymbol parse(String str) {
        switch (str) {
            case "=":
                return EQUAL;
            case "<=":
                return LESS_EQUAL;
            case ">=":
                return GREATER_EQUAL;
            case "<":
                return LESS;
            case ">":
                return GREATER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
