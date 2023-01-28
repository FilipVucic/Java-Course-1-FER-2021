package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

/**
 * JNotepad++ implementation.
 * @author Filip Vucic
 */
public class JNotepadPP extends JFrame {

    /**
     * Title of the program.
     */
    private final static String TITLE = "JNotepad++";

    /**
     * {@link FormLocalizationProvider}.
     */
    private final FormLocalizationProvider lp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

    /**
     * Change case menu.
     */
    private JMenu changeCaseMenu;

    /**
     * Sort menu.
     */
    private JMenu sortMenu;

    /**
     * Status bar.
     */
    private StatusBar myStatusBar;

    /**
     * {@link DefaultMultipleDocumentModel}.
     */
    private DefaultMultipleDocumentModel model;

    /**
     * Copy action.
     */
    private final Action copyAction = new LocalizableAction("copy", lp) {
        final Action action = new DefaultEditorKit.CopyAction();

        @Override
        public void actionPerformed(ActionEvent e) {
            action.actionPerformed(e);
        }
    };

    /**
     * Paste action.
     */
    private final Action pasteAction = new LocalizableAction("paste", lp) {
        final Action action = new DefaultEditorKit.PasteAction();

        @Override
        public void actionPerformed(ActionEvent e) {
            action.actionPerformed(e);
        }
    };

    /**
     * Cut action.
     */
    private final Action cutAction = new LocalizableAction("cut", lp) {
        final Action action = new DefaultEditorKit.CutAction();

        @Override
        public void actionPerformed(ActionEvent e) {
            action.actionPerformed(e);
        }
    };

    /**
     * Set language to english action.
     */
    private final Action setLanguageToEn = getLanguageAction("en");

    /**
     * Set language to german action.
     */
    private final Action setLanguageToDe = getLanguageAction("de");

    /**
     * Set language to croatian action.
     */
    private final Action setLanguageToHr = getLanguageAction("hr");

    /**
     * New blank document action.
     */
    private final Action newBlankDocumentAction = new LocalizableAction("new", lp) {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            model.createNewDocument();
        }
    };

    /**
     * Open existing document action.
     */
    private final Action openExistingDocumentAction = new LocalizableAction("open", lp) {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open");
            if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File fileName = fc.getSelectedFile();
            Path filePath = fileName.toPath();
            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Datoteka " + fileName.getAbsolutePath() + " ne postoji!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                model.loadDocument(filePath);
            } catch (JNotepadPPException ex) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        ex.getMessage(),
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    /**
     * Save as document action.
     */
    private final Action saveAsDocumentAction = new LocalizableAction("saveas", lp) {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.getCurrentDocument() == null) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Nema otvorenog dokumenta!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save As");
            if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Ništa nije snimljeno.",
                        "Upozorenje",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Path path = jfc.getSelectedFile().toPath();

            if (Files.exists(path)) {
                int n = JOptionPane.showConfirmDialog(
                        JNotepadPP.this,
                        path.getFileName().toString() + " already exists.\nDo you want to replace it?",
                        "Confirm Save As",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.NO_OPTION || n == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }

            try {
                model.saveDocument(model.getCurrentDocument(), path);
            } catch (JNotepadPPException ex) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        ex.getMessage(),
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(
                    JNotepadPP.this,
                    "Datoteka je snimljena.",
                    "Informacija",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    };

    /**
     * Save document action.
     */
    private final Action saveDocumentAction = new LocalizableAction("save", lp) {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.getCurrentDocument() == null) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Nema otvorenog dokumenta!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (model.getCurrentDocument().getFilePath() == null) {
                saveAsDocumentAction.actionPerformed(e);
                return;
            }

            try {
                model.saveDocument(model.getCurrentDocument(), null);
            } catch (JNotepadPPException ex) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        ex.getMessage(),
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(
                    JNotepadPP.this,
                    "Datoteka je snimljena.",
                    "Informacija",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    };

    /**
     * Close tab action.
     */
    private final Action closeTabAction = new LocalizableAction("closetab", lp) {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.getCurrentDocument() == null) {
                return;
            }
            if (model.getCurrentDocument().isModified()) {
                String filePath = model.getCurrentDocument().getFilePath() == null ? "(unnamed)" : model.getCurrentDocument().getFilePath().getFileName().toString();
                int n = JOptionPane.showConfirmDialog(JNotepadPP.this,
                        "Save file " + filePath + "?", "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    saveDocumentAction.actionPerformed(e);
                    return;
                } else if (n == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }

            model.closeDocument(model.getCurrentDocument());
        }
    };

    /**
     * Statistical info action.
     */
    private final Action statisticalInfoAction = new LocalizableAction("stats", lp) {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.getCurrentDocument() == null) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Nema otvorenog dokumenta!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String text = model.getCurrentDocument().getTextComponent().getText();
            int numberOfCharacters = text.length();
            int nonBlankCharacters = text.replaceAll("\\s+", "").length();
            long numberOfLines = text.chars().filter(ch -> ch == '\n').count() + 1;
            String message = "Your document has " + numberOfCharacters + " characters, " + nonBlankCharacters +
                    " non-blank characters and " + numberOfLines + " lines.";

            JOptionPane.showMessageDialog(
                    JNotepadPP.this,
                    message,
                    "Statistical Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    };

    /**
     * Exit action.
     */
    private final Action exitAction = new LocalizableAction("exit", lp) {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            for (SingleDocumentModel document : model) {
                if (document.isModified()) {
                    String filePath = document.getFilePath() == null ? "(unnamed)" : document.getFilePath().getFileName().toString();
                    int n = JOptionPane.showConfirmDialog(JNotepadPP.this,
                            "Save file " + filePath + "?", "Save",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        saveAsDocumentAction.actionPerformed(e);
                        return;
                    } else if (n == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
            }

            dispose();
        }
    };

    /**
     * To upper case action.
     */
    private final Action toUpperCaseAction = getToolsAction(String::toUpperCase, "upper");

    /**
     * To lower case action.
     */
    private final Action toLowerCaseAction = getToolsAction(String::toLowerCase, "lower");

    /**
     * Invert case action.
     */
    private final Action invertCaseAction = getToolsAction(this::changeCase, "invert");

    /**
     * Sort ascending action.
     */
    private final Action sortAscendingAction = getToolsAction(text -> this.sort(text, true), "asc");

    /**
     * Sort descending action.
     */
    private final Action sortDescendingAction = getToolsAction(text -> this.sort(text, false), "desc");

    /**
     * Unique lines action.
     */
    private final Action uniqueAction = getToolsAction(this::removeDuplicates, "uniq");

    /**
     * Initialize {@link JNotepadPP}.
     */
    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });
        setLocation(0, 0);
        setSize(600, 600);

        initGUI();
    }

    /**
     * Main program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }

    /**
     * Get language action.
     * @param language Language
     * @return Language {@link LocalizableAction}
     */
    private Action getLanguageAction(String language) {
        return new LocalizableAction(language, lp) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage(language);
            }
        };
    }

    /**
     * Get tools action.
     * @param modifier {@link Modifier}
     * @param key Key
     * @return Tools {@link LocalizableAction}
     */
    private Action getToolsAction(Modifier<String> modifier, String key) {
        return new LocalizableAction(key, lp) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                Document doc = model.getCurrentDocument().getTextComponent().getDocument();
                JTextArea textComponent = model.getCurrentDocument().getTextComponent();
                int len = Math.abs(textComponent.getCaret().getDot() - textComponent.getCaret().getMark());
                int offset;
                if (len != 0) {
                    offset = Math.min(textComponent.getCaret().getDot(), textComponent.getCaret().getMark());
                } else {
                    throw new JNotepadPPException("Button should be disabled!");
                }
                try {
                    String text = doc.getText(offset, len);
                    text = modifier.modify(text);
                    doc.remove(offset, len);
                    doc.insertString(offset, text, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Change case method.
     * @param text Text
     * @return Changed case text
     */
    private String changeCase(String text) {
        char[] znakovi = text.toCharArray();
        for (int i = 0; i < znakovi.length; i++) {
            char c = znakovi[i];
            if (Character.isLowerCase(c)) {
                znakovi[i] = Character.toUpperCase(c);
            } else if (Character.isUpperCase(c)) {
                znakovi[i] = Character.toLowerCase(c);
            }
        }
        return new String(znakovi);
    }

    /**
     * Sort text lines method.
     * @param text Text lines
     * @param ascending True if ascending, false if descending
     * @return Sorted lines
     */
    private String sort(String text, boolean ascending) {
        StringBuilder sb = new StringBuilder();
        Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
        Collator collator = Collator.getInstance(locale);
        String[] lines = text.split("\\r?\\n");
        boolean containsBackslashR = text.contains("\r");
        boolean endsWithNew = text.endsWith("\r") || text.endsWith("\n");
        List<String> lineList = new ArrayList<>(List.of(lines));
        lineList.sort(ascending ? collator : collator.reversed());
        for (String line : lineList) {
            sb.append(line);
            if (containsBackslashR) {
                sb.append("\r");
            } else {
                sb.append("\n");
            }
        }
        if (!endsWithNew) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Remove duplicates from text lines.
     * @param text Text lines
     * @return Text lines without duplicates
     */
    private String removeDuplicates(String text) {
        StringBuilder sb = new StringBuilder();
        String[] lines = text.split("\\r?\\n");
        boolean containsBackslashR = text.contains("\r");
        boolean endsWithNew = text.endsWith("\r") || text.endsWith("\n");
        Set<String> lineSet = new HashSet<>(List.of(lines));
        for (String line : lineSet) {
            sb.append(line);
            if (containsBackslashR) {
                sb.append("\r");
            } else {
                sb.append("\n");
            }
        }
        if (!endsWithNew) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Initialize GUI.
     */
    private void initGUI() {
        this.myStatusBar = new StatusBar();
        this.changeCaseMenu = new LJMenu("case", lp);
        this.sortMenu = new LJMenu("sort", lp);
        this.model = new DefaultMultipleDocumentModel();
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(model, BorderLayout.CENTER);

        setTitle(TITLE);
        createActions();
        createMenus();
        createToolbar();
        createStatusBar();

        new Timer(1000, e -> {
            String pattern = "yyyy/MM/dd  HH:mm:ss ";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            myStatusBar.setTime(format.format(new Date()));
        }).start();

        model.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                if (previousModel == null && currentModel == null) {
                    throw new JNotepadPPException("Previous and current models null!");
                }

                String title = TITLE;
                if (currentModel != null) {
                    String file;
                    if (currentModel.getFilePath() != null) {
                        file = currentModel.getFilePath().toAbsolutePath().toString();
                    } else {
                        file = "Untitled";
                    }
                    title = file + " - " + TITLE;
                    currentModel.getTextComponent().addCaretListener(e -> updateStatusBarAndMenu());
                }
                setTitle(title);

                updateStatusBarAndMenu();
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {

            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {

            }
        });
    }

    /**
     * Create actions.
     */
    private void createActions() {
        newBlankDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control N"));
        newBlankDocumentAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_N);

        openExistingDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control O"));
        openExistingDocumentAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_O);

        saveDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control S"));
        saveDocumentAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_S);

        saveAsDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control alt S"));
        saveAsDocumentAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_W);

        closeTabAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control B"));
        closeTabAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_B);

        statisticalInfoAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control I"));
        statisticalInfoAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_I);

        exitAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control D"));
        exitAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_D);

        copyAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control C"));
        copyAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_C);

        pasteAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control V"));
        pasteAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_V);

        cutAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control X"));
        cutAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_X);

        toUpperCaseAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control U"));
        toUpperCaseAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_U);

        toLowerCaseAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control L"));
        toLowerCaseAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_L);

        invertCaseAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control T"));
        invertCaseAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_T);
    }

    /**
     * Create menus.
     */
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new LJMenu("file", lp);
        menuBar.add(fileMenu);

        fileMenu.add(new JMenuItem(newBlankDocumentAction));
        fileMenu.add(new JMenuItem(openExistingDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.add(new JMenuItem(saveAsDocumentAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(closeTabAction));
        fileMenu.add(new JMenuItem(exitAction));

        JMenu editMenu = new LJMenu("edit", lp);
        menuBar.add(editMenu);

        editMenu.add(new JMenuItem(copyAction));
        editMenu.add(new JMenuItem(pasteAction));
        editMenu.add(new JMenuItem(cutAction));

        JMenu infoMenu = new LJMenu("info", lp);
        menuBar.add(infoMenu);

        infoMenu.add(new JMenuItem(statisticalInfoAction));

        JMenu toolsMenu = new LJMenu("tools", lp);
        menuBar.add(toolsMenu);

        changeCaseMenu.add(new JMenuItem(toUpperCaseAction)).setEnabled(false);
        changeCaseMenu.add(new JMenuItem(toLowerCaseAction)).setEnabled(false);
        changeCaseMenu.add(new JMenuItem(invertCaseAction)).setEnabled(false);
        toolsMenu.add(changeCaseMenu);

        sortMenu.add(new JMenuItem(sortAscendingAction)).setEnabled(false);
        sortMenu.add(new JMenuItem(sortDescendingAction)).setEnabled(false);
        sortMenu.add(new JMenuItem(uniqueAction)).setEnabled(false);
        toolsMenu.add(sortMenu);

        JMenu langMenu = new LJMenu("languages", lp);
        menuBar.add(langMenu);

        langMenu.add(new JMenuItem(setLanguageToEn));
        langMenu.add(new JMenuItem(setLanguageToDe));
        langMenu.add(new JMenuItem(setLanguageToHr));

        this.setJMenuBar(menuBar);
    }

    /**
     * Create toolbars.
     */
    private void createToolbar() {
        JToolBar toolBar = new JToolBar("Toolbar");
        toolBar.setFloatable(true);

        toolBar.add(new JButton(newBlankDocumentAction));
        toolBar.add(new JButton(openExistingDocumentAction));
        toolBar.add(new JButton(saveDocumentAction));
        toolBar.add(new JButton(saveAsDocumentAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(statisticalInfoAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(closeTabAction));

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Create status bar.
     */
    private void createStatusBar() {
        JToolBar statusBar = new JToolBar("Statusbar");
        statusBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        statusBar.setFloatable(true);
        statusBar.add(myStatusBar.length);
        statusBar.addSeparator();
        statusBar.add(myStatusBar.position);
        statusBar.addSeparator();
        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(myStatusBar.time);

        this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
    }

    /**
     * Update status bar and menu buttons availability.
     */
    private void updateStatusBarAndMenu() {
        if (model.getCurrentDocument() == null) {
            myStatusBar.setLength("");
            myStatusBar.setPosition("");
        } else {
            JTextArea textComponent = model.getCurrentDocument().getTextComponent();
            myStatusBar.setLength(lp.getString("len") + ": " + textComponent.getText().length() + " ".repeat(10));
            int caretPos = textComponent.getCaretPosition();
            int ln = 0;
            int col = 0;
            try {
                ln = textComponent.getLineOfOffset(caretPos);
                col = caretPos - textComponent.getLineStartOffset(ln);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            int sel = Math.abs(textComponent.getCaret().getDot() - textComponent.getCaret().getMark());
            for (int i = 0; i < changeCaseMenu.getItemCount(); i++) {
                changeCaseMenu.getItem(i).setEnabled(sel != 0);
            }
            for (int i = 0; i < sortMenu.getItemCount(); i++) {
                sortMenu.getItem(i).setEnabled(sel != 0);
            }
            myStatusBar.setPosition("Ln: " + (ln + 1) + "  Col: " + (col + 1) + "  Sel: " + sel + " ");
        }
    }

    /**
     * {@link JNotepadPP} status bar.
     * @author Filip Vucic
     */
    private static class StatusBar {
        private final JLabel length;
        private final JLabel position;
        private final JLabel time;

        public StatusBar() {
            this.length = new JLabel();
            this.position = new JLabel();
            this.time = new JLabel();
        }

        public void setLength(String length) {
            this.length.setText(length);
        }

        public void setPosition(String position) {
            this.position.setText(position);
        }

        public void setTime(String time) {
            this.time.setText(time);
        }
    }
}
