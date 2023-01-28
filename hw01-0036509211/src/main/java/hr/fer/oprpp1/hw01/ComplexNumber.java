package hr.fer.oprpp1.hw01;

/**
 * @author Filip Vucic
 * Class which represents Complex Number with its real and imaginary part.
 */
public class ComplexNumber {

    /**
     * Real part of {@link ComplexNumber}.
     */
    double realPart;

    /**
     * Imaginary part of {@link ComplexNumber}.
     */
    double imaginaryPart;

    /**
     * Initialize {@link ComplexNumber} with given real and imaginary part.
     *
     * @param realPart      Real part of {@link ComplexNumber}
     * @param imaginaryPart Imaginary part of {@link ComplexNumber}
     */
    public ComplexNumber(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    /**
     * Create new {@link ComplexNumber} with given real part, and return it.
     * Imaginary part will be 0.
     *
     * @param real Real part of {@link ComplexNumber}
     * @return New {@link ComplexNumber}
     */
    public static ComplexNumber fromReal(double real) {
        return new ComplexNumber(real, 0);
    }

    /**
     * Create new {@link ComplexNumber} with given imaginary part, and return it.
     * Real part will be 0.
     *
     * @param imaginary Imaginary part of {@link ComplexNumber}
     * @return New {@link ComplexNumber}
     */
    public static ComplexNumber fromImaginary(double imaginary) {
        return new ComplexNumber(0, imaginary);
    }

    /**
     * Create new {@link ComplexNumber} with given magnitude and angle, and return it.
     *
     * @param magnitude Magnitude of {@link ComplexNumber}
     * @param angle     Angle of {@link ComplexNumber}
     * @return New {@link ComplexNumber}
     */
    public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
        return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    /**
     * Parse {@link ComplexNumber} from string.
     *
     * @param s Complex Number string
     * @return New {@link ComplexNumber}
     */
    public static ComplexNumber parse(String s) {
        double realPart = 0;
        double imaginaryPart = 0;

//        if (s.contains("++") || s.contains("--") || s.contains("+-") || s.contains("-+")) {
//            throw new IllegalArgumentException("Can not parse this string!");
//        }

        if (s.equals("i") || s.equals("+i")) {
            realPart = 0;
            imaginaryPart = 1;
        } else if (s.equals("-i")) {
            realPart = 0;
            imaginaryPart = -1;
        } else if (!s.contains("i")) {
            realPart = Double.parseDouble(s);
            imaginaryPart = 0;
        } else {
            String realPartString;
            String imaginaryPartString;

            int indexOfi = s.indexOf("i");
            imaginaryPartString = s.substring(0, indexOfi);
            for (int i = indexOfi - 1; i >= 0; i--) {
                if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                    imaginaryPartString = s.substring(i, indexOfi);
                    try {
                        if (i == indexOfi - 1) {
                            imaginaryPart = Double.parseDouble(imaginaryPartString + "1");
                        } else {
                            imaginaryPart = Double.parseDouble(imaginaryPartString);
                        }
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Can not parse this string!");
                    }
                    break;
                }
            }

            realPartString = s.replace(imaginaryPartString + "i", "");
            if (realPartString.equals("")) {
                realPart = 0;
            } else {
                try {
                    realPart = Double.parseDouble(realPartString);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Can not parse this string!");
                }
            }
        }

        return new ComplexNumber(realPart, imaginaryPart);
    }

    /**
     * Get real part of this {@link ComplexNumber}.
     *
     * @return Real part of this {@link ComplexNumber}
     */
    public double getReal() {
        return realPart;
    }

    /**
     * Get imaginary part of this {@link ComplexNumber}.
     *
     * @return Imaginary part of this {@link ComplexNumber}
     */
    public double getImaginary() {
        return imaginaryPart;
    }

    /**
     * Get magnitude of this {@link ComplexNumber}.
     *
     * @return Magnitude of this {@link ComplexNumber}
     */
    public double getMagnitude() {
        return Math.sqrt(realPart * realPart + imaginaryPart * imaginaryPart);
    }

    /**
     * Get angle of this {@link ComplexNumber}.
     *
     * @return Angle of this {@link ComplexNumber}
     */
    public double getAngle() {
        double angle = Math.atan2(imaginaryPart, realPart);
        if (angle < 0) {
            angle += Math.PI * 2;
        }

        return angle;
    }

    /**
     * Add given {@link ComplexNumber} to this {@link ComplexNumber}.
     *
     * @param c {@link ComplexNumber} to be added
     * @return New {@link ComplexNumber} which represents a sum
     */
    public ComplexNumber add(ComplexNumber c) {
        return new ComplexNumber(this.realPart + c.realPart, this.imaginaryPart + c.imaginaryPart);
    }

    /**
     * Subtract given {@link ComplexNumber} from this {@link ComplexNumber}.
     *
     * @param c {@link ComplexNumber} to be subtracted
     * @return New {@link ComplexNumber} which represents a subtraction
     */
    public ComplexNumber sub(ComplexNumber c) {
        return new ComplexNumber(this.realPart - c.realPart, this.imaginaryPart - c.imaginaryPart);
    }

    /**
     * Multiply given {@link ComplexNumber} to this {@link ComplexNumber}.
     *
     * @param c {@link ComplexNumber} to be multiplied
     * @return New {@link ComplexNumber} which represents a multiplication
     */
    public ComplexNumber mul(ComplexNumber c) {
        return new ComplexNumber(this.realPart * c.realPart - this.imaginaryPart * c.imaginaryPart,
                this.realPart * c.imaginaryPart + c.realPart * this.imaginaryPart);
    }

    /**
     * Divide given {@link ComplexNumber} from this {@link ComplexNumber}.
     *
     * @param c {@link ComplexNumber} to be divided
     * @return New {@link ComplexNumber} which represents a division
     */
    public ComplexNumber div(ComplexNumber c) {
        double denominator = c.realPart * c.realPart + c.imaginaryPart * c.imaginaryPart;
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator should not be 0!");
        }

        return new ComplexNumber((this.realPart * c.realPart + this.imaginaryPart * c.imaginaryPart) / denominator,
                (this.imaginaryPart * c.realPart - this.realPart * c.imaginaryPart) / denominator);
    }

    /**
     * Power this {@link ComplexNumber} to n and return powered {@link ComplexNumber}.
     *
     * @param n Power n
     * @return New {@link ComplexNumber}
     */
    public ComplexNumber power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n should not be less than 0!");
        }

        return fromMagnitudeAndAngle(Math.pow(this.getMagnitude(), n), this.getAngle() * n);
    }

    /**
     * Calculate all n-roots of this {@link ComplexNumber}.
     *
     * @param n Number of roots
     * @return Array of n-roots of this {@link ComplexNumber}
     */
    public ComplexNumber[] root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0!");
        }

        ComplexNumber[] nRoots = new ComplexNumber[n];
        double rootAngle = this.getAngle() / n;
        double rootMagnitude = Math.pow(this.getMagnitude(), 1.0 / n);

        for (int i = 0; i < n; i++) {
            nRoots[i] = fromMagnitudeAndAngle(rootMagnitude, rootAngle);
            rootAngle += 2 * Math.PI / n;
        }

        return nRoots;
    }

    @Override
    public String toString() {
        if (imaginaryPart == 0) {
            return Double.toString(realPart);
        } else if (realPart == 0) {
            if (imaginaryPart == -1) {
                return "-i";
            } else if (imaginaryPart == 1) {
                return "i";
            }

            return imaginaryPart + "i";
        } else if (imaginaryPart < 0) {
            return realPart + "" + imaginaryPart + "i";
        } else {
            return realPart + "+" + imaginaryPart + "i";
        }
    }
}
