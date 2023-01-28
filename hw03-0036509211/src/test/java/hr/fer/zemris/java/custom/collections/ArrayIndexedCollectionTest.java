// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.zemris.java.custom.collections;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Processor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIndexedCollectionTest {

    @Test
    public void testConstructors() {
        ArrayIndexedCollection collection1 = new ArrayIndexedCollection();

        ArrayIndexedCollection collection2 = new ArrayIndexedCollection(20);

        ArrayIndexedCollection collection3 = new ArrayIndexedCollection(collection2);

        ArrayIndexedCollection collection4 = new ArrayIndexedCollection(collection2, 10);

        assertEquals(0, collection1.size());
        assertEquals(0, collection2.size());
        assertEquals(0, collection3.size());
        assertEquals(0, collection4.size());
    }

    @Test
    public void testConstructorExceptions() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-4));
    }

    @Test
    public void add() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

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
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.clear();

        assertEquals(0, collection.size());
    }

    @Test
    public void get() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertEquals("Ivan", collection.get(0));
        assertEquals(702, collection.get(1));
        assertEquals("Mate", collection.get(2));
    }

    @Test
    public void insert() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.insert("Pero", 0);

        assertEquals("Pero", collection.get(0));
        assertEquals("Ivan", collection.get(1));
        assertEquals(702, collection.get(2));
        assertEquals("Mate", collection.get(3));

    }

    @Test
    public void insertExceptions() {
        ArrayIndexedCollection<String> collection = new ArrayIndexedCollection<>(3);

        collection.add("Ivan");
        collection.add("Mara");
        collection.add("Mate");

        assertThrows(NullPointerException.class, () -> collection.insert(null, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("Pero", 4));
    }

    @Test
    public void indexOf() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertEquals(2, collection.indexOf("Mate"));
    }

    @Test
    public void remove1() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.remove(1);

        assertEquals("Ivan", collection.get(0));
        assertEquals("Mate", collection.get(1));
    }

    @Test
    public void remove2() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.remove(Integer.valueOf(702));

        assertEquals("Ivan", collection.get(0));
        assertEquals("Mate", collection.get(1));
    }

    @Test
    public void size() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        collection.remove(Integer.valueOf(702));

        assertEquals("Ivan", collection.get(0));
        assertEquals("Mate", collection.get(1));
    }

    @Test
    public void contains() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertFalse(collection.contains("Jure"));
        assertTrue(collection.contains("Mate"));
    }

    @Test
    public void toArray() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        assertEquals("Ivan", collection.toArray()[0]);
        assertEquals(3, collection.toArray().length);
    }

    @Test
    public void forEach() {
        ArrayIndexedCollection<String> collection = new ArrayIndexedCollection<>();

        collection.add("Ivan");
        collection.add("Mara");
        collection.add("Mate");

        ArrayIndexedCollection<String> collection2 = new ArrayIndexedCollection<>();

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
        ArrayIndexedCollection<String> collection = new ArrayIndexedCollection<>();

        collection.add("Ivan");
        collection.add("Mara");
        collection.add("Mate");

        ArrayIndexedCollection<String> collection2 = new ArrayIndexedCollection<>();

        collection2.addAll(collection);

        for (int i = 0; i < collection.size(); i++) {
            assertEquals(collection.get(i), collection2.get(i));
        }
    }

    @Test
    public void isEmpty() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("Ivan");
        collection.add(702);
        collection.add("Mate");

        ArrayIndexedCollection collection2 = new ArrayIndexedCollection();

        assertFalse(collection.isEmpty());
        assertTrue(collection2.isEmpty());
    }

    @Test
    public void addAllSatisfying() {
        ArrayIndexedCollection<String> collection = new ArrayIndexedCollection<>();

        collection.add("Ivan");
        collection.add("Mara");
        collection.add("Mate");

        ArrayIndexedCollection<String> collection2 = new ArrayIndexedCollection<>();

        collection2.addAllSatisfying(collection, obj -> obj.startsWith("M"));

        assertEquals("Mara", collection2.get(0));
        assertEquals("Mate", collection2.get(1));
    }
}