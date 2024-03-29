package hr.fer.oprpp1.custom.collections;

/**
 * Class which represents an object stack.
 *
 * @author Filip Vucic
 */
public class ObjectStack<T> {

    /**
     * Stack elements storage.
     */
    private final ArrayIndexedCollection<T> stack;

    /**
     * Creates new {@link ObjectStack} and initializes its storage.
     */
    public ObjectStack() {
        stack = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if the stack is empty.
     *
     * @return True if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the size of the stack.
     *
     * @return Size of the stack
     */
    public int size() {
        return stack.size();
    }

    /**
     * Push value to the stack.
     *
     * @param value Value to be pushed
     */
    public void push(T value) {
        if (value == null) {
            throw new NullPointerException("Value should not be null!");
        }

        stack.add(value);
    }

    /**
     * Pop the last element from the stack.
     *
     * @return Last element from the stack
     */
    public T pop() {
        T returnObject = peek();
        stack.remove(size() - 1);
        return returnObject;
    }

    /**
     * Peek the last element from the stack.
     *
     * @return Last element from the stack
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException("The stack is empty!");
        }

        return stack.get(size() - 1);
    }

    /**
     * Clear all stack elements.
     */
    public void clear() {
        stack.clear();
    }
}
