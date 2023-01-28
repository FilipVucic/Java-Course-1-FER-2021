package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents a lexer for {@link QueryParser}.
 *
 * @author Filip Vucic
 */
public class QueryLexer {

    /**
     * Data to be parsed.
     */
    private final char[] data;
    /**
     * Current token.
     */
    private QueryToken token;
    /**
     * Current index.
     */
    private int currentIndex;

    /**
     * Create new {@link QueryLexer} with given text.
     *
     * @param text Given text
     */
    public QueryLexer(String text) {
        data = text.toCharArray();
    }

    /**
     * Get next {@link QueryToken}.
     *
     * @return Next {@link QueryToken}
     */
    public QueryToken nextToken() {
        if (data.length < currentIndex) {
            throw new QueryLexerException("Illegal access after EOF!");
        }

        if (data.length == currentIndex) {
            token = new QueryToken(QueryTokenType.EOF, null);
            currentIndex++;
            return token;
        }

        while (data[currentIndex] == '\r' || data[currentIndex] == '\n'
                || data[currentIndex] == '\t' || data[currentIndex] == ' ') {
            currentIndex++;
            if (data.length == currentIndex) {
                token = new QueryToken(QueryTokenType.EOF, null);
                currentIndex++;
                return token;
            }
        }

        StringBuilder tokenValue = new StringBuilder();

        if (data[currentIndex] == '\"') {
            currentIndex++;
            while (data[currentIndex] != '\"') {
                tokenValue.append(data[currentIndex++]);
            }
            currentIndex++;
            token = new QueryToken(QueryTokenType.STRING, tokenValue.toString());
        } else if (Character.isLetter(data[currentIndex])) {
            while (Character.isLetter(data[currentIndex])) {
                tokenValue.append(data[currentIndex++]);
            }
            if (tokenValue.toString().equalsIgnoreCase("AND")) {
                token = new QueryToken(QueryTokenType.AND, "AND");
            } else if (tokenValue.toString().equalsIgnoreCase("LIKE")) {
                token = new QueryToken(QueryTokenType.OPERATOR, "LIKE");
            } else if (tokenValue.toString().equals("query")) {
                token = new QueryToken(QueryTokenType.QUERY, "query");
            } else {
                token = new QueryToken(QueryTokenType.ATTRIBUTE_NAME, tokenValue.toString());
            }
        } else {
            tokenValue.append(data[currentIndex++]);
            if (data[currentIndex] == '=') {
                tokenValue.append(data[currentIndex++]);
            }
            token = new QueryToken(QueryTokenType.OPERATOR, tokenValue.toString());
        }

        return token;
    }
}
