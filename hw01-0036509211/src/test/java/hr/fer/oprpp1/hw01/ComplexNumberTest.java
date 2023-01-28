package hr.fer.oprpp1.hw01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComplexNumberTest {

    @Test
    void fromReal() {
        ComplexNumber complexNumber = ComplexNumber.fromReal(2);

        assertEquals(2, complexNumber.getReal());
        assertEquals(0, complexNumber.getImaginary());
    }

    @Test
    void fromImaginary() {
        ComplexNumber complexNumber = ComplexNumber.fromImaginary(-2);

        assertEquals(0, complexNumber.getReal());
        assertEquals(-2, complexNumber.getImaginary());
    }

    @Test
    void fromMagnitudeAndAngle() {
        ComplexNumber complexNumber = ComplexNumber.fromMagnitudeAndAngle(5, Math.PI / 2);

        assertEquals(5, complexNumber.getImaginary());
        assertEquals(0, complexNumber.getReal(), 1E-8);
    }

    @Test
    void parse() {
        ComplexNumber complexNumber1 = ComplexNumber.parse("i");
        assertEquals(0, complexNumber1.getReal());
        assertEquals(1, complexNumber1.getImaginary());

        ComplexNumber complexNumber2 = ComplexNumber.parse("+i");
        assertEquals(0, complexNumber2.getReal());
        assertEquals(1, complexNumber2.getImaginary());

        ComplexNumber complexNumber3 = ComplexNumber.parse("-i");
        assertEquals(0, complexNumber3.getReal());
        assertEquals(-1, complexNumber3.getImaginary());

        ComplexNumber complexNumber4 = ComplexNumber.parse("2");
        assertEquals(2, complexNumber4.getReal());
        assertEquals(0, complexNumber4.getImaginary());

        ComplexNumber complexNumber5 = ComplexNumber.parse("-2");
        assertEquals(-2, complexNumber5.getReal());
        assertEquals(0, complexNumber5.getImaginary());

        ComplexNumber complexNumber6 = ComplexNumber.parse("2+3i");
        assertEquals(2, complexNumber6.getReal());
        assertEquals(3, complexNumber6.getImaginary());

        ComplexNumber complexNumber8 = ComplexNumber.parse("+2+3i");
        assertEquals(2, complexNumber8.getReal());
        assertEquals(3, complexNumber8.getImaginary());

        ComplexNumber complexNumber9 = ComplexNumber.parse("-2+3i");
        assertEquals(-2, complexNumber9.getReal());
        assertEquals(3, complexNumber9.getImaginary());

        ComplexNumber complexNumber10 = ComplexNumber.parse("-2-i");
        assertEquals(-2, complexNumber10.getReal());
        assertEquals(-1, complexNumber10.getImaginary());

        assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-2-+3i"));
        assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-2+-3i"));
        assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("+-3i"));
        assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("++3i"));
    }

    @Test
    void getReal() {
        ComplexNumber complexNumber = ComplexNumber.fromReal(2);
        assertEquals(2, complexNumber.getReal());
    }

    @Test
    void getImaginary() {
        ComplexNumber complexNumber = ComplexNumber.fromImaginary(-2);
        assertEquals(-2, complexNumber.getImaginary());
    }

    @Test
    void getMagnitude() {
        ComplexNumber complexNumber1 = new ComplexNumber(2, 2);
        assertEquals(2.82842712475, complexNumber1.getMagnitude(), 1E-8);

        ComplexNumber complexNumber2 = new ComplexNumber(5, 0);
        assertEquals(5, complexNumber2.getMagnitude());

    }

    @Test
    void getAngle() {
        ComplexNumber complexNumber1 = new ComplexNumber(2, 2);
        assertEquals(Math.PI / 4, complexNumber1.getAngle());

        ComplexNumber complexNumber2 = new ComplexNumber(-2, 2);
        assertEquals(3 * Math.PI / 4, complexNumber2.getAngle());

        ComplexNumber complexNumber3 = new ComplexNumber(2, -2);
        assertEquals(7 * Math.PI / 4, complexNumber3.getAngle());

        ComplexNumber complexNumber4 = new ComplexNumber(-2, -2);
        assertEquals(5 * Math.PI / 4, complexNumber4.getAngle());
    }

    @Test
    void add() {
        ComplexNumber complexNumber1 = new ComplexNumber(2, 2);
        ComplexNumber complexNumber2 = new ComplexNumber(4, 3);
        ComplexNumber complexNumber3 = complexNumber1.add(complexNumber2);

        assertEquals(6, complexNumber3.getReal());
        assertEquals(5, complexNumber3.getImaginary());
    }

    @Test
    void sub() {
        ComplexNumber complexNumber1 = new ComplexNumber(2, 2);
        ComplexNumber complexNumber2 = new ComplexNumber(4, 3);
        ComplexNumber complexNumber3 = complexNumber1.sub(complexNumber2);

        assertEquals(-2, complexNumber3.getReal());
        assertEquals(-1, complexNumber3.getImaginary());
    }

    @Test
    void mul() {
        ComplexNumber complexNumber1 = new ComplexNumber(2, 2);
        ComplexNumber complexNumber2 = new ComplexNumber(4, 3);

        ComplexNumber complexNumber3 = complexNumber1.mul(complexNumber2);

        assertEquals(2, complexNumber3.getReal());
        assertEquals(14, complexNumber3.getImaginary());
    }

    @Test
    void div() {
        ComplexNumber complexNumber1 = new ComplexNumber(2, 2);
        ComplexNumber complexNumber2 = new ComplexNumber(4, 3);

        ComplexNumber complexNumber3 = complexNumber1.div(complexNumber2);

        assertEquals(0.56, complexNumber3.getReal());
        assertEquals(0.08, complexNumber3.getImaginary());
    }

    @Test
    void power() {
        ComplexNumber complexNumber1 = new ComplexNumber(4, 3);

        ComplexNumber complexNumber2 = complexNumber1.power(3);

        assertEquals(-44, complexNumber2.getReal(), 1E-8);
        assertEquals(117, complexNumber2.getImaginary(), 1E-8);

    }

    @Test
    void root() {
        ComplexNumber complexNumber1 = new ComplexNumber(4, 3);

        ComplexNumber[] complexNumberRoots = complexNumber1.root(4);

        assertEquals(1.47604002673698, complexNumberRoots[0].getReal(), 1E-8);
        assertEquals(0.23952832185375, complexNumberRoots[0].getImaginary(), 1E-8);
        assertEquals(-0.23952832185375, complexNumberRoots[1].getReal(), 1E-8);
        assertEquals(1.47604002673698, complexNumberRoots[1].getImaginary(), 1E-8);
        assertEquals(-1.47604002673698, complexNumberRoots[2].getReal(), 1E-8);
        assertEquals(-0.23952832185375, complexNumberRoots[2].getImaginary(), 1E-8);
        assertEquals(0.23952832185375, complexNumberRoots[3].getReal(), 1E-8);
        assertEquals(-1.47604002673698, complexNumberRoots[3].getImaginary(), 1E-8);
    }

    @Test
    void testToString() {
        ComplexNumber complexNumber1 = new ComplexNumber(0, 1);
        assertEquals("i", complexNumber1.toString());

        ComplexNumber complexNumber2 = new ComplexNumber(0, -1);
        assertEquals("-i", complexNumber2.toString());

        ComplexNumber complexNumber3 = new ComplexNumber(0, -2);
        assertEquals("-2.0i", complexNumber3.toString());

        ComplexNumber complexNumber4 = new ComplexNumber(-3, 0);
        assertEquals("-3.0", complexNumber4.toString());

        ComplexNumber complexNumber5 = new ComplexNumber(-3, -2);
        assertEquals("-3.0-2.0i", complexNumber5.toString());

        ComplexNumber complexNumber6 = new ComplexNumber(+3, -2);
        assertEquals("3.0-2.0i", complexNumber6.toString());

        ComplexNumber complexNumber7 = new ComplexNumber(-3, +1);
        assertEquals("-3.0+1.0i", complexNumber7.toString());
    }
}