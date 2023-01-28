package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents {@link QueryParser} exception. It extends {@link RuntimeException}.
 *
 * @author Filip Vucic
 */
public class QueryParserException extends RuntimeException {

    /**
     * Default constructor.
     */
    public QueryParserException() {
    }

    /**
     * Constructor which accepts message.
     *
     * @param message Message
     */
    public QueryParserException(String message) {
        super(message);
    }
}
