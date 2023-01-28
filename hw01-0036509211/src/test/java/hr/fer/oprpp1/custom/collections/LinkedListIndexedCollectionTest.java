package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListIndexedCollectionTest {

    @Test
    void add() {
        LinkedListIndexedCollection LinkedListIndexedCollection = new LinkedListIndexedCollection();
        LinkedListIndexedCollection.add("Auto");
        LinkedListIndexedCollection.add("Bicikl");
        assertThrows(NullPointerException.class, () -> LinkedListIndexedCollection.add(null));

        assertEquals("Auto", LinkedListIndexedCollection.get(0));
        assertEquals("Bicikl", LinkedListIndexedCollection.get(1));

    }

    @Test
    void get() {
        LinkedListIndexedCollection LinkedListIndexedCollection = new LinkedListIndexedCollection();
        LinkedListIndexedCollection.add("Auto");
        LinkedListIndexedCollection.add("Bicikl");

        assertEquals("Auto", LinkedListIndexedCollection.get(0));
        assertEquals("Bicikl", LinkedListIndexedCollection.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> LinkedListIndexedCollection.get(2));
    }

    @Test
    void contains() {
        LinkedListIndexedCollection LinkedListIndexedCollection = new LinkedListIndexedCollection();
        LinkedListIndexedCollection.add("Auto");
        LinkedListIndexedCollection.add("Bicikl");

        assertTrue(LinkedListIndexedCollection.contains("Auto"));
        assertFalse(LinkedListIndexedCollection.contains("Motor"));
    }

    @Test
    void clearAndSize() {
        LinkedListIndexedCollection LinkedListIndexedCollection = new LinkedListIndexedCollection();
        LinkedListIndexedCollection.add("Auto");
        LinkedListIndexedCollection.add("Bicikl");

        LinkedListIndexedCollection.clear();
        assertEquals(0, LinkedListIndexedCollection.size());
    }

    @Test
    void insertAndConstructors() {
        LinkedListIndexedCollection LinkedListIndexedCollection = new LinkedListIndexedCollection();
        LinkedListIndexedCollection.add("Auto");
        LinkedListIndexedCollection.add("Bicikl");

        assertEquals("Auto", LinkedListIndexedCollection.get(0));
        assertEquals("Bicikl", LinkedListIndexedCollection.get(1));

        LinkedListIndexedCollection LinkedListIndexedCollection2 = new LinkedListIndexedCollection(LinkedListIndexedCollection);
        LinkedListIndexedCollection2.insert("Motor", 1);
        assertEquals("Auto", LinkedListIndexedCollection2.get(0));
        assertEquals("Motor", LinkedListIndexedCollection2.get(1));
        assertEquals("Bicikl", LinkedListIndexedCollection2.get(2));

        LinkedListIndexedCollection2.insert("Romobil", 3);
        assertEquals("Romobil", LinkedListIndexedCollection2.get(3));

        assertThrows(NullPointerException.class, () -> LinkedListIndexedCollection2.insert(null, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> LinkedListIndexedCollection2.insert("Tricikl", 5));
    }

    @Test
    void indexOf() {
        LinkedListIndexedCollection LinkedListIndexedCollection = new LinkedListIndexedCollection();
        LinkedListIndexedCollection.add("Auto");
        LinkedListIndexedCollection.add("Bicikl");

        assertEquals(0, LinkedListIndexedCollection.indexOf("Auto"));
        assertEquals(1, LinkedListIndexedCollection.indexOf("Bicikl"));
    }

    @Test
    void remove() {
        LinkedListIndexedCollection LinkedListIndexedCollection = new LinkedListIndexedCollection();
        LinkedListIndexedCollection.add("Auto");
        LinkedListIndexedCollection.add("Bicikl");
        LinkedListIndexedCollection.add("Motor");
        LinkedListIndexedCollection.remove("Bicikl");

        assertEquals("Auto", LinkedListIndexedCollection.get(0));
        assertEquals("Motor", LinkedListIndexedCollection.get(1));
    }
}