package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents declared instances of {@link IComparisonOperator}.
 *
 * @author Filip Vucic
 */
public class ComparisonOperators {

    /**
     * Checks if the first value is less than the second.
     */
    public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0;

    /**
     * Checks if the first value is less or equals to the second.
     */
    public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;

    /**
     * Checks if the first value is greater than the second.
     */
    public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0;

    /**
     * Checks if the first value is greater or equals to the second.
     */
    public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;

    /**
     * Checks if the first value equals to the second.
     */
    public static final IComparisonOperator EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;

    /**
     * Checks if the first value doesn't equal to the second.
     */
    public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;

    /**
     * Check if the first value is LIKE second. You can use wildcard '*'.
     */
    public static final IComparisonOperator LIKE = (value1, value2) -> {
        if (value2.indexOf("*") != value2.lastIndexOf("*")) {
            throw new IllegalArgumentException("You are not allowed to use more than 1 wildcard character!");
        }

        return value2.contains("*") ? value1.matches(value2.replace("*", ".*")) : value1.equals(value2);
    };
}
