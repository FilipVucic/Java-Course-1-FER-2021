package hr.fer.oprpp1.custom.scripting.elems;

/**
 * @author Filip Vucic
 * Class which represents a {@link hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser} element as function.
 */
public class ElementFunction extends Element {

    /**
     * Name of the function.
     */
    private final String name;

    /**
     * Create new {@link ElementFunction} with function name.
     *
     * @param name Function name
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    @Override
    public String asText() {
        return "@" + name;
    }
}
