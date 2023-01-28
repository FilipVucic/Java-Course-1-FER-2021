package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @param <K> Key
 * @param <V> Value
 * @author Filip Vucic
 * Class which represents a SimpleHashtable. Key-value pairs are stored inside.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Default capacity of the {@link SimpleHashtable}.
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * {@link SimpleHashtable} storage of {@link TableEntry} entries.
     */
    private TableEntry<K, V>[] table;
    /**
     * Size of the {@link SimpleHashtable}.
     */
    private int size;
    /**
     * {@link SimpleHashtable} modification count.
     */
    private int modificationCount;

    /**
     * Creates new {@link SimpleHashtable} with {@value DEFAULT_CAPACITY}.
     */
    public SimpleHashtable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates new {@link SimpleHashtable} with given capacity.
     *
     * @param capacity {@link SimpleHashtable} capacity
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1!");
        }

        int pow2Capacity = 1;
        while (capacity > pow2Capacity) {
            pow2Capacity *= 2;
        }

        table = (TableEntry<K, V>[]) new TableEntry[pow2Capacity];
    }

    /**
     * Put the key-value entry in the {@link SimpleHashtable}.
     * If given key is present in the {@link SimpleHashtable}, method overrides the old
     * value with the given one.
     *
     * @param key   Key
     * @param value Value
     * @return null if there was no key present in the {@link SimpleHashtable}, old value otherwise
     */
    public V put(K key, V value) {
        if (size >= 0.75 * table.length) {
            doubleTableSize();
        }
        modificationCount++;

        V returnValue = putInTable(key, value);
        if (returnValue == null) {
            size++;
        }

        return returnValue;
    }

    /**
     * Get value of the given key in the {@link SimpleHashtable}.
     *
     * @param key Key
     * @return null if there is no given key in the {@link SimpleHashtable}, value otherwise
     */
    public V get(Object key) {
        TableEntry<K, V> entry = findEntry(key);

        return entry == null ? null : entry.value;
    }

    /**
     * Get size of the {@link SimpleHashtable}.
     *
     * @return Size of the {@link SimpleHashtable}
     */
    public int size() {
        return size;
    }

    /**
     * Check if {@link SimpleHashtable} contains the given key.
     *
     * @param key Key to be checked
     * @return True if contains, false otherwise
     */
    public boolean containsKey(Object key) {
        TableEntry<K, V> entry = findEntry(key);
        return entry != null;
    }

    /**
     * Check if {@link SimpleHashtable} contains the given value.
     *
     * @param value Value to be checked
     * @return True if contains, false otherwise
     */
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                if (entry.value.equals(value)) {
                    return true;
                }

                entry = entry.next;
            }
        }

        return false;
    }

    /**
     * Remove entry from the {@link SimpleHashtable} which has the given key.
     *
     * @param key Key
     * @return null if there is no given key in the {@link SimpleHashtable}, removed value otherwise
     */
    public V remove(Object key) {
        TableEntry<K, V> entry = findEntry(key);
        if (entry == null) {
            return null;
        }

        int slot = calculateSlot(key);
        if (table[slot] == entry) {
            table[slot] = entry.next;
        } else {
            TableEntry<K, V> previousEntry = getPreviousEntry(entry);
            assert previousEntry != null;
            previousEntry.next = entry.next;
        }

        size--;
        modificationCount++;
        return entry.value;
    }

    /**
     * Checks if the {@link SimpleHashtable} is empty.
     *
     * @return True if {@link SimpleHashtable} is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();
        print.append("[");
        String comma = "";
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                print.append(comma);
                print.append(entry);
                comma = ", ";
                entry = entry.next;
            }
        }
        print.append("]");

        return print.toString();
    }

    /**
     * Return array representation of this {@link SimpleHashtable}.
     *
     * @return Array representation of this {@link SimpleHashtable}
     */
    @SuppressWarnings("unchecked")
    public TableEntry<K, V>[] toArray() {
        TableEntry<K, V>[] entryArray = (TableEntry<K, V>[]) new TableEntry[size];

        int i = 0;
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                entryArray[i++] = entry;

                entry = entry.next;
            }
        }

        return entryArray;
    }

    /**
     * Clear the {@link SimpleHashtable}.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        modificationCount++;
        size = 0;
        table = (TableEntry<K, V>[]) new TableEntry[table.length];
    }

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * Calculate slot of the entry in this {@link SimpleHashtable}
     * table with the given entry key.
     *
     * @param key Entry key
     * @return Slot in the table
     */
    private int calculateSlot(Object key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Find entry in the {@link SimpleHashtable} with the given entry key.
     * Returns null if not found.
     *
     * @param key Entry key
     * @return Entry if found, null otherwise
     */
    private TableEntry<K, V> findEntry(Object key) {
        if (key == null) {
            return null;
        }

        int slot = calculateSlot(key);
        TableEntry<K, V> finderEntry = table[slot];

        while (finderEntry != null) {
            if (finderEntry.key.equals(key)) {
                return finderEntry;
            }

            finderEntry = finderEntry.next;
        }

        return null;
    }

    /**
     * Get entry that is previous to given entry.
     *
     * @param entry Entry
     * @return Previous entry
     */
    private TableEntry<K, V> getPreviousEntry(TableEntry<K, V> entry) {
        int slot = calculateSlot(entry.key);
        TableEntry<K, V> finderEntry = table[slot];

        while (finderEntry.next != null) {
            if (finderEntry.next.key.equals(entry.key)) {
                return finderEntry;
            }

            finderEntry = finderEntry.next;
        }

        return null;
    }

    /**
     * Get last entry in the slot.
     *
     * @param slot Table slot
     * @return Last entry in the slot
     */
    private TableEntry<K, V> lastEntryInTheSlot(int slot) {
        TableEntry<K, V> finderEntry = table[slot];

        while (finderEntry != null) {
            if (finderEntry.next == null) {
                return finderEntry;
            }

            finderEntry = finderEntry.next;
        }

        return null;
    }

    /**
     * Double table size and copy all the entries.
     */
    @SuppressWarnings("unchecked")
    private void doubleTableSize() {
        modificationCount++;
        TableEntry<K, V>[] oldTable = this.toArray();

        table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];

        for (TableEntry<K, V> entry : oldTable) {
            putInTable(entry.key, entry.value);
        }
    }

    /**
     * Put the key-value entry in the {@link SimpleHashtable}.
     * If given key is present in the {@link SimpleHashtable}, method overrides the old
     * value with the given one.
     *
     * @param key   Key
     * @param value Value
     * @return null if there was no key present in the {@link SimpleHashtable}, old value otherwise
     */
    private V putInTable(K key, V value) {
        TableEntry<K, V> newEntry = new TableEntry<>(key, value, null);

        TableEntry<K, V> finderEntry = findEntry(key);

        int slot = calculateSlot(key);
        if (table[slot] == null) {
            table[slot] = newEntry;
        } else {
            if (finderEntry == null) {
                TableEntry<K, V> lastEntryInTheSlot = lastEntryInTheSlot(slot);
                assert lastEntryInTheSlot != null;
                lastEntryInTheSlot.next = newEntry;
            } else {
                V oldValue = finderEntry.value;
                finderEntry.value = value;
                return oldValue;
            }
        }

        return null;
    }

    /**
     * Private class which represents {@link SimpleHashtable} {@link Iterator}.
     */
    private class IteratorImpl implements Iterator<TableEntry<K, V>> {

        /**
         * Saved {@link SimpleHashtable} modification count when this
         * {@link IteratorImpl} was created.
         */
        int savedModificationCount = modificationCount;
        /**
         * Current {@link SimpleHashtable} slot.
         */
        int currentSlot;
        /**
         * Flag to check if current entry was already removed.
         */
        boolean removed = false;
        /**
         * Current index of the iteration.
         */
        private int currentIndex;
        /**
         * Current {@link TableEntry} of the iteration.
         */
        private TableEntry<K, V> currentEntry;

        @Override
        public boolean hasNext() {
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("SimpleHashtable was modified!");
            }

            return currentIndex < size;
        }

        @Override
        public TableEntry<K, V> next() {
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("SimpleHashtable was modified!");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("No more entries!");
            }

            removed = false;

            if (currentEntry == null) {
                currentEntry = getFirstEntry();
            } else {
                currentSlot = calculateSlot(currentEntry.key);
                currentEntry = currentEntry.next;

                if (currentEntry == null) {
                    for (int i = currentSlot + 1; i < table.length; i++) {
                        if (table[i] != null) {
                            currentEntry = table[i];
                        }
                    }
                }
            }

            currentIndex++;
            return currentEntry;
        }

        @Override
        public void remove() {
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("SimpleHashtable was modified!");
            }

            if (currentEntry == null) {
                throw new NullPointerException("Call next() first!");
            }
            if (removed) {
                throw new IllegalStateException("Entry already removed!");
            }

            savedModificationCount++;
            SimpleHashtable.this.remove(currentEntry.key);
            removed = true;
            currentIndex--;
        }

        /**
         * Get first entry of the {@link SimpleHashtable}.
         *
         * @return First entry of the {@link SimpleHashtable}
         */
        private TableEntry<K, V> getFirstEntry() {
            for (TableEntry<K, V> entry : table) {
                if (entry != null) {
                    return entry;
                }
            }

            return null;
        }
    }

    /**
     * Class which represents one key-value entry of {@link SimpleHashtable}.
     *
     * @param <K> Entry key
     * @param <V> Entry value
     */
    public static class TableEntry<K, V> {

        /**
         * Key of the {@link TableEntry}.
         */
        private final K key;

        /**
         * Value of the {@link TableEntry}.
         */
        private V value;

        /**
         * Next {@link TableEntry}.
         */
        private TableEntry<K, V> next;

        /**
         * Create new {@link TableEntry} with given key, value and next
         * {@link TableEntry}.
         *
         * @param key   Key of the {@link TableEntry}
         * @param value Value of the {@link TableEntry}
         * @param next  Next {@link TableEntry}
         */
        public TableEntry(K key, V value, TableEntry<K, V> next) {
            Objects.requireNonNull(key, "Key can not be null!");

            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Get key of the {@link TableEntry}.
         *
         * @return Key of the {@link TableEntry}
         */
        public K getKey() {
            return key;
        }

        /**
         * Get value of the {@link TableEntry}.
         *
         * @return Value of the {@link TableEntry}
         */
        public V getValue() {
            return value;
        }

        /**
         * Set value of the {@link TableEntry}.
         *
         * @param value Value to be set
         */
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

}
