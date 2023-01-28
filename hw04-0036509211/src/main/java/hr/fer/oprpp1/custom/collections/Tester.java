package hr.fer.oprpp1.custom.collections;

/**
 * Interface which represents a Tester with its single method test.
 *
 * @author Filip Vucic
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
