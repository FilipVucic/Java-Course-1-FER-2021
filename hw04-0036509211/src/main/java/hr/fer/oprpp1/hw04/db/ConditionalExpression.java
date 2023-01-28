package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents one conditional expression of the query.
 *
 * @author Filip Vucic
 */
public class ConditionalExpression {

    /**
     * Query field value getter.
     */
    private final IFieldValueGetter fieldValueGetter;

    /**
     * Query string literal.
     */
    private final String stringLiteral;

    /**
     * Query comparison operator.
     */
    private final IComparisonOperator comparisonOperator;

    /**
     * Create new {@link ConditionalExpression} with given query field value getter, string literal
     * and comparison operator.
     *
     * @param fieldValueGetter   Field value getter
     * @param stringLiteral      String literal
     * @param comparisonOperator Comparison operator
     */
    public ConditionalExpression(IFieldValueGetter fieldValueGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
        this.fieldValueGetter = fieldValueGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Get field value getter.
     *
     * @return Field value getter
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldValueGetter;
    }

    /**
     * Get comparison operator.
     *
     * @return Comparison operator
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    /**
     * Get string literal.
     *
     * @return String literal
     */
    public String getStringLiteral() {
        return stringLiteral;
    }
}
