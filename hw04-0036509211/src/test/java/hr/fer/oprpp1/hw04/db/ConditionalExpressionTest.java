// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConditionalExpressionTest {

    @Test
    void satisfied() {
        ConditionalExpression expression =
                new ConditionalExpression(FieldValueGetters.FIRST_NAME,
                        "Fi*p", ComparisonOperators.LIKE);

        StudentRecord record = new StudentRecord("0036509211",
                "Vucic", "Filip", 5);

        assertTrue(expression.getComparisonOperator().satisfied(
                expression.getFieldGetter().get(record),
                expression.getStringLiteral()
        ));

    }

}