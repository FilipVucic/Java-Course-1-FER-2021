package hr.fer.oprpp1.math;

/**
 * Class which represents a 2-dimensional vector with its
 * x and y double components.
 *
 * @author Filip Vucic
 */
public class Vector2D {

    /**
     * x component of the {@link Vector2D}.
     */
    private double x;

    /**
     * y component of the {@link Vector2D}.
     */
    private double y;

    /**
     * Create new {@link Vector2D} with its x and y components.
     *
     * @param x x component
     * @param y y component
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x component of this {@link Vector2D}.
     *
     * @return x component
     */
    public double getX() {
        return x;
    }

    /**
     * Get y component of this {@link Vector2D}.
     *
     * @return y component
     */
    public double getY() {
        return y;
    }

    /**
     * Add another offset {@link Vector2D} to this {@link Vector2D}.
     *
     * @param offset Offset {@link Vector2D}
     */
    public void add(Vector2D offset) {
        x += offset.x;
        y += offset.y;
    }

    /**
     * Add another offset {@link Vector2D} to this {@link Vector2D} and
     * return it. This {@link Vector2D} is not changed.
     *
     * @param offset Offset {@link Vector2D}
     * @return New added {@link Vector2D}
     */
    public Vector2D added(Vector2D offset) {
        return new Vector2D(x + offset.x, y + offset.y);
    }

    /**
     * Rotate this {@link Vector2D} with given angle.
     *
     * @param angle Angle of rotation
     */
    public void rotate(double angle) {
        double oldX = x;
        double oldY = y;

        x = oldX * Math.cos(angle) - oldY * Math.sin(angle);
        y = oldX * Math.sin(angle) + oldY * Math.cos(angle);
    }

    /**
     * Rotate this {@link Vector2D} with given angle and return
     * it. This {@link Vector2D} is not changed.
     *
     * @param angle Angle of rotation
     * @return New rotated {@link Vector2D}
     */
    public Vector2D rotated(double angle) {
        return new Vector2D(x * Math.cos(angle) - y * Math.sin(angle),
                x * Math.sin(angle) + y * Math.cos(angle));
    }

    /**
     * Scale this {@link Vector2D} with given scaler.
     *
     * @param scaler Scaler
     */
    public void scale(double scaler) {
        x *= scaler;
        y *= scaler;
    }

    /**
     * Scale this {@link Vector2D} with given scaler and return
     * it. This {@link Vector2D} is not changed.
     *
     * @param scaler Scaler
     * @return New scaled {@link Vector2D}
     */
    public Vector2D scaled(double scaler) {
        return new Vector2D(x * scaler, y * scaler);
    }

    /**
     * Return a copy of this {@link Vector2D}.
     *
     * @return Copy of this {@link Vector2D}
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    /**
     * This method is used for normalizing this vector.
     *
     * @return Normalized vector
     */
    public Vector2D normalized() {
        double magnitude = Math.sqrt(x * x + y * y);

        if (magnitude <= 0) {
            return new Vector2D(x, y);
        }

        return new Vector2D(x / magnitude, y / magnitude);
    }
}
