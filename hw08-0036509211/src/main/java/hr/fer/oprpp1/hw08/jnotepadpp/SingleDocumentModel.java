package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Single Document Model interface.
 * @author Filip Vucic
 */
public interface SingleDocumentModel {

    /**
     * Get text component of the document.
     * @return Text component of the document.
     */
    JTextArea getTextComponent();

    /**
     * Get file path of the document.
     * @return File path of the document.
     */
    Path getFilePath();

    /**
     * Set file path of the document.
     * @param path File path of the document
     */
    void setFilePath(Path path);

    /**
     * Check if the document is modified.
     * @return True if modified, false otherwise
     */
    boolean isModified();

    /**
     * Set modified flag of the document.
     * @param modified Modified flag
     */
    void setModified(boolean modified);

    /**
     * Add {@link SingleDocumentListener} to the document.
     * @param l {@link SingleDocumentListener} to be added
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Remove {@link SingleDocumentListener} from the document.
     * @param l {@link SingleDocumentListener} to be removed
     */
    void removeSingleDocumentListener(SingleDocumentListener l);

}
