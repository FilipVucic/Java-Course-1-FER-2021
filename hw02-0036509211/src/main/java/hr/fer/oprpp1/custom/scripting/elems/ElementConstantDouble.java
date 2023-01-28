package hr.fer.oprpp1.custom.scripting.elems;

/**
 * @author Filip Vucic
 * Class which represents a {@link hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser} element as constant double.
 */
public class ElementConstantDouble extends Element {

    /**
     * Constant double value.
     */
    private final double value;

    /**
     * Create new {@link ElementConstantDouble} with double value.
     *
     * @param value Double value
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return Double.toString(value);
    }
}
