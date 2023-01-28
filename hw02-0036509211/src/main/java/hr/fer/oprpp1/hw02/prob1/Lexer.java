package hr.fer.oprpp1.hw02.prob1;

import java.util.Objects;

/**
 * @author Filip Vucic
 * Class which represents a lexer for elements of inputed text.
 */
public class Lexer {

    /**
     * Char array of input data.
     */
    private final char[] data;

    /**
     * Current {@link Token} of the lexer.
     */
    private Token token;

    /**
     * Current index.
     */
    private int currentIndex;

    /**
     * Current {@link LexerState} of the lexer.
     * BASIC by default.
     */
    private LexerState state = LexerState.BASIC;

    /**
     * Creates new {@link Lexer} with given input text.
     *
     * @param text Input text
     */
    public Lexer(String text) {
        Objects.requireNonNull(text, "Text can not be null!");

        data = text.toCharArray();
    }

    /**
     * Returns next {@link Token} of the lexer.
     *
     * @return Next {@link Token} of the lexer
     * @throws LexerException if error
     */
    public Token nextToken() {
        if (data.length < currentIndex) {
            throw new LexerException("Illegal access after EOF!");
        }

        if (data.length == currentIndex) {
            token = new Token(TokenType.EOF, null);
            currentIndex++;
            return token;
        }

        while (data[currentIndex] == '\r' || data[currentIndex] == '\n'
                || data[currentIndex] == '\t' || data[currentIndex] == ' ') {
            currentIndex++;
            if (data.length == currentIndex) {
                token = new Token(TokenType.EOF, null);
                currentIndex++;
                return token;
            }
        }

        if (state.equals(LexerState.EXTENDED)) {
            if (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '\\') {
                StringBuilder word = new StringBuilder();

                while (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '\\') {
                    word.append(data[currentIndex++]);
                    if (data.length == currentIndex) {
                        break;
                    }
                }

                token = new Token(TokenType.WORD, word.toString());
            } else {
                token = new Token(TokenType.SYMBOL, data[currentIndex++]);
            }
        } else {
            if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
                StringBuilder word = new StringBuilder();

                while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
                    if (data[currentIndex] == '\\') {
                        currentIndex++;
                        if (data.length == currentIndex || (!Character.isDigit(data[currentIndex]) && data[currentIndex] != '\\')) {
                            throw new LexerException("Invalid escaping!");
                        }
                    }
                    word.append(data[currentIndex++]);
                    if (data.length == currentIndex) {
                        break;
                    }
                }

                token = new Token(TokenType.WORD, word.toString());
            } else if (Character.isDigit(data[currentIndex])) {
                StringBuilder number = new StringBuilder();

                while (Character.isDigit(data[currentIndex])) {
                    number.append(data[currentIndex++]);
                    if (data.length == currentIndex) {
                        break;
                    }
                }

                try {
                    token = new Token(TokenType.NUMBER, Long.parseLong(number.toString()));
                } catch (NumberFormatException ex) {
                    throw new LexerException("Can't parse the number " + number.toString());
                }
            } else {
                token = new Token(TokenType.SYMBOL, data[currentIndex++]);
            }
        }

        return token;
    }

    /**
     * Returns current {@link Token} of the lexer.
     * Does not generate the new one.
     *
     * @return Current {@link Token} of the lexer.
     */
    public Token getToken() {
        return token;
    }

    /**
     * Set {@link LexerState} of the lexer.
     *
     * @param state {@link LexerState} to be set
     * @throws NullPointerException if state is null
     */
    public void setState(LexerState state) {
        if (state == null) {
            throw new NullPointerException("State can not be null!");
        }
        this.state = state;
    }
}