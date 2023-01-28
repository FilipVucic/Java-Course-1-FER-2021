package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * @author Filip Vucic
 * Class which represents {@link SmartScriptLexer} exception and extends {@link RuntimeException}.
 */
public class SmartScriptLexerException extends RuntimeException {

    /**
     * Create new {@link SmartScriptLexerException}.
     */
    public SmartScriptLexerException() {
        super();
    }

    /**
     * Create new {@link SmartScriptLexerException} with appropriate message.
     *
     * @param message Appropriate message
     */
    public SmartScriptLexerException(String message) {
        super(message);
    }
}
