package hr.fer.oprpp1.custom.scripting.elems;

/**
 * @author Filip Vucic
 * Class which represents a {@link hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser} element as variable.
 */
public class ElementVariable extends Element {

    /**
     * Variable name.
     */
    private final String name;

    /**
     * Create new {@link ElementVariable} with given variable name.
     *
     * @param name Variable name
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    @Override
    public String asText() {
        return name;
    }
}
