package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;

/**
 * Default Multiple Document Model which represents a {@link JTabbedPane} of {@link JNotepadPP}.
 * @author Filip Vucic
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    /**
     * Documents.
     */
    private final List<SingleDocumentModel> documents;

    /**
     * Listeners.
     */
    private final List<MultipleDocumentListener> listeners;

    /**
     * Document listeners.
     */
    private final Map<SingleDocumentModel, SingleDocumentListener> documentListeners;

    /**
     * Current document.
     */
    private SingleDocumentModel currentDocument;

    /**
     * Create new {@link DefaultMultipleDocumentModel}.
     */
    public DefaultMultipleDocumentModel() {
        this.documents = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.documentListeners = new HashMap<>();
        addChangeListener(e -> {
            SingleDocumentModel oldDocument = currentDocument;
            currentDocument = getSelectedIndex() == -1 ? null : getDocument(getSelectedIndex());
            notifyListenersForDocumentChange(oldDocument, currentDocument);
        });
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
        addDocument(newDocument);
        return currentDocument;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) throws JNotepadPPException {
        Objects.requireNonNull(path, "Path must not be null!");
        for (SingleDocumentModel document : documents) {
            if (document.getFilePath() != null) {
                if (document.getFilePath().equals(path)) {
                    setSelectedIndex(documents.indexOf(document));
                    return currentDocument;
                }
            }
        }

        byte[] okteti;
        try {
            okteti = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new JNotepadPPException("Error while reading the document.");
        }
        SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, new String(okteti, StandardCharsets.UTF_8));
        addDocument(newDocument);

        return currentDocument;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) throws JNotepadPPException {
        if (newPath == null) {
            try {
                Files.writeString(model.getFilePath(), model.getTextComponent().getText());
            } catch (IOException e) {
                throw new JNotepadPPException("Error while writing the file.");
            }
        } else {
            for (SingleDocumentModel document : documents) {
                if (document.getFilePath() != null) {
                    if (document.getFilePath().equals(newPath)) {
                        throw new JNotepadPPException("The file is already opened in JNotepad++.");
                    }
                }
            }
            try {
                Files.writeString(newPath, model.getTextComponent().getText());
            } catch (IOException e) {
                throw new JNotepadPPException("Error while writing the file.");
            }
            currentDocument.setFilePath(newPath);
        }

        if (currentDocument.isModified()) {
            currentDocument.setModified(false);
        }
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (getNumberOfDocuments() == 0) {
            return;
        }
        int index = documents.indexOf(model);
        documents.remove(model);
        removeTabAt(index);

        removeOldSingleDocumentListener(model);
        notifyListenersForDocumentRemoval(model);
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    /**
     * Notify listeners that the document change happened.
     * @param oldDocument Old {@link SingleDocumentModel}
     * @param newDocument New {@link SingleDocumentModel}
     */
    private void notifyListenersForDocumentChange(SingleDocumentModel oldDocument, SingleDocumentModel newDocument) {
        for (MultipleDocumentListener l : listeners) {
            l.currentDocumentChanged(oldDocument, newDocument);
        }
    }

    /**
     * Notify listeners that the document addition happened.
     * @param newDocument New {@link SingleDocumentModel}
     */
    private void notifyListenersForDocumentAddition(SingleDocumentModel newDocument) {
        for (MultipleDocumentListener l : listeners) {
            l.documentAdded(newDocument);
        }
    }

    /**
     * Notify listeners that the document removal happened.
     * @param document Removed {@link SingleDocumentModel}
     */
    private void notifyListenersForDocumentRemoval(SingleDocumentModel document) {
        for (MultipleDocumentListener l : listeners) {
            l.documentRemoved(document);
        }
    }

    /**
     * Add document to documents.
     * @param newDocument New {@link SingleDocumentModel}.
     */
    private void addDocument(SingleDocumentModel newDocument) {
        documents.add(newDocument);
        addNewSingleDocumentListener(newDocument);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(newDocument.getTextComponent()));
        String title = "(unnamed)";
        String toolTipText = "(unnamed)";
        if (newDocument.getFilePath() != null) {
            title = newDocument.getFilePath().getFileName().toString();
            toolTipText = newDocument.getFilePath().toAbsolutePath().toString();
        }
        addTab(title, getFloppy("green"), panel, toolTipText);

        setSelectedComponent(panel);
        currentDocument = newDocument;
        notifyListenersForDocumentAddition(newDocument);
    }

    /**
     * Add new {@link SingleDocumentListener}.
     * @param newDocument New {@link SingleDocumentModel}
     */
    private void addNewSingleDocumentListener(SingleDocumentModel newDocument) {
        SingleDocumentListener documentListener = new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                if (model.isModified()) {
                    setIconAt(documents.indexOf(model), getFloppy("red"));
                } else {
                    setIconAt(documents.indexOf(model), getFloppy("green"));
                }
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
                setToolTipTextAt(documents.indexOf(model), model.getFilePath().toAbsolutePath().toString());
            }
        };

        documentListeners.put(newDocument, documentListener);
        newDocument.addSingleDocumentListener(documentListener);
    }

    /**
     * Remove old {@link SingleDocumentListener}.
     * @param oldDocument Old {@link SingleDocumentModel}
     */
    private void removeOldSingleDocumentListener(SingleDocumentModel oldDocument) {
        SingleDocumentListener listener = documentListeners.get(oldDocument);
        oldDocument.removeSingleDocumentListener(listener);
        documentListeners.remove(oldDocument);
    }

    /**
     * Get red/green floppy from disk.
     * @param color Red/green
     * @return Floppy icon
     */
    private ImageIcon getFloppy(String color) {
        String resource;
        switch (color) {
            case "red" -> resource = "icons/floppy_disk_red.png";
            case "green" -> resource = "icons/floppy_disk_green.png";
            default -> throw new IllegalArgumentException("Unsupported color!");
        }

        InputStream is = this.getClass().getResourceAsStream(resource);
        if (is == null) {
            throw new JNotepadPPException("Icon does not exist!");
        }

        byte[] bytes;
        try {
            bytes = is.readAllBytes();
            is.close();
        } catch (IOException e) {
            throw new JNotepadPPException("Error while reading the icon.");
        }

        return new ImageIcon(new ImageIcon(bytes).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return this.documents.iterator();
    }
}
