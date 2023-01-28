// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueryFilterTest {

    @Test
    void accepts() {
        QueryParser parser = new QueryParser("query lastName LIKE \"Vu*c\" and firstName > \"Ante\" ");
        StudentRecord record = new StudentRecord("0036509211", "Vucic",
                "Filip", 5);
        StudentRecord record2 = new StudentRecord("0036509211", "Vucic",
                "Ana", 5);

        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertTrue(filter.accepts(record));
        assertFalse(filter.accepts(record2));
    }
}