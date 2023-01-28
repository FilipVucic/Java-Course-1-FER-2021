package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default Single Document Model which represents one tab.
 * @author Filip Vucic
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * Text component.
     */
    private final JTextArea textComponent;

    /**
     * Listeners.
     */
    private final List<SingleDocumentListener> listeners = new ArrayList<>();

    /**
     * File path.
     */
    private Path filePath;

    /**
     * Modified flag.
     */
    private boolean modified = false;

    /**
     * Create new {@link DefaultMultipleDocumentModel} with given file path and text content.
     * @param filePath File path, can be null
     * @param textContent Text content
     */
    public DefaultSingleDocumentModel(Path filePath, String textContent) {
        this.textComponent = new JTextArea(textContent);
        this.filePath = filePath;
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!modified) {
                    setModified(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!modified) {
                    setModified(true);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!modified) {
                    setModified(true);
                }
            }
        });
    }

    /**
     * Notify listeners that the status update happened.
     */
    private void notifyListenersForStatusUpdate() {
        for (SingleDocumentListener l : listeners) {
            l.documentModifyStatusUpdated(this);
        }
    }

    /**
     * Notify listeners that the path update happened.
     */
    private void notifyListenersForPathUpdate() {
        for (SingleDocumentListener l : listeners) {
            l.documentFilePathUpdated(this);
        }
    }

    /**
     * Get text component.
     * @return Text component
     */
    public JTextArea getTextComponent() {
        return textComponent;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(Path path) {
        Objects.requireNonNull(path, "Path must not be null!");
        filePath = path;
        notifyListenersForPathUpdate();
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        notifyListenersForStatusUpdate();
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultSingleDocumentModel that = (DefaultSingleDocumentModel) o;
        return Objects.equals(textComponent, that.textComponent) && Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textComponent, filePath);
    }
}
