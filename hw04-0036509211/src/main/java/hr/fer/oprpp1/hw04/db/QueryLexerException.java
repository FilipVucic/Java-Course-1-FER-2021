package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents {@link QueryLexer} exception. It extends {@link RuntimeException}.
 *
 * @author Filip Vucic
 */
public class QueryLexerException extends RuntimeException {

    /**
     * Default constructor.
     */
    public QueryLexerException() {
    }

    /**
     * Constructor which accepts message.
     *
     * @param message Message
     */
    public QueryLexerException(String message) {
        super(message);
    }
}