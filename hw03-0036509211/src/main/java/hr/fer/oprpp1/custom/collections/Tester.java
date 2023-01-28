package hr.fer.oprpp1.custom.collections;

/**
 * @author Filip Vucic
 * Interface which represents a Tester with its single method test.
 */
public interface Tester<T> {

    /**
     * Check if an object passes the test.
     *
     * @param obj Object to be tested
     * @return True if passes, false otherwise
     */
    boolean test(T obj);
}
