package hr.fer.oprpp1.custom.collections;

/**
 * Empty stack exception which is thrown when there are no elements left on the stack.
 *
 * @author Filip Vucic
 */
public class EmptyStackException extends RuntimeException {

    /**
     * Creates new {@link EmptyStackException}.
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Creates new {@link ArrayIndexedCollection} with a message.
     *
     * @param message Message to be passed
     */
    public EmptyStackException(String message) {
        super(message);
    }
}
