package hr.fer.oprpp1.hw04.db;

/**
 * Strategy with a single method which gets field value of the {@link StudentRecord}.
 *
 * @author Filip Vucic
 */
public interface IFieldValueGetter {

    /**
     * Get specific field value from the given {@link StudentRecord}.
     *
     * @param record Given {@link StudentRecord}
     * @return Field value
     */
    String get(StudentRecord record);
}
