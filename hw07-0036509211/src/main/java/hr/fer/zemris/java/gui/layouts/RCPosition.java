package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Position containing row and column.
 *
 * @author Filip Vucic
 */
public class RCPosition {

    /**
     * Row.
     */
    private final int row;

    /**
     * Column.
     */
    private final int column;

    /**
     * Create new {@link RCPosition} with given row and column.
     *
     * @param row    Row
     * @param column Column
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Parse {@link RCPosition} string.
     *
     * @param text String
     * @return Parsed {@link RCPosition}
     */
    public static RCPosition parse(String text) {
        String[] elements = text.trim().split("\\s*,\\s*");

        if (elements.length != 2) {
            throw new IllegalArgumentException("RCPosition must be created with row and column divided by comma!");
        }

        int row;
        int column;
        try {
            row = Integer.parseInt(elements[0]);
            column = Integer.parseInt(elements[1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Can't parse!");
        }

        return new RCPosition(row, column);
    }

    /**
     * Get row.
     *
     * @return Row
     */
    public int getRow() {
        return row;
    }

    /**
     * Get column.
     *
     * @return Column
     */
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition position = (RCPosition) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
