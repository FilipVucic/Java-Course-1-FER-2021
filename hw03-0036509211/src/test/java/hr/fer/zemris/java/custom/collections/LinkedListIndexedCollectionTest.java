// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.zemris.java.custom.collections;

import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.Processor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListIndexedCollectionTest {

    @Test
    public void testConstructors() {
        LinkedListIndexedCollection collection1 = new LinkedListIndexedCollection();
        LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection(collection1);

        assertEquals(0, collection1.size());
        assertEquals(0, collection2.size());
    }

    @Test
    public void testConstructorExceptions() {
        assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
    }

    @Test
    public void add() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        Object[] array = collection.toArray();
        assertEquals("Ivan", array[0]);
        assertEquals(702, array[1]);
        assertEquals("Mate", array[2]);
    }

    @Test
    public void clear() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.clear();

        assertEquals(0, collection.size());
    }

    @Test
    public void get() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertEquals("Ivan", collection.get(0));
        assertEquals(702, collection.get(1));
        assertEquals("Mate", collection.get(2));
    }

    @Test
    public void insert() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.insert("Pero", 0);
        collection.insert("Vlado", 2);
        collection.insert("Miro", 5);

        assertEquals("Pero", collection.get(0));
        assertEquals("Ivan", collection.get(1));
        assertEquals("Vlado", collection.get(2));
        assertEquals(702, collection.get(3));
        assertEquals("Mate", collection.get(4));
        assertEquals("Miro", collection.get(5));
    }

    @Test
    public void insertExceptions() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertThrows(NullPointerException.class, () -> collection.insert(null, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("Pero", 4));
    }

    @Test
    public void indexOf() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertEquals(2, collection.indexOf("Mate"));
    }

    @Test
    public void remove() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.remove(1);

        assertEquals("Ivan", collection.get(0));
        assertEquals("Mate", collection.get(1));

        collection.remove(0);

        assertEquals("Mate", collection.get(0));
    }

    @Test
    public void remove2() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.remove(Integer.valueOf(702));

        assertEquals("Ivan", collection.get(0));
        assertEquals("Mate", collection.get(1));
    }

    @Test
    public void size() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.remove(Integer.valueOf(702));

        assertEquals("Ivan", collection.get(0));
        assertEquals("Mate", collection.get(1));
    }

    @Test
    public void contains() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertFalse(collection.contains("Jure"));
        assertTrue(collection.contains("Mate"));
    }

    @Test
    public void toArray() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertEquals("Ivan", collection.toArray()[0]);
        assertEquals(3, collection.toArray().length);
    }

    @Test
    public void forEach() {
        LinkedListIndexedCollection<String> collection = new LinkedListIndexedCollection<>();

        collection.add("Ivan");
        collection.add("Mara");
        collection.add("Mate");

        LinkedListIndexedCollection<String> collection2 = new LinkedListIndexedCollection<>();

        class P implements Processor<String> {
            public void process(String o) {
                collection2.add(o);
            }
        }

        collection.forEach(new P());

        for (int i = 0; i < collection.size(); i++) {
            assertEquals(collection.get(i), collection2.get(i));
        }
    }

    @Test
    public void addAll() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection();

        collection2.addAll(collection);

        for (int i = 0; i < collection.size(); i++) {
            assertEquals(collection.get(i), collection2.get(i));
        }
    }

    @Test
    public void isEmpty() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection();

        assertFalse(collection.isEmpty());
        assertTrue(collection2.isEmpty());
    }
}