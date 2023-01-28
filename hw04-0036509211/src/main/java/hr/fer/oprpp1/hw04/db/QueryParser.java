package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which represents parser of queries.
 *
 * @author Filip Vucic
 */
public class QueryParser {

    /**
     * Lexer.
     */
    private final QueryLexer lexer;

    /**
     * Query.
     */
    private final List<ConditionalExpression> queryExpressions;

    /**
     * Create new {@link QueryParser} with given text.
     *
     * @param text Given text
     */
    public QueryParser(String text) {
        lexer = new QueryLexer(text);
        queryExpressions = new ArrayList<>();
        parse();

    }

    /**
     * Check if this query is direct.
     *
     * @return True if direct, false otherwise.
     */
    public boolean isDirectQuery() {
        if (queryExpressions.size() != 1) {
            return false;
        }

        ConditionalExpression firstExpression = queryExpressions.get(0);

        return firstExpression.getComparisonOperator().equals(ComparisonOperators.EQUALS)
                && firstExpression.getFieldGetter().equals(FieldValueGetters.JMBAG);
    }

    /**
     * Get direct queried JMBAG.
     *
     * @return Queried JMBAG
     * @throws IllegalStateException if not direct query.
     */
    public String getQueriedJMBAG() {
        if (!isDirectQuery()) {
            throw new IllegalStateException("Not direct query!");
        }

        return queryExpressions.get(0).getStringLiteral();
    }

    /**
     * Get query.
     *
     * @return Query
     */
    public List<ConditionalExpression> getQuery() {
        return queryExpressions;
    }

    /**
     * Parse the text.
     */
    private void parse() {
        QueryToken token;
        try {
            token = lexer.nextToken();
        } catch (QueryLexerException ex) {
            throw new QueryParserException("Lexer exception: " + ex.getMessage());
        }
        if (!token.getType().equals(QueryTokenType.QUERY)) {
            throw new QueryParserException("query keyword expected!");
        }

        while (true) {
            try {
                token = lexer.nextToken();
            } catch (QueryLexerException ex) {
                throw new QueryParserException("Lexer exception: " + ex.getMessage());
            }
            if (!token.getType().equals(QueryTokenType.ATTRIBUTE_NAME)) {
                throw new QueryParserException("Attribute name expected!");
            }
            String attributeName = token.getValue();
            IFieldValueGetter fieldValueGetter = getFieldValueGetter(attributeName);

            try {
                token = lexer.nextToken();
            } catch (QueryLexerException ex) {
                throw new QueryParserException("Lexer exception: " + ex.getMessage());
            }
            String operator = token.getValue();
            IComparisonOperator comparisonOperator = getOperator(operator);

            try {
                token = lexer.nextToken();
            } catch (QueryLexerException ex) {
                throw new QueryParserException("Lexer exception: " + ex.getMessage());
            }
            String stringLiteral = token.getValue();
            if (!token.getType().equals(QueryTokenType.STRING)) {
                throw new QueryParserException("String literal expected!");
            }

            queryExpressions.add(new ConditionalExpression(fieldValueGetter, stringLiteral, comparisonOperator));

            try {
                token = lexer.nextToken();
            } catch (QueryLexerException ex) {
                throw new QueryParserException("Lexer exception: " + ex.getMessage());
            }
            if (token.getType().equals(QueryTokenType.EOF)) {
                break;
            }
            if (!token.getType().equals(QueryTokenType.AND)) {
                throw new QueryParserException("AND expected!");
            }
        }
    }

    /**
     * Get {@link IComparisonOperator} with given string operator.
     *
     * @param operator String operator
     * @return {@link IComparisonOperator} of the given string operator
     */
    private IComparisonOperator getOperator(String operator) {
        return switch (operator) {
            case "=" -> ComparisonOperators.EQUALS;
            case "!=" -> ComparisonOperators.NOT_EQUALS;
            case ">" -> ComparisonOperators.GREATER;
            case ">=" -> ComparisonOperators.GREATER_OR_EQUALS;
            case "<" -> ComparisonOperators.LESS;
            case "<=" -> ComparisonOperators.LESS_OR_EQUALS;
            case "LIKE" -> ComparisonOperators.LIKE;
            default -> throw new QueryParserException("Invalid operator!");
        };
    }

    /**
     * Get {@link IFieldValueGetter} with given attribute name.
     *
     * @param attributeName Attribute name
     * @return {@link IFieldValueGetter} of given the attribute name
     */
    private IFieldValueGetter getFieldValueGetter(String attributeName) {
        return switch (attributeName) {
            case "firstName" -> FieldValueGetters.FIRST_NAME;
            case "lastName" -> FieldValueGetters.LAST_NAME;
            case "jmbag" -> FieldValueGetters.JMBAG;
            default -> throw new QueryParserException("Invalid attribute name!");
        };
    }
}
