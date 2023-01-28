package hr.fer.zemris.java.gui.charts;

/**
 * XY value containing x and y.
 *
 * @author Filip Vucic
 */
public class XYValue {

    /**
     * x value.
     */
    private final int x;

    /**
     * y value.
     */
    private final int y;

    /**
     * Create new {@link XYValue} with given x and y value.
     *
     * @param x x value
     * @param y y value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x value.
     *
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * Get y value.
     *
     * @return y value
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "XYValue{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
