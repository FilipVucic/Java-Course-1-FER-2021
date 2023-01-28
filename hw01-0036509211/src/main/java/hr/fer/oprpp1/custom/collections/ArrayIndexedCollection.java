package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * @author Filip Vucic
 * Class which represents array indexed collection of elements and extends {@link Collection}.
 */
public class ArrayIndexedCollection extends Collection {

    /**
     * Current size of the collection.
     */
    private int size;

    /**
     * An array of object references.
     */
    private Object[] elements;

    /**
     * Capacity of the array if created with default constructor.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Creates new {@link ArrayIndexedCollection}.
     * Initializes the collection with default capacity of {@value DEFAULT_CAPACITY}.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates new {@link ArrayIndexedCollection} and initializes the collection with given capacity.
     *
     * @param initialCapacity Initial capacity of the collection
     * @throws IllegalArgumentException if given initial capacity is less than 1
     */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be at least 1!");
        }

        elements = new Object[initialCapacity];
    }

    /**
     * Creates new {@link ArrayIndexedCollection} and initializes the collection with default capacity
     * of {@value DEFAULT_CAPACITY} and copies all elements from given collection to this collection.
     *
     * @param other Collection to be copied
     */
    public ArrayIndexedCollection(Collection other) {
        this(other, DEFAULT_CAPACITY);
    }

    /**
     * Creates new {@link ArrayIndexedCollection} and  initializes the collection with given capacity
     * and copies all elements from given collection to this collection.
     * If the initial capacity is smaller than given collection's size, the main collection's initial capacity
     * will be size of the given collection.
     *
     * @param other           Collection to be copied
     * @param initialCapacity Initial capacity
     */
    public ArrayIndexedCollection(Collection other, int initialCapacity) {
        this(initialCapacity);

        if (other == null) {
            throw new NullPointerException("Given collection shouldn't be null!");
        }

        if (initialCapacity < other.size()) {
            reallocateArray(other.size());
        }

        this.addAll(other);
    }

    @Override
    public int size() {
        return this.size;
    }

    /**
     * Average complexity O(1).
     *
     * @param value Value to be added
     */
    @Override
    public void add(Object value) {
        insert(value, this.size);
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    @Override
    public boolean remove(Object value) {
        int indexOfValue = indexOf(value);
        if (indexOfValue == -1) {
            return false;
        }

        remove(indexOfValue);
        return true;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, this.size);
    }

    @Override
    public void forEach(Processor processor) {
        for (int i = 0; i < this.size; i++) {
            processor.process(elements[i]);
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.size; i++) {
            elements[i] = null;
        }

        this.size = 0;
    }

    /**
     * Get collection's element at the given index.
     * Average complexity O(1).
     *
     * @param index Element index
     * @return Object that is stored in backing array at position index
     */
    public Object get(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException("Index out of collection's bounds!");
        }

        return elements[index];
    }

    /**
     * Insert element in the collection at the given position.
     * Average complexity O(n).
     *
     * @param value    Value to be inserted
     * @param position Position of value in the collection
     */
    public void insert(Object value, int position) {
        if (value == null) {
            throw new NullPointerException("Value should not be null!");
        }

        if (position < 0 || position > this.size) {
            throw new IndexOutOfBoundsException("Index out of collection's bounds!");
        }

        if (this.size == elements.length) {
            reallocateArray(this.size * 2);
        }

        for (int i = this.size; i > position; i--) {
            elements[i] = elements[i - 1];
        }

        elements[position] = value;
        this.size++;
    }

    /**
     * Get given element's index in the collection.
     * Average complexity O(n).
     *
     * @param value Value whose index is searched for
     * @return Index of the given element, -1 if given element is not found in the collection
     */
    public int indexOf(Object value) {
        for (int i = 0; i < this.size; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Remove element from the collection at the given index.
     *
     * @param index Index of the element to be removed
     */
    public void remove(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException("Index out of collection's bounds!");
        }

        for (int i = index; i < this.size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[this.size - 1] = null;
        this.size--;
    }

    /**
     * Private method used for reallocating the elements array with bigger capacity.
     *
     * @param newCapacity New capacity of the elements array
     * @throws IllegalArgumentException if new capacity is smaller than the old one
     */
    private void reallocateArray(int newCapacity) {
        elements = Arrays.copyOf(elements, newCapacity);
    }

}
