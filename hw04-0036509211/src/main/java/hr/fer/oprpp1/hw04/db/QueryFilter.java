package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class which represents a query filter.
 *
 * @author Filip Vucic
 */
public class QueryFilter implements IFilter {

    /**
     * Query to be filtered
     */
    private final List<ConditionalExpression> query;

    /**
     * Create new {@link QueryFilter} with given query.
     *
     * @param query Given query
     */
    public QueryFilter(List<ConditionalExpression> query) {
        this.query = query;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression : query) {
            if (!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(record),
                    expression.getStringLiteral())) {
                return false;
            }
        }
        return true;
    }
}
