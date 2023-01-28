package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIndexedCollectionTest {

    @Test
    void add() {
        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
        arrayIndexedCollection.add("Auto");
        arrayIndexedCollection.add("Bicikl");
        assertThrows(NullPointerException.class, () -> arrayIndexedCollection.add(null));

        assertEquals("Auto", arrayIndexedCollection.get(0));
        assertEquals("Bicikl", arrayIndexedCollection.get(1));

    }

    @Test
    void contains() {
        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
        arrayIndexedCollection.add("Auto");
        arrayIndexedCollection.add("Bicikl");

        assertTrue(arrayIndexedCollection.contains("Auto"));
        assertFalse(arrayIndexedCollection.contains("Motor"));
    }

    @Test
    void get() {
        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(30);
        arrayIndexedCollection.add("Auto");
        arrayIndexedCollection.add("Bicikl");

        assertEquals("Auto", arrayIndexedCollection.get(0));
        assertEquals("Bicikl", arrayIndexedCollection.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayIndexedCollection.get(2));
    }

    @Test
    void clearAndSize() {
        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(30);
        arrayIndexedCollection.add("Auto");
        arrayIndexedCollection.add("Bicikl");

        arrayIndexedCollection.clear();
        assertEquals(0, arrayIndexedCollection.size());
    }

    @Test
    void insertAndConstructors() {
        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(30);
        arrayIndexedCollection.add("Auto");
        arrayIndexedCollection.add("Bicikl");

        assertEquals("Auto", arrayIndexedCollection.get(0));
        assertEquals("Bicikl", arrayIndexedCollection.get(1));

        ArrayIndexedCollection arrayIndexedCollection2 = new ArrayIndexedCollection(arrayIndexedCollection, 40);
        arrayIndexedCollection2.insert("Motor", 1);
        assertEquals("Auto", arrayIndexedCollection2.get(0));
        assertEquals("Motor", arrayIndexedCollection2.get(1));
        assertEquals("Bicikl", arrayIndexedCollection2.get(2));

        arrayIndexedCollection2.insert("Romobil", 3);
        assertEquals("Romobil", arrayIndexedCollection2.get(3));

        assertThrows(NullPointerException.class, () -> arrayIndexedCollection2.insert(null, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayIndexedCollection2.insert("Tricikl", 5));

        ArrayIndexedCollection arrayIndexedCollection3 = new ArrayIndexedCollection(arrayIndexedCollection);
        arrayIndexedCollection3.insert("Motor", 1);
        assertEquals("Auto", arrayIndexedCollection3.get(0));
        assertEquals("Motor", arrayIndexedCollection3.get(1));
        assertEquals("Bicikl", arrayIndexedCollection3.get(2));

        arrayIndexedCollection3.insert("Romobil", 3);
        assertEquals("Romobil", arrayIndexedCollection3.get(3));

        assertThrows(NullPointerException.class, () -> arrayIndexedCollection3.insert(null, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayIndexedCollection3.insert("Tricikl", 5));
    }

    @Test
    void indexOf() {
        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(30);
        arrayIndexedCollection.add("Auto");
        arrayIndexedCollection.add("Bicikl");

        assertEquals(0, arrayIndexedCollection.indexOf("Auto"));
        assertEquals(1, arrayIndexedCollection.indexOf("Bicikl"));
    }

    @Test
    void remove() {
        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(30);
        arrayIndexedCollection.add("Auto");
        arrayIndexedCollection.add("Bicikl");
        arrayIndexedCollection.add("Motor");
        arrayIndexedCollection.remove("Bicikl");

        assertEquals("Auto", arrayIndexedCollection.get(0));
        assertEquals("Motor", arrayIndexedCollection.get(1));
    }
}