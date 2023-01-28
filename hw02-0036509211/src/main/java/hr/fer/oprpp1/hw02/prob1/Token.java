package hr.fer.oprpp1.hw02.prob1;

/**
 * @author Filip Vucic
 * Class which represents one Token of the {@link Lexer}.
 */
public class Token {

    /**
     * Token type.
     */
    private final TokenType type;

    /**
     * Value of the token.
     */
    private final Object value;

    /**
     * Create new {@link Token} with given type and value.
     *
     * @param type  {@link Token} type
     * @param value {@link Token} value
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Get value of the {@link Token}.
     *
     * @return {@link Token} value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Get type of the {@link Token}.
     *
     * @return {@link Token} type
     */
    public TokenType getType() {
        return type;
    }
}