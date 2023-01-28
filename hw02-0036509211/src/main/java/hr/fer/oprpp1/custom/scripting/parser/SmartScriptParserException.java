package hr.fer.oprpp1.custom.scripting.parser;

/**
 * @author Filip Vucic
 * Class which represents {@link SmartScriptParser} exception and extends {@link RuntimeException}.
 */
public class SmartScriptParserException extends RuntimeException {

    /**
     * Create new {@link SmartScriptParserException}.
     */
    public SmartScriptParserException() {
    }

    /**
     * Create new {@link SmartScriptParserException} with appropriate message.
     *
     * @param message Appropriate message
     */
    public SmartScriptParserException(String message) {
        super(message);
    }
}
