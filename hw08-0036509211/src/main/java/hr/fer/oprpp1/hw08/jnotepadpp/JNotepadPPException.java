package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * JNotepadPP Exception which extends {@link RuntimeException}.
 * @author Filip Vucic
 */
public class JNotepadPPException extends RuntimeException {

    /**
     * Create new {@link JNotepadPPException}.
     */
    public JNotepadPPException() {
        super();
    }

    /**
     * Create new {@link JNotepadPPException} with given message.
     * @param message Message
     */
    public JNotepadPPException(String message) {
        super(message);
    }
}
