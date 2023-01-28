package hr.fer.oprpp1.custom.collections;

import java.util.Objects;


/**
 * Class which represents a dictionary. Key-value pairs are stored inside.
 *
 * @param <K> Key
 * @param <V> Value
 */
public class Dictionary<K, V> {

    /**
     * Dictionary {@link Entry} storage.
     */
    private final ArrayIndexedCollection<Entry<K, V>> storage;

    /**
     * Create new {@link Dictionary}.
     */
    public Dictionary() {
        this.storage = new ArrayIndexedCollection<>();
    }

    /**
     * Check if the {@link Dictionary} is empty.
     *
     * @return True if empty, false otherwise
     */
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    /**
     * Return size of the {@link Dictionary}.
     *
     * @return Size of the {@link Dictionary}
     */
    public int size() {
        return storage.size();
    }

    /**
     * Clear this {@link Dictionary}.
     */
    public void clear() {
        storage.clear();
    }

    /**
     * Put the key-value entry in the {@link Dictionary}.
     * If given key is present in the {@link Dictionary}, method overrides the
     * old value with the given one.
     *
     * @param key   Key
     * @param value Value
     * @return null if there was no key present in the {@link Dictionary}, old value otherwise
     */
    public V put(K key, V value) {
        Entry<K, V> newEntry = new Entry<>(key, value);
        V overridenEntryValue = null;
        if (storage.contains(newEntry)) {
            int indexOfOldEntry = storage.indexOf(newEntry);
            overridenEntryValue = storage.get(indexOfOldEntry).value;
            storage.remove(indexOfOldEntry);
        }
        storage.add(newEntry);

        return overridenEntryValue;
    }

    /**
     * Get value of the given key in the {@link Dictionary}.
     *
     * @param key Key
     * @return null if there is no given key in the {@link Dictionary}, value otherwise
     */
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        Object[] entries = storage.toArray();

        for (Object entry : entries) {
            if (((Entry<K, V>) entry).key.equals(key)) {
                return ((Entry<K, V>) entry).value;
            }
        }

        return null;
    }

    /**
     * Remove entry from the {@link Dictionary} which has the given key.
     *
     * @param key Key
     * @return null if there is no given key in the {@link Dictionary}, removed value otherwise
     */
    public V remove(K key) {
        Entry<K, V> finderEntry = new Entry<>(key, null);
        V value = null;
        if (storage.contains(finderEntry)) {
            int indexOfEntry = storage.indexOf(finderEntry);
            value = storage.get(indexOfEntry).value;
            storage.remove(indexOfEntry);
        }

        return value;
    }

    /**
     * Class which represents one key-value entry of {@link Dictionary}.
     *
     * @param <K> Entry key
     * @param <V> Entry value
     */
    private static class Entry<K, V> {

        /**
         * Entry key.
         */
        K key;

        /**
         * Entry value.
         */
        V value;

        /**
         * Creates new {@link Entry} with given key and value.
         *
         * @param key   Key
         * @param value Value
         */
        public Entry(K key, V value) {
            Objects.requireNonNull(key, "Key can not be null!");

            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }

            Entry<K, V> other = (Entry<K, V>) obj;

            return this.key.equals(other.key);
        }
    }

}
