package hr.fer.oprpp1.custom.collections;

/**
 * @author Filip Vucic
 * Class which represents some general collection of objects.
 */
public class Collection {

    /**
     * Default constructor.
     */
    protected Collection() {

    }

    /**
     * Checks if collection is empty.
     *
     * @return True if collection is empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the size of the collection.
     *
     * @return Size of the collection
     */
    public int size() {
        return 0;
    }

    /**
     * Add value to the collection.
     *
     * @param value Value to be added
     */
    public void add(Object value) {

    }

    /**
     * Check if the collection contains given value.
     *
     * @param value Value to be checked
     * @return True if the collection contains given value, false otherwise
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Remove one occurrence of value from the collection.
     *
     * @param value Value to be removed
     * @return True if the collection contains given value, false otherwise
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Return array with collection content.
     *
     * @return Array with collection content
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Call {@link Processor}.process for each element of this collection.
     *
     * @param processor Collection processor
     */
    public void forEach(Processor processor) {

    }

    /**
     * Add into current collection all elements from other collection.
     * Other collection remains unchanged.
     *
     * @param other Collection whose elements will be added
     */
    public void addAll(Collection other) {
        class AddProcessor extends Processor {
            @Override
            public void process(Object value) {
                add(value);
            }
        }

        other.forEach(new AddProcessor());
    }

    /**
     * Remove all elements from the collection.
     */
    public void clear() {

    }

}
