package hr.fer.oprpp1.custom.collections;

/**
 * Class Processor here represents an conceptual contract between clients
 * which will have objects to be processed, and each concrete Processor
 * which knows how to perform the selected operation.
 *
 * @author Filip Vucic
 */
public interface Processor<T> {

    /**
     * Process an object.
     *
     * @param value Object to be processed
     */
    void process(T value);
}
