package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Shell environment.
 *
 * @author Filip Vucic
 */
public interface Environment {

    /**
     * Read line from the console.
     *
     * @return Read line
     * @throws ShellIOException If the line can't be read
     */
    String readLine() throws ShellIOException;

    /**
     * Write text on the console.
     *
     * @param text Text to be written
     * @throws ShellIOException If the line can't be written
     */
    void write(String text) throws ShellIOException;

    /**
     * Write text on the console, then jump to the new line.
     *
     * @param text Text to be written
     * @throws ShellIOException If the line can't be written
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Get all commands from the environment.
     *
     * @return Sorted map of commands
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Get multiline symbol of the environment.
     *
     * @return Multiline symbol
     */
    Character getMultilineSymbol();

    /**
     * Set multiline symbol of the environment.
     *
     * @param symbol Multiline symbol
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Get prompt symbol of the environment.
     *
     * @return Prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Set prompt symbol of the environment.
     *
     * @param symbol Prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * Get morelines symbol of the environment.
     *
     * @return Morelines symbol
     */
    Character getMorelinesSymbol();

    /**
     * Set morelines symbol of the environment.
     *
     * @param symbol Morelines symbol
     */
    void setMorelinesSymbol(Character symbol);
}
