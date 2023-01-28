package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Newton program.
 *
 * @author Filip Vucic
 */
public class Newton {

    /**
     * Main program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" +
                "Please enter at least two roots, one root per line. Enter 'done' when done.");
        List<Complex> polynomialRoots = new ArrayList<>();
        int i = 1;
        while (true) {
            System.out.print("Root " + i++ + "> ");
            String complexNumber = sc.nextLine();
            if (complexNumber.equals("done")) {
                break;
            }
            Complex complex = Util.parseComplex(complexNumber);
            polynomialRoots.add(complex);
        }

        ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(Complex.ONE, polynomialRoots.toArray(Complex[]::new));

        FractalViewer.show(new MojProducer(polynomial));
    }

    /**
     * Implementation of {@link IFractalProducer}.
     *
     * @author Filip Vucic
     */
    public static class MojProducer implements IFractalProducer {

        private static final double convergenceThreshold = 0.001;
        private static final double rootThreshold = 0.002;
        private static final int maxIter = 16 * 16 * 16;
        private final ComplexRootedPolynomial polynomial;
        private final ComplexPolynomial derived;

        /**
         * Create new {@link MojProducer}.
         * @param polynomial Polynomial
         */
        public MojProducer(ComplexRootedPolynomial polynomial) {
            this.polynomial = polynomial;
            this.derived = polynomial.toComplexPolynom().derive();
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            System.out.println("Zapocinjem izracun...");
            short[] data = new short[width * height];
            Util.calculate(height, cancel, width, reMax, reMin, imMax, imMin, polynomial, derived, convergenceThreshold, maxIter, rootThreshold, data, 0, height - 1);
            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
        }
    }
}
