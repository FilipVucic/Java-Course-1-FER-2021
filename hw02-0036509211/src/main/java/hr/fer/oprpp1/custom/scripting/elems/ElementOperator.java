package hr.fer.oprpp1.custom.scripting.elems;

/**
 * @author Filip Vucic
 * Class which represents a {@link hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser} element as operator.
 */
public class ElementOperator extends Element {

    /**
     * Operator symbol.
     */
    private final String symbol;

    /**
     * Create new {@link ElementOperator} with given symbol.
     *
     * @param symbol Symbol
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }
}
