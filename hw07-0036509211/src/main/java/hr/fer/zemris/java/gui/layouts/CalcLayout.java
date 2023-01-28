package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculator Layout.
 *
 * @author Filip Vucic
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * Gap between the components.
     */
    private final int gap;

    /**
     * Components.
     */
    private final Map<RCPosition, Component> components = new HashMap<>();

    /**
     * Create new {@link CalcLayout} with given gap between the components.
     *
     * @param gap Gap between the components
     */
    public CalcLayout(int gap) {
        this.gap = gap;
    }

    /**
     * Create new {@link CalcLayout}.
     */
    public CalcLayout() {
        this(0);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (comp == null || constraints == null) {
            throw new NullPointerException();
        }

        RCPosition position;
        if (constraints instanceof String) {
            position = RCPosition.parse((String) constraints);
        } else if (constraints instanceof RCPosition) {
            position = (RCPosition) constraints;
        } else {
            throw new IllegalArgumentException("Invalid instance!");
        }

        if (components.containsKey(position)) {
            throw new CalcLayoutException("Already contains!");
        }
        if (position.getRow() > 5 || position.getRow() < 1
                || position.getColumn() > 7 || position.getColumn() < 1) {
            throw new CalcLayoutException("Invalid position!");
        }
        if (position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6) {
            throw new CalcLayoutException("Invalid position!");
        }
        components.put(position, comp);
    }

    /**
     * Get layout size.
     *
     * @param parent Parent
     * @param getter Getter
     * @return Layout dimension
     */
    private Dimension getLayoutSize(Container parent, SizeGetter getter) {
        int height = 0;
        int width = 0;

        for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
            Dimension compDim = getter.get(entry.getValue());
            if (entry.getKey().getRow() == 1 && entry.getKey().getColumn() == 1) {
                compDim.width -= 4 * gap;
                compDim.width /= 5;
            }
            if (compDim.height > height) {
                height = compDim.height;
            }
            if (compDim.width > width) {
                width = compDim.width;
            }
        }

        Insets ins = parent.getInsets();

        return new Dimension(width * 7 + gap * 6 + ins.left + ins.right,
                height * 5 + gap * 4 + ins.top + ins.bottom);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
            if (entry.getValue().equals(comp)) {
                components.remove(entry.getKey());
            }
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return getLayoutSize(target, Component::getMaximumSize);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getLayoutSize(parent, Component::getPreferredSize);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getLayoutSize(parent, Component::getMinimumSize);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets ins = parent.getInsets();
        System.out.println("Pozvan layoutContainer!");

        int w = parent.getWidth() - ins.left - ins.right - 6 * gap;
        int[] componentsW = Util.balanceDimension(w, 7);

        int h = parent.getHeight() - ins.top - ins.bottom - 4 * gap;
        int[] componentsH = Util.balanceDimension(h, 5);

        int y = ins.top;
        for (int i = 0; i < 5; i++) {
            int x = ins.left;
            int compH = componentsH[i];
            for (int j = 0; j < 7; j++) {
                RCPosition position = new RCPosition(i + 1, j + 1);
                int compW = componentsW[j];
                if (components.containsKey(position)) {

                    if (position.getRow() == 1 && position.getColumn() == 1) {
                        for (int z = 1; z < 5; z++) {
                            compW += componentsW[z] + gap;
                        }
                        j = 4;
                    }
                    components.get(position).setBounds(x, y, compW, compH);
                }
                x += compW + gap;
            }
            y += compH + gap;
        }
    }
}
