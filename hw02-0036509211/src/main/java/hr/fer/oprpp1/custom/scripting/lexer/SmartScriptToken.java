package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * @author Filip Vucic
 * Class which represents one Token of the {@link SmartScriptLexer}.
 */
public class SmartScriptToken {

    /**
     * Token type.
     */
    private final SmartScriptTokenType type;

    /**
     * Value of the token.
     */
    private final Object value;

    /**
     * Create new {@link SmartScriptToken} with given type and value.
     *
     * @param type  {@link SmartScriptToken} type
     * @param value {@link SmartScriptToken} value
     */
    public SmartScriptToken(SmartScriptTokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Get value of the {@link SmartScriptToken}.
     *
     * @return {@link SmartScriptToken} value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Get type of the {@link SmartScriptToken}.
     *
     * @return {@link SmartScriptToken} type
     */
    public SmartScriptTokenType getType() {
        return type;
    }
}
