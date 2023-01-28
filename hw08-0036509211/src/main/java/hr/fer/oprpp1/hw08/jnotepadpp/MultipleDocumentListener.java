package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Multiple Document Listener interface.
 * @author Filip Vucic
 */
public interface MultipleDocumentListener {

    /**
     * Current document changed.
     * @param previousModel Previous document
     * @param currentModel Current document
     */
    void currentDocumentChanged(SingleDocumentModel previousModel,
                                SingleDocumentModel currentModel);

    /**
     * Document added.
     * @param model Document
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Document removed.
     * @param model Document
     */
    void documentRemoved(SingleDocumentModel model);
}
