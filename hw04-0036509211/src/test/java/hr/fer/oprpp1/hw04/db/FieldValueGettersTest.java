// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldValueGettersTest {

    StudentRecord record = new StudentRecord("0036509211", "Vucic",
            "Filip", 5);

    @Test
    void lastName() {
        assertEquals("Vucic", FieldValueGetters.LAST_NAME.get(record));
    }

    @Test
    void firstName() {
        assertEquals("Filip", FieldValueGetters.FIRST_NAME.get(record));
    }

    @Test
    void jmbag() {
        assertEquals("0036509211", FieldValueGetters.JMBAG.get(record));
    }
}