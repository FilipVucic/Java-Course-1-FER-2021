package hr.fer.zemris.java.gui.layouts;

import java.awt.*;

/**
 * Strategy for getting size of the component.
 *
 * @author Filip Vucic
 */
public interface SizeGetter {

    /**
     * Get dimension of the component.
     *
     * @param comp Component
     * @return Dimension of the component
     */
    Dimension get(Component comp);
}
