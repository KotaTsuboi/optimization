package jp.ac.u_tokyo.iis.space.optimization.boundary;

import jp.ac.u_tokyo.iis.space.optimization.symbol.EquationSymbol;

/**
 *
 * @author Kota
 */
abstract public class ContinuousBoundaryCondition extends BoundaryCondition {

    protected final int[] variableIndexes;
    protected final EquationSymbol[] symbols;
    protected final double[] constants;
    protected final int numBoundary;

    public ContinuousBoundaryCondition(int[] variableIndexes, EquationSymbol[] symbols, double[] constants) {
        this.variableIndexes = variableIndexes;
        this.symbols = symbols;
        this.constants = constants;
        this.numBoundary = variableIndexes.length;

        for (int j = 0; j < numBoundary; j++) {
            if (symbols[j] == null) {
                symbols[j] = EquationSymbol.GREATER_EQUAL;
            }
        }
    }

    public int[] getVariableIndexes() {
        return variableIndexes;
    }

    public EquationSymbol[] getSymbols() {
        return symbols;
    }

    public double[] getConstants() {
        return constants;
    }

    @Override
    public int getNumBoundary() {
        return numBoundary;
    }

}
