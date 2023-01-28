package hr.fer.zemris.java.gui.charts;

import hr.fer.zemris.java.gui.layouts.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * BarChart component used for displaying {@link BarChart}.
 *
 * @author Filip Vucic
 */
public class BarChartComponent extends JComponent {

    /**
     * {@link BarChart} that will be displayed.
     */
    private final BarChart chart;

    /**
     * Create new {@link BarChartComponent} with given {@link BarChart} that will be displayed.
     *
     * @param chart {@link BarChart} that will be displayed.
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }


    @Override
    public void paintComponent(Graphics g) {
        final int space = 15;
        final int dash = 5;
        final int smallSpace = 5;

        Graphics2D graphics2D = (Graphics2D) g;
        FontMetrics fm = graphics2D.getFontMetrics();
        Dimension dim = getSize();

        int xStart = fm.getHeight() + space + fm.stringWidth(String.valueOf(chart.getMaxy())) + space;
        int yStart = dim.height - (fm.getHeight() + space + fm.getHeight() + space);
        int xEnd = dim.width - space;
        graphics2D.setColor(Color.BLACK);

        Line2D.Double x = new Line2D.Double(xStart - dash, yStart, xEnd + smallSpace, yStart);
        Line2D.Double y = new Line2D.Double(xStart, yStart + dash, xStart, space);

        graphics2D.draw(x);
        graphics2D.draw(y);
        drawArrowHead(graphics2D, x);
        drawArrowHead(graphics2D, y);

        AffineTransform defaultAt = graphics2D.getTransform();
        graphics2D.drawString(chart.getxAxisDescription(), (dim.width - fm.stringWidth(chart.getxAxisDescription()) + xStart - space) / 2, dim.height - fm.getHeight());
        AffineTransform at = new AffineTransform(defaultAt);
        at.rotate(-Math.PI / 2);
        graphics2D.setTransform(at);
        graphics2D.drawString(chart.getyAxisDescription(), -(fm.stringWidth(chart.getyAxisDescription()) + yStart + space) / 2, fm.getHeight());
        graphics2D.setTransform(defaultAt);

        double yLocation = yStart;
        double yRange = yStart - space * 2;
        double nOfY = (double) chart.getMaxy() / (double) chart.getDiffy();
        double rowHeight = yRange / nOfY;
        graphics2D.drawString(String.valueOf(0), xStart - dash - space + 10 - fm.stringWidth(String.valueOf(0)), (int) yLocation - fm.getHeight() / 2 + fm.getAscent() - 1);

        for (int i = 0; i < chart.getMaxy() / chart.getDiffy(); i++) {
            yLocation -= rowHeight;
            graphics2D.draw(new Line2D.Double(xStart - dash, yLocation, xEnd, yLocation));
            graphics2D.drawString(String.valueOf(chart.getDiffy() * (i + 1)), xStart - dash - space + 10 - fm.stringWidth(String.valueOf(chart.getDiffy() * (i + 1))), (int) yLocation - fm.getHeight() / 2 + fm.getAscent() - 1);
        }

        double xLocation = xStart;
        double xRange = xEnd - xStart - space;
        double columnWidth = xRange / chart.getValues().size();
        for (int i = 1; i <= chart.getValues().size(); i++) {
            double rectHeight = 0;
            for (int j = 0; j < chart.getValues().get(i - 1).getY() / chart.getDiffy(); j++) {
                rectHeight += rowHeight;
            }
            graphics2D.setColor(Color.ORANGE);
            graphics2D.fill(new Rectangle2D.Double(xLocation + 1, yStart - rectHeight + 1, columnWidth, rectHeight - 1));
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(String.valueOf(chart.getValues().get(i - 1).getX()), (int) xLocation + (int) columnWidth / 2 + fm.getHeight() / 2 - fm.getAscent() - 1, dim.height - (fm.getHeight() + space + space));
            graphics2D.draw(new Line2D.Double(xLocation, yStart + dash, xLocation, space));
            xLocation += columnWidth;
        }
        graphics2D.draw(new Line2D.Double(xLocation, yStart + dash, xLocation, space));
    }

    /**
     * Draw the arrow head.
     *
     * @param g2   {@link Graphics2D} to be drawed on
     * @param line Arrow body
     */
    private void drawArrowHead(Graphics2D g2, Line2D.Double line) {
        AffineTransform tx = g2.getTransform();
        double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
        tx.translate(line.x2, line.y2);
        tx.rotate((angle - Math.PI / 2d));
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 5);
        arrowHead.addPoint(-5, -5);
        arrowHead.addPoint(5, -5);

        Graphics2D g = (Graphics2D) g2.create();
        g.setTransform(tx);
        g.fill(arrowHead);
        g.dispose();
    }

}