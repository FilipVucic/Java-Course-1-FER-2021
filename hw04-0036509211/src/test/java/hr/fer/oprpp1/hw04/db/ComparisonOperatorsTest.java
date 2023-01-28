// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonOperatorsTest {

    @Test
    void comparisonOperatorsTest() {
        assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Jasna"));
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "Jasna"));
        assertTrue(ComparisonOperators.GREATER.satisfied("Jasna", "Ana"));
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Jasna", "Ana"));
        assertTrue(ComparisonOperators.EQUALS.satisfied("Jasna", "Jasna"));
        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Jasna", "Ana"));

        assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
        assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
        assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));

    }

}