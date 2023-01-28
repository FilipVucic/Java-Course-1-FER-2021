package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Context which consists of multiple instances of {@link TurtleState} stored in an
 * {@link ObjectStack}.
 *
 * @author Filip Vucic
 */
public class Context {

    /**
     * Storage of {@link TurtleState} instances.
     */
    private final ObjectStack<TurtleState> turtleStates;

    /**
     * Create new {@link Context}.
     */
    public Context() {
        turtleStates = new ObjectStack<>();
    }

    /**
     * Get current {@link TurtleState}.
     *
     * @return Current {@link TurtleState}
     */
    public TurtleState getCurrentState() {
        return turtleStates.peek();
    }

    /**
     * Push new {@link TurtleState} to the stack.
     *
     * @param state New {@link TurtleState}
     */
    public void pushState(TurtleState state) {
        turtleStates.push(state);
    }

    /**
     * Pop current {@link TurtleState} from the stack.
     */
    public void popState() {
        turtleStates.pop();
    }
}
