package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class which represents linked list indexed collection of elements and implements {@link List}.
 *
 * @author Filip Vucic
 */
public class LinkedListIndexedCollection<T> implements List<T> {

    /**
     * Modification count of the collection.
     */
    private long modificationCount = 0;
    /**
     * Current size of the collection.
     */
    private int size;
    /**
     * First {@link ListNode} in the collection.
     */
    private ListNode<T> first;
    /**
     * Last {@link ListNode} in the collection.
     */
    private ListNode<T> last;

    /**
     * Creates new {@link LinkedListIndexedCollection}.
     */
    public LinkedListIndexedCollection() {
        this.first = null;
        this.last = null;
    }

    /**
     * Creates new {@link LinkedListIndexedCollection} and copies elements from given collection inside.
     *
     * @param other Collection to be copied
     */
    public LinkedListIndexedCollection(Collection<? extends T> other) {
        if (other == null) {
            throw new NullPointerException("Collection should not be null!");
        }
        this.addAll(other);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void add(T value) {
        if (value == null) {
            throw new NullPointerException("Element should not be null!");
        }

        if (first == null) {
            first = new ListNode<>();
            first.value = value;
            last = new ListNode<>();
            last.value = value;
        } else {
            if (this.size == 1) {
                last.value = value;
                first.next = last;
                last.previous = first;
            } else {
                last.next = new ListNode<>();
                last.next.value = value;
                last.next.previous = last;
                last = last.next;
            }
        }

        this.size++;
        modificationCount++;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    @Override
    public boolean remove(Object value) {
        if (contains(value)) {
            remove(indexOf(value));
            return true;
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] returnArray = new Object[this.size];
        ListNode<T> iterationNode = first;

        for (int i = 0; i < this.size; i++) {
            returnArray[i] = iterationNode.value;
            iterationNode = iterationNode.next;
        }

        return returnArray;
    }

    @Override
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
        modificationCount++;
    }

    @Override
    public ElementsGetter<T> createElementsGetter() {
        return new LinkedListIndexedCollectionElementsGetter<>(this);
    }

    /**
     * Get collection's element at the given index.
     * Has complexity less than n/2+1.
     *
     * @param index Element index
     * @return Object that is stored in the linked list at position index
     */
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index out of collection's bounds!");
        }

        ListNode<T> node = findNode(index);

        return node.value;
    }

    /**
     * Insert element in the collection at the given position.
     * Average complexity O(n/2+1).
     *
     * @param value    Value to be inserted
     * @param position Position of value in the collection
     */
    public void insert(T value, int position) {
        if (value == null) {
            throw new NullPointerException("Value should not be null!");
        }

        if (position < 0 || position > this.size) {
            throw new IndexOutOfBoundsException("Index out of collection's bounds!");
        }

        if (position == this.size) {
            add(value);
            return;
        }

        ListNode<T> lastNode = findNode(position);

        ListNode<T> newNode = new ListNode<>();
        newNode.previous = lastNode.previous;
        newNode.next = lastNode;
        if (newNode.previous != null) {
            newNode.previous.next = newNode;
        } else {
            first = newNode;
        }
        lastNode.previous = newNode;
        newNode.value = value;

        this.size++;
        modificationCount++;
    }

    /**
     * Get given element's index in the collection.
     * Average complexity O(n).
     *
     * @param value Value whose index is searched for
     * @return Index of the given element, -1 if given element is not found in the collection
     */
    public int indexOf(Object value) {
        ListNode<T> iterationNode = first;

        for (int i = 0; i < this.size; i++) {
            if (iterationNode.value.equals(value)) {
                return i;
            }
            iterationNode = iterationNode.next;
        }

        return -1;
    }

    /**
     * Remove element from the collection at the given index.
     *
     * @param index Index of the element to be removed
     */
    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index out of collection's bounds!");
        }

        ListNode<T> nodeToBeRemoved = findNode(index);

        if (nodeToBeRemoved.previous == null) {
            first = first.next;
        } else {
            nodeToBeRemoved.previous.next = nodeToBeRemoved.next;
        }

        if (nodeToBeRemoved.next == null) {
            last = last.previous;
        } else {
            nodeToBeRemoved.next.previous = nodeToBeRemoved.previous;
        }

        this.size--;
        modificationCount++;
    }

    /**
     * Private method used for finding the node on given position.
     *
     * @param position Given position
     * @return Found {@link ListNode}
     */
    private ListNode<T> findNode(int position) {
        ListNode<T> finderNode;

        if (position < this.size / 2) {
            finderNode = first;
            int i = 0;

            while (i != position) {
                finderNode = finderNode.next;
                i++;
            }
        } else {
            finderNode = last;
            int i = this.size - 1;

            while (i != position) {
                finderNode = finderNode.previous;
                i--;
            }
        }

        return finderNode;
    }

    /**
     * Class which represents {@link ElementsGetter} of the {@link LinkedListIndexedCollection}.
     *
     * @author Filip Vucic
     */
    private static class LinkedListIndexedCollectionElementsGetter<T> implements ElementsGetter<T> {

        /**
         * Reference to this {@link LinkedListIndexedCollection}.
         */
        LinkedListIndexedCollection<T> reference;
        /**
         * Saved modification count of the {@link LinkedListIndexedCollection} reference.
         */
        private long savedModificationCount = 0;
        /**
         * Current node.
         */
        private ListNode<T> currentNode;

        /**
         * Create new {@link LinkedListIndexedCollectionElementsGetter} with given {@link LinkedListIndexedCollection} reference.
         *
         * @param reference Reference to this {@link ArrayIndexedCollection}
         */
        public LinkedListIndexedCollectionElementsGetter(LinkedListIndexedCollection<T> reference) {
            currentNode = reference.first;
            this.reference = reference;
            savedModificationCount = reference.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            if (reference.modificationCount != savedModificationCount) {
                throw new ConcurrentModificationException("Collection has been modified!");
            }

            return currentNode != null;
        }

        @Override
        public T getNextElement() {
            if (hasNextElement()) {
                T value = currentNode.value;
                currentNode = currentNode.next;
                return value;
            } else {
                throw new NoSuchElementException("No more elements in the collection!");
            }
        }
    }

    /**
     * List node.
     */
    private static class ListNode<T> {

        /**
         * Previous list node.
         */
        ListNode<T> previous;

        /**
         * Next list node.
         */
        ListNode<T> next;

        /**
         * List node value.
         */
        T value;
    }
}
