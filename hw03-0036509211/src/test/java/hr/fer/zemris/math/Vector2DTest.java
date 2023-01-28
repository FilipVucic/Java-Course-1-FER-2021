// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.zemris.math;

import hr.fer.oprpp1.math.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2DTest {

    @Test
    void getX() {
        Vector2D vector2D = new Vector2D(20.5, 30.5);

        assertEquals(20.5, vector2D.getX());
    }

    @Test
    void getY() {
        Vector2D vector2D = new Vector2D(20.5, 30.5);

        assertEquals(30.5, vector2D.getY());
    }

    @Test
    void translate() {
        Vector2D vector2D = new Vector2D(20.5, 30.5);
        Vector2D offset = new Vector2D(2.5, 3.5);

        vector2D.add(offset);

        assertEquals(23, vector2D.getX());
        assertEquals(34, vector2D.getY());
    }

    @Test
    void translated() {
        Vector2D vector2D = new Vector2D(20.5, 30.5);
        Vector2D offset = new Vector2D(2.5, 3.5);

        Vector2D addedVector = vector2D.added(offset);

        assertEquals(23, addedVector.getX());
        assertEquals(34, addedVector.getY());

        assertEquals(20.5, vector2D.getX());
        assertEquals(30.5, vector2D.getY());
    }

    @Test
    void rotate() {
        Vector2D vector2D = new Vector2D(20, 20);

        vector2D.rotate(Math.PI / 4);

        assertEquals(0, vector2D.getX(), 10E-7);
        assertEquals(20 * Math.sqrt(2), vector2D.getY(), 10E-7);
    }

    @Test
    void rotated() {
        Vector2D vector2D = new Vector2D(20, 20);

        Vector2D rotatedVector = vector2D.rotated(Math.PI / 4);

        assertEquals(0, rotatedVector.getX(), 10E-7);
        assertEquals(20 * Math.sqrt(2), rotatedVector.getY(), 10E-7);

        assertEquals(20, vector2D.getX());
        assertEquals(20, vector2D.getY());
    }

    @Test
    void scale() {
        Vector2D vector2D = new Vector2D(20.5, 30.5);

        vector2D.scale(2);

        assertEquals(41, vector2D.getX());
        assertEquals(61, vector2D.getY());
    }

    @Test
    void scaled() {
        Vector2D vector2D = new Vector2D(20.5, 30.5);

        Vector2D scaledVector = vector2D.scaled(2);

        assertEquals(41, scaledVector.getX());
        assertEquals(61, scaledVector.getY());

        assertEquals(20.5, vector2D.getX());
        assertEquals(30.5, vector2D.getY());
    }

    @Test
    void copy() {
        Vector2D vector2D = new Vector2D(20.5, 30.5);

        Vector2D copiedVector = vector2D.copy();

        assertEquals(20.5, copiedVector.getX());
        assertEquals(30.5, copiedVector.getY());

        assertEquals(20.5, vector2D.getX());
        assertEquals(30.5, vector2D.getY());
    }
}