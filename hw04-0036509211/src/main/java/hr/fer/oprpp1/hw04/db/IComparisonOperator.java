package hr.fer.oprpp1.hw04.db;

/**
 * Strategy with a single method that checks if comparison between 2 string values is satisfied.
 *
 * @author Filip Vucic
 */
public interface IComparisonOperator {

    boolean satisfied(String value1, String value2);
}
