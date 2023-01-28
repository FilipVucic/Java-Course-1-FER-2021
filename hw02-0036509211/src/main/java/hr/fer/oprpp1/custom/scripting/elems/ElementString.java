package hr.fer.oprpp1.custom.scripting.elems;

/**
 * @author Filip Vucic
 * Class which represents a {@link hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser} element as string.
 */
public class ElementString extends Element {

    /**
     * Value of the string.
     */
    private final String value;

    /**
     * Create new {@link ElementString} with given string value.
     *
     * @param value String value
     */
    public ElementString(String value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return "\"" + value + "\"";
    }
}
