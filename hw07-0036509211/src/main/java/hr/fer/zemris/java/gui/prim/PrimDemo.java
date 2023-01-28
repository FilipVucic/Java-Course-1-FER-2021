package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

/**
 * Prim Demo.
 *
 * @author Filip Vucic
 */
public class PrimDemo extends JFrame {

    /**
     * Create new {@link PrimDemo}.
     */
    public PrimDemo() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
        setSize(500, 500);
    }

    /**
     * Main program.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
    }

    /**
     * Initialize GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        PrimListModel primListModel = new PrimListModel();

        JPanel listPanel = new JPanel(new GridLayout(0, 2));
        listPanel.add(new JScrollPane(new JList<>(primListModel)));
        listPanel.add(new JScrollPane(new JList<>(primListModel)));
        cp.add(listPanel, BorderLayout.CENTER);

        JButton next = new JButton("sljedeći");
        next.addActionListener(e -> primListModel.next());
        cp.add(next, BorderLayout.PAGE_END);
    }
}
