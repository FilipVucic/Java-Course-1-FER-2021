package hr.fer.oprpp1.custom.collections;

/**
 * Interface which represents some general collection of objects.
 *
 * @author Filip Vucic
 */
public interface Collection<T> {

    /**
     * Checks if collection is empty.
     *
     * @return True if collection is empty, false otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the size of the collection.
     *
     * @return Size of the collection
     */
    int size();

    /**
     * Add value to the collection.
     *
     * @param value Value to be added
     */
    void add(T value);

    /**
     * Check if the collection contains given value.
     *
     * @param value Value to be checked
     * @return True if the collection contains given value, false otherwise
     */
    boolean contains(Object value);

    /**
     * Remove one occurrence of value from the collection.
     *
     * @param value Value to be removed
     * @return True if the collection contains given value, false otherwise
     */
    boolean remove(Object value);

    /**
     * Return array with collection content.
     *
     * @return Array with collection content
     */
    Object[] toArray();

    /**
     * Call {@link Processor}.process for each element of this collection.
     *
     * @param processor Collection processor
     */
    default void forEach(Processor<? super T> processor) {
        ElementsGetter<? extends T> getter = this.createElementsGetter();

        while (getter.hasNextElement()) {
            processor.process(getter.getNextElement());
        }
    }

    /**
     * Add into current collection all elements from other collection.
     * Other collection remains unchanged.
     *
     * @param other Collection whose elements will be added
     */
    default void addAll(Collection<? extends T> other) {
        other.forEach(this::add);
    }

    /**
     * Remove all elements from the collection.
     */
    void clear();

    /**
     * Create new {@link ElementsGetter} of this collection.
     *
     * @return New {@link ElementsGetter}
     */
    ElementsGetter<T> createElementsGetter();

    /**
     * Add all satisfying elements from other collection to this collection. Elements are
     * satisfying if they pass the test from the given tester.
     *
     * @param col    Collection with elements to be added
     * @param tester Tester which will test the elements
     */
    default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
        ElementsGetter<? extends T> getter = col.createElementsGetter();

        while (getter.hasNextElement()) {
            T nextElement = getter.getNextElement();
            if (tester.test(nextElement)) {
                this.add(nextElement);
            }
        }
    }
}
