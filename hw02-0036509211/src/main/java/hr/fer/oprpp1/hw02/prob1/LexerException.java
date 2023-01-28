package hr.fer.oprpp1.hw02.prob1;

/**
 * @author Filip Vucic
 * Class which represents {@link Lexer} exception and extends {@link RuntimeException}.
 */
public class LexerException extends RuntimeException {

    /**
     * Create new {@link LexerException}.
     */
    public LexerException() {
        super();
    }

    /**
     * Create new {@link LexerException} with appropriate message.
     *
     * @param message Appropriate message
     */
    public LexerException(String message) {
        super(message);
    }
}
