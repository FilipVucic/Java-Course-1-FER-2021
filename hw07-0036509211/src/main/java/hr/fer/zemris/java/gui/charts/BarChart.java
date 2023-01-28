package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Bar Chart containing {@link XYValue} values, x and y axis descriptions, miny, maxy and y diff on graph..
 *
 * @author Filip Vucic
 */
public class BarChart {

    /**
     * List of {@link XYValue} values.
     */
    private final List<XYValue> values;

    /**
     * x axis description.
     */
    private final String xAxisDescription;

    /**
     * y axis description.
     */
    private final String yAxisDescription;

    /**
     * min y on the graph y axis.
     */
    private final int miny;

    /**
     * max y on the graph y axis.
     */
    private final int maxy;

    /**
     * diff between every y on y axis.
     */
    private final int diffy;

    /**
     * Create new {@link BarChart}.
     *
     * @param values           Values
     * @param xAxisDescription x axis description
     * @param yAxisDescription y axis description
     * @param miny             min y on the graph y axis
     * @param maxy             max y on the graph y axis.
     * @param diffy            diff y on the graph y axis.
     */
    public BarChart(List<XYValue> values, String xAxisDescription,
                    String yAxisDescription, int miny, int maxy, int diffy) {
        if (miny < 0) {
            throw new IllegalArgumentException("Min y can't be negative!");
        }
        if (maxy <= miny) {
            throw new IllegalArgumentException("Max y must be greater than min y!");
        }

        for (XYValue value : values) {
            if (value.getY() < miny) {
                throw new IllegalArgumentException("Y of the value: " + value + " is less than min y!");
            }
        }

        if ((maxy - miny) % diffy != 0) {
            this.maxy = miny + diffy * (1 + ((maxy - miny) / diffy));
        } else {
            this.maxy = maxy;
        }
        this.values = values;
        this.xAxisDescription = xAxisDescription;
        this.yAxisDescription = yAxisDescription;
        this.miny = miny;
        this.diffy = diffy;
    }

    /**
     * Get {@link XYValue} values.
     *
     * @return values
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Get x axis description.
     *
     * @return x axis description
     */
    public String getxAxisDescription() {
        return xAxisDescription;
    }

    /**
     * Get y axis description
     *
     * @return y axis description
     */
    public String getyAxisDescription() {
        return yAxisDescription;
    }

    /**
     * Get min y.
     *
     * @return min y
     */
    public int getMiny() {
        return miny;
    }

    /**
     * Get max y.
     *
     * @return max y
     */
    public int getMaxy() {
        return maxy;
    }

    /**
     * Get diff between every y.
     *
     * @return diff between every y
     */
    public int getDiffy() {
        return diffy;
    }
}
