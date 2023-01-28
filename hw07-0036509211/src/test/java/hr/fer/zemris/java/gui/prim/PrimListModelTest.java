package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimListModelTest {
    private PrimListModel model;

    @BeforeEach
    void prepareModel() {
        model = new PrimListModel();
    }

    @Test
    void testConstructor() {
        assertEquals(1, model.getSize());
        assertEquals(1, model.getElementAt(0));
    }

    @Test
    void testPrimes() {
        int[] primes = {
                2, 3, 5, 7, 11, 13, 17, 19,
                23, 29, 31, 37, 41, 43, 47, 53,
                59, 61, 67, 71, 73, 79, 83, 89,
                97, 101, 103, 107, 109, 113,
                127, 131, 137 };

        for (int prime : primes) {
            assertTrue(PrimListModel.isPrime(prime));
        }
    }

}