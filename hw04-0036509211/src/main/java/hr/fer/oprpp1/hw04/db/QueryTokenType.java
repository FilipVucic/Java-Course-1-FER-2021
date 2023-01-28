package hr.fer.oprpp1.hw04.db;

/**
 * Enum which represents query token type.
 *
 * @author Filip Vucic
 */
public enum QueryTokenType {

    /**
     * Query keyword token type.
     */
    QUERY,

    /**
     * Attribute name token type.
     */
    ATTRIBUTE_NAME,

    /**
     * String literal token type.
     */
    STRING,

    /**
     * And token type.
     */
    AND,

    /**
     * Operator token type.
     */
    OPERATOR,

    /**
     * EOF token type.
     */
    EOF
}
