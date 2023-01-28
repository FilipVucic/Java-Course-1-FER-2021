package hr.fer.oprpp1.hw05.shell;

/**
 * Exception thrown when reading from or writing to the console fails.
 *
 * @author Filip Vucic
 */
public class ShellIOException extends RuntimeException {

    /**
     * Create new {@link ShellIOException}.
     */
    public ShellIOException() {
        super();
    }

    /**
     * Create new {@link ShellIOException} with exception message.
     *
     * @param message Exception message
     */
    public ShellIOException(String message) {
        super(message);
    }
}
