package hr.fer.oprpp1.hw04.db;

/**
 * Strategy with a single method which filters instances of {@link StudentRecord}.
 *
 * @author Filip Vucic
 */
public interface IFilter {

    /**
     * Check if filter accepts the given {@link StudentRecord}.
     *
     * @param record Given {@link StudentRecord}
     * @return True if accepts, false otherwise
     */
    boolean accepts(StudentRecord record);
}
