package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class which represents query token.
 *
 * @author Filip Vucic
 */
public class QueryToken {

    /**
     * Type of the query token.
     */
    private final QueryTokenType type;

    /**
     * Value of the token.
     */
    private final String value;

    /**
     * Create new {@link QueryToken} with given {@link QueryTokenType} and value.
     *
     * @param type  Token type
     * @param value Token value
     */
    public QueryToken(QueryTokenType type, String value) {
        Objects.requireNonNull(type, "Token type can't be null!");

        this.type = type;
        this.value = value;
    }

    /**
     * Token type getter.
     *
     * @return Token type
     */
    public QueryTokenType getType() {
        return type;
    }

    /**
     * Token value getter.
     *
     * @return Token value
     */
    public String getValue() {
        return value;
    }
}
