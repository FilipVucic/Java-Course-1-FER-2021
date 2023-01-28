package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 * Multiple Document Model interface.
 * @author Filip Vucic
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

    /**
     * Create new document.
     * @return New {@link SingleDocumentModel}
     */
    SingleDocumentModel createNewDocument();

    /**
     * Get current document.
     * @return Current {@link SingleDocumentModel}
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Load document from path.
     * @param path Path
     * @return {@link SingleDocumentModel}.
     * @throws JNotepadPPException if can't load
     */
    SingleDocumentModel loadDocument(Path path) throws JNotepadPPException;

    /**
     * Save document.
     * @param model {@link SingleDocumentModel}
     * @param newPath path
     * @throws JNotepadPPException if can't save
     */
    void saveDocument(SingleDocumentModel model, Path newPath) throws JNotepadPPException;

    /**
     * Close document.
     * @param model {@link SingleDocumentModel}
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Add {@link MultipleDocumentListener}.
     * @param l {@link MultipleDocumentListener}
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Remove {@link MultipleDocumentListener}.
     * @param l {@link MultipleDocumentListener}
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Get number of {@link SingleDocumentModel} documents.
     * @return Number of {@link SingleDocumentModel}  documents
     */
    int getNumberOfDocuments();

    /**
     * Get {@link SingleDocumentModel} document at given index.
     * @param index Index
     * @return {@link SingleDocumentModel} document at given index
     */
    SingleDocumentModel getDocument(int index);

}
