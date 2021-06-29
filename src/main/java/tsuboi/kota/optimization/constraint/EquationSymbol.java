package tsuboi.kota.optimization.constraint;

/**
 *
 * @author Kota
 */
public class EquationSymbol {

    public static final EquationSymbol EQUAL;
    public static final EquationSymbol LESS_EQUAL;
    public static final EquationSymbol GREATER_EQUAL;
    public static final EquationSymbol LESS;
    public static final EquationSymbol GREATER;

    static {
        EQUAL = new EquationSymbol(0);
        LESS_EQUAL = new EquationSymbol(-1);
        GREATER_EQUAL = new EquationSymbol(1);
        LESS = new EquationSymbol(-2);
        GREATER = new EquationSymbol(2);
    }

    public static void main(String[] args) {
        if (EQUAL.equals(EQUAL)) {
            System.out.println("= =");
        }
        if (EQUAL.equals(LESS)) {
            System.out.println("= <");
        }
    }

    private final int i;

    private EquationSymbol(int i) {
        this.i = i;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof EquationSymbol) {
            EquationSymbol symbol = (EquationSymbol) obj;
            return this.i == symbol.i;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.i;
        return hash;
    }
}
