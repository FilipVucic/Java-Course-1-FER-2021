package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Parallel newton program.
 *
 * @author Filip Vucic
 */
public class NewtonParallel {

    /**
     * Main program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int threads = availableProcessors;
        int tracks = 4 * availableProcessors;
        if (args.length != 0) {
            Map<String, Integer> argsMap = Util.parseArgs(args);
            if (argsMap.containsKey("workers")) {
                threads = argsMap.get("workers");
            }
            if (argsMap.containsKey("tracks")) {
                tracks = argsMap.get("tracks");
            }
            if (tracks < 1) {
                throw new IllegalArgumentException("Number of tracks must be at least 1!");
            }
        }

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

        FractalViewer.show(new MojProducer(polynomial, threads, tracks));
    }

    /**
     * Posao izracuna.
     *
     * @author Filip Vucic
     */
    public static class PosaoIzracuna implements Runnable {
        /**
         * No job.
         */
        public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

        double reMin;
        double reMax;
        double imMin;
        double imMax;
        int width;
        int height;
        int yMin;
        int yMax;
        int maxIter;
        short[] data;
        AtomicBoolean cancel;
        double rootThreshold;
        double convergenceThreshold;
        ComplexRootedPolynomial polynomial;
        ComplexPolynomial derived;

        /**
         * Create new {@link PosaoIzracuna}
         */
        private PosaoIzracuna() {
        }

        /**
         * Create new {@link PosaoIzracuna}.
         * @param reMin
         * @param reMax
         * @param imMin
         * @param imMax
         * @param width
         * @param height
         * @param yMin
         * @param yMax
         * @param maxIter
         * @param data
         * @param cancel
         * @param rootThreshold
         * @param convergenceThreshold
         * @param polynomial
         * @param derived
         */
        public PosaoIzracuna(double reMin, double reMax, double imMin,
                             double imMax, int width, int height, int yMin, int yMax,
                             int maxIter, short[] data, AtomicBoolean cancel, double rootThreshold,
                             double convergenceThreshold, ComplexRootedPolynomial polynomial, ComplexPolynomial derived) {
            super();
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.maxIter = maxIter;
            this.data = data;
            this.cancel = cancel;
            this.rootThreshold = rootThreshold;
            this.convergenceThreshold = convergenceThreshold;
            this.polynomial = polynomial;
            this.derived = derived;
        }

        @Override
        public void run() {
            Util.calculate(height, cancel, width, reMax, reMin, imMax, imMin, polynomial, derived, convergenceThreshold, maxIter, rootThreshold, data, yMin, yMax);
        }
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
        private final int threads;
        private int tracks;
        private final ComplexRootedPolynomial polynomial;
        private final ComplexPolynomial derived;

        /**
         * Create new {@link MojProducer}.
         * @param polynomial
         * @param threads
         * @param tracks
         */
        public MojProducer(ComplexRootedPolynomial polynomial, int threads, int tracks) {
            this.polynomial = polynomial;
            this.derived = polynomial.toComplexPolynom().derive();
            this.threads = threads;
            this.tracks = tracks;
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            System.out.println("Zapocinjem izracun...");
            short[] data = new short[width * height];
            if (tracks > height) {
                tracks = height;
            }
            int brojYPoTraci = height / tracks;

            final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

            AtomicInteger brojac = new AtomicInteger(threads);
            Thread[] radnici = new Thread[threads];
            for (int i = 0; i < radnici.length; i++) {
                radnici[i] = new Thread(() -> {
                    boolean doneSomething = false;
                    while (true) {
                        PosaoIzracuna p;
                        try {
                            p = queue.take();
                            if (p == PosaoIzracuna.NO_JOB) {
                                if (!doneSomething) {
                                    brojac.getAndDecrement();
                                }
                                break;
                            }
                            doneSomething = true;
                        } catch (InterruptedException e) {
                            continue;
                        }
                        p.run();
                    }
                });
            }
            for (Thread value : radnici) {
                value.start();
            }

            for (int i = 0; i < tracks; i++) {
                int yMin = i * brojYPoTraci;
                int yMax = (i + 1) * brojYPoTraci - 1;
                if (i == tracks - 1) {
                    yMax = height - 1;
                }
                PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax,
                        maxIter, data, cancel, rootThreshold, convergenceThreshold, polynomial, derived);
                while (true) {
                    try {
                        queue.put(posao);
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        queue.put(PosaoIzracuna.NO_JOB);
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Thread thread : radnici) {
                while (true) {
                    try {
                        thread.join();
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            System.out.println("Effective number of threads: " + brojac);
            System.out.println("Number of jobs: " + tracks);
            observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
        }
    }
}
