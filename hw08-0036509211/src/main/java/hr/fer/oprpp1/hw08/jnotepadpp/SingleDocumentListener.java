package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Single Document Listener interface.
 * @author Filip Vucic
 */
public interface SingleDocumentListener {

    /**
     * Document modify status updated.
     * @param model {@link SingleDocumentModel}
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Document file path updated.
     * @param model {@link SingleDocumentModel}
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
