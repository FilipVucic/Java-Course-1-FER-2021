package hr.fer.oprpp1.custom.collections;

/**
 * @author Filip Vucic
 * Class which represents linked list indexed collection of elements and extends {@link Collection}.
 */
public class LinkedListIndexedCollection extends Collection {

    /**
     * List node.
     */
    private static class ListNode {

        /**
         * Previous list node.
         */
        ListNode previous;

        /**
         * Next list node.
         */
        ListNode next;

        /**
         * List node value.
         */
        Object value;
    }

    /**
     * Current size of the collection.
     */
    private int size;

    /**
     * First {@link ListNode} in the collection.
     */
    private ListNode first;

    /**
     * Last {@link ListNode} in the collection.
     */
    private ListNode last;

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
    public LinkedListIndexedCollection(Collection other) {
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
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException("Element should not be null!");
        }

        if (first == null) {
            first = new ListNode();
            first.value = value;
            last = new ListNode();
            last.value = value;
        } else {
            if (this.size == 1) {
                last.value = value;
                first.next = last;
                last.previous = first;
            } else {
                last.next = new ListNode();
                last.next.value = value;
                last.next.previous = last;
                last = last.next;
            }
        }

        this.size++;
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
        ListNode iterationNode = first;

        for (int i = 0; i < this.size; i++) {
            returnArray[i] = iterationNode.value;
            iterationNode = iterationNode.next;
        }

        return returnArray;
    }

    @Override
    public void forEach(Processor processor) {
        ListNode iterationNode = first;

        for (int i = 0; i < this.size; i++) {
            processor.process(iterationNode.value);
            iterationNode = iterationNode.next;
        }
    }

    @Override
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * Get collection's element at the given index.
     * Has complexity less than n/2+1.
     *
     * @param index Element index
     * @return Object that is stored in the linked list at position index
     */
    public Object get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index out of collection's bounds!");
        }

        ListNode node = findNode(index);

        return node.value;
    }

    /**
     * Insert element in the collection at the given position.
     * Average complexity O(n/2+1).
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

        if (position == this.size) {
            add(value);
            return;
        }

        ListNode lastNode = findNode(position);

        ListNode newNode = new ListNode();
        newNode.previous = lastNode.previous;
        newNode.next = lastNode;
        if (newNode.previous != null) {
            newNode.previous.next = newNode;
        }
        lastNode.previous = newNode;
        newNode.value = value;

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
        ListNode iterationNode = first;

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

        ListNode nodeToBeRemoved = findNode(index);

        nodeToBeRemoved.previous.next = nodeToBeRemoved.next;
        nodeToBeRemoved.next.previous = nodeToBeRemoved.previous;

        this.size--;
    }

    /**
     * Private method used for finding the node on given position.
     *
     * @param position Given position
     * @return Found {@link ListNode}
     */
    private ListNode findNode(int position) {
        ListNode finderNode;

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
}
