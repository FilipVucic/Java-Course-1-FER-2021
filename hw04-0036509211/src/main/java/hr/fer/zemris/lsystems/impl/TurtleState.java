package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.math.Vector2D;

import java.awt.*;

/**
 * Class which represents turtle state.
 *
 * @author Filip Vucic
 */
public class TurtleState {

    /**
     * Position of the turtle.
     */
    private Vector2D turtlePosition;

    /**
     * Direction of the turtle.
     */
    private Vector2D turtleDirection;

    /**
     * Drawing color of the turtle.
     */
    private Color turtleDrawingColor;

    /**
     * Turtle offset.
     */
    private double offset;

    /**
     * Create new {@link TurtleState} with given turtle position, direction, drawing color and offset.
     *
     * @param turtlePosition     Position of the turtle
     * @param turtleDirection    Direction of the turtle
     * @param turtleDrawingColor Drawing color of the turtle
     * @param offset             Offset of the turtle
     */
    public TurtleState(Vector2D turtlePosition, Vector2D turtleDirection, Color turtleDrawingColor, double offset) {
        this.turtlePosition = turtlePosition;
        this.turtleDirection = turtleDirection;
        this.turtleDrawingColor = turtleDrawingColor;
        this.offset = offset;
    }

    /**
     * Return copy of this {@link TurtleState}.
     *
     * @return Copy of this {@link TurtleState}
     */
    public TurtleState copy() {
        return new TurtleState(turtlePosition.copy(), turtleDirection.copy(), turtleDrawingColor, offset);
    }

    /**
     * Get turtle position.
     *
     * @return Turtle position
     */
    public Vector2D getTurtlePosition() {
        return turtlePosition;
    }

    /**
     * Set turtle position.
     *
     * @param turtlePosition Turtle position
     */
    public void setTurtlePosition(Vector2D turtlePosition) {
        this.turtlePosition = turtlePosition;
    }

    /**
     * Get turtle direction.
     *
     * @return Turtle direction
     */
    public Vector2D getTurtleDirection() {
        return turtleDirection;
    }

    /**
     * Set turtle direction.
     *
     * @param turtleDirection Turtle direction
     */
    public void setTurtleDirection(Vector2D turtleDirection) {
        this.turtleDirection = turtleDirection;
    }

    /**
     * Get turtle drawing color.
     *
     * @return Turtle drawing color
     */
    public Color getTurtleDrawingColor() {
        return turtleDrawingColor;
    }

    /**
     * Set turtle drawing color.
     *
     * @param turtleDrawingColor Turtle drawing color
     */
    public void setTurtleDrawingColor(Color turtleDrawingColor) {
        this.turtleDrawingColor = turtleDrawingColor;
    }

    /**
     * Get turtle offset.
     *
     * @return Turtle offset
     */
    public double getOffset() {
        return offset;
    }

    /**
     * Set turtle offset.
     *
     * @param offset Turtle offset
     */
    public void setOffset(double offset) {
        this.offset = offset;
    }
}
