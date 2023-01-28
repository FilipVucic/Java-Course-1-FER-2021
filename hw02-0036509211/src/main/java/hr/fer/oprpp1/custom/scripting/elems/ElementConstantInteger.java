package hr.fer.oprpp1.custom.scripting.elems;

/**
 * @author Filip Vucic
 * Class which represents a {@link hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser} element as constant integer.
 */
public class ElementConstantInteger extends Element {

    /**
     * Constant int value.
     */
    private final int value;

    /**
     * Create new {@link ElementConstantInteger} with int value.
     *
     * @param value Int value
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return Integer.toString(value);
    }
}
