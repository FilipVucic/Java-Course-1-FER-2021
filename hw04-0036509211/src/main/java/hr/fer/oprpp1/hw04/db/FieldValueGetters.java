package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents instances of {@link IFieldValueGetter}.
 *
 * @author Filip Vucic
 */
public class FieldValueGetters {

    /**
     * Get first name value.
     */
    public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;

    /**
     * Get last name value.
     */
    public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;

    /**
     * Get JMBAG value.
     */
    public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
}
