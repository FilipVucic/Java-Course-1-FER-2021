// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryLexerTest {

    @Test
    void lexer() {
        QueryLexer lexer = new QueryLexer("query firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");

        QueryToken correctData[] = {
                new QueryToken(QueryTokenType.QUERY,"query"),
                new QueryToken(QueryTokenType.ATTRIBUTE_NAME,"firstName"),
                new QueryToken(QueryTokenType.OPERATOR,">"),
                new QueryToken(QueryTokenType.STRING, "A"),
                new QueryToken(QueryTokenType.AND, "AND"),
                new QueryToken(QueryTokenType.ATTRIBUTE_NAME,"firstName"),
                new QueryToken(QueryTokenType.OPERATOR, "<"),
                new QueryToken(QueryTokenType.STRING, "C"),
                new QueryToken(QueryTokenType.AND, "AND"),
                new QueryToken(QueryTokenType.ATTRIBUTE_NAME, "lastName"),
                new QueryToken(QueryTokenType.OPERATOR, "LIKE"),
                new QueryToken(QueryTokenType.STRING, "B*ć"),
                new QueryToken(QueryTokenType.AND, "AND"),
                new QueryToken(QueryTokenType.ATTRIBUTE_NAME, "jmbag"),
                new QueryToken(QueryTokenType.OPERATOR, ">"),
                new QueryToken(QueryTokenType.STRING, "0000000002"),


                new QueryToken(QueryTokenType.EOF, null)
        };

        checkQueryTokenStream(lexer, correctData);

    }

    private void checkQueryTokenStream(QueryLexer lexer,
                                       QueryToken[] correctData) {
        int counter = 0;
        for (QueryToken expected : correctData) {
            QueryToken actual = lexer.nextToken();
            String msg = "Checking token " + counter + ":";
            assertEquals(expected.getType(), actual.getType(), msg);
            assertEquals(expected.getValue(), actual.getValue(), msg);
            counter++;
        }
    }
}
