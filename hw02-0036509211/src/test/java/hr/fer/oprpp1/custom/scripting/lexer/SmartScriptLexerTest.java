// 2018/2019 opjj test modified, @author Filip Vucic

package hr.fer.oprpp1.custom.scripting.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SmartScriptLexerTest {

    @Test
    void nextToken() {
        String text =  "A tag follows {$= \"Joe \\\\\\\"Long\\\\\\\" Smith\"$}.\n";
        SmartScriptLexer lexer = new SmartScriptLexer(text);

        SmartScriptToken correctData[] = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "A tag follows "),
                new SmartScriptToken(SmartScriptTokenType.TAGSTART, "$"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "="),
                new SmartScriptToken(SmartScriptTokenType.STRING, "Joe \\\"Long\\\" Smith"),
                new SmartScriptToken(SmartScriptTokenType.TAGEND, "$"),
                new SmartScriptToken(SmartScriptTokenType.TEXT, ".\n"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)

        };

        checkTokenStream(lexer, correctData);
    }

    @Test
    void nextToken2() {
        String text = "This is sample text.\n" +
                "{$ FOR i 1 10 1 $}\n" +
                " This is {$= i $}-th time this message is generated.\n" +
                "{$END$}\n" +
                "{$FOR i 0 10 2 $}\n" +
                " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" +
                "{$END$}";
        SmartScriptLexer lexer = new SmartScriptLexer(text);

        SmartScriptToken correctData[] = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "This is sample text.\n"),
                new SmartScriptToken(SmartScriptTokenType.TAGSTART, "$"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "FOR"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "i"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 10),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
                new SmartScriptToken(SmartScriptTokenType.TAGEND, "$"),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "\n This is "),
                new SmartScriptToken(SmartScriptTokenType.TAGSTART, "$"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "="),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "i"),
                new SmartScriptToken(SmartScriptTokenType.TAGEND, "$"),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "-th time this message is generated.\n"),
                new SmartScriptToken(SmartScriptTokenType.TAGSTART, "$"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "END"),
                new SmartScriptToken(SmartScriptTokenType.TAGEND, "$"),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
                new SmartScriptToken(SmartScriptTokenType.TAGSTART, "$"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "FOR"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "i"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 0),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 10),
                new SmartScriptToken(SmartScriptTokenType.INTEGER, 2),
                new SmartScriptToken(SmartScriptTokenType.TAGEND, "$"),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "\n sin("),
                new SmartScriptToken(SmartScriptTokenType.TAGSTART, "$"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "="),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "i"),
                new SmartScriptToken(SmartScriptTokenType.TAGEND, "$"),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "^2) = "),
                new SmartScriptToken(SmartScriptTokenType.TAGSTART, "$"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "="),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "i"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "i"),
                new SmartScriptToken(SmartScriptTokenType.OPERATOR, "*"),
                new SmartScriptToken(SmartScriptTokenType.FUNCTIONNAME, "sin"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "0.000"),
                new SmartScriptToken(SmartScriptTokenType.FUNCTIONNAME, "decfmt"),
                new SmartScriptToken(SmartScriptTokenType.TAGEND, "$"),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
                new SmartScriptToken(SmartScriptTokenType.TAGSTART, "$"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLEORTAGNAME, "END"),
                new SmartScriptToken(SmartScriptTokenType.TAGEND, "$"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
        int counter = 0;
        for(SmartScriptToken expected : correctData) {
            SmartScriptToken actual = lexer.nextToken();

            String msg = "Checking token "+counter + ":";
            if (actual.getType().equals(SmartScriptTokenType.TAGSTART)) {
                lexer.setState(SmartScriptLexerState.TAG);
            }
            if (actual.getType().equals(SmartScriptTokenType.TAGEND)) {
                lexer.setState(SmartScriptLexerState.TEXT);
            }
            assertEquals(expected.getType(), actual.getType(), msg);
            assertEquals(expected.getValue(), actual.getValue(), msg);
            counter++;
        }
    }
}