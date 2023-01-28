// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.zemris.java.custom.collections;

import hr.fer.oprpp1.custom.collections.Dictionary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    @Test
    void isEmpty() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        assertTrue(dictionary.isEmpty());

        dictionary.put("Mara", 20);
        dictionary.put("Lucija", 21);

        assertFalse(dictionary.isEmpty());
    }

    @Test
    void size() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        assertEquals(0, dictionary.size());

        dictionary.put("Mara", 20);
        dictionary.put("Lucija", 21);

        assertEquals(2, dictionary.size());

    }

    @Test
    void clear() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("Mara", 20);
        dictionary.put("Lucija", 21);

        assertFalse(dictionary.isEmpty());

        dictionary.clear();

        assertTrue(dictionary.isEmpty());
    }

    @Test
    void put() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("Mara", 20);
        dictionary.put("Lucija", 21);

        dictionary.put("Mara", 22);

        assertEquals(2, dictionary.size());
    }

    @Test
    void get() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("Mara", 20);
        dictionary.put("Lucija", 21);

        dictionary.put("Mara", 22);

        assertEquals(22, dictionary.get("Mara"));
        assertEquals(21, dictionary.get("Lucija"));
    }
}