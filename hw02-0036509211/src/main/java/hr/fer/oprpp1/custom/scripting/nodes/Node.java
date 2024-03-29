package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Abstract class which represents one {@link hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser} node.
 */
public abstract class Node {

    /**
     * Add child to node.
     *
     * @param child Node child to be added
     */
    public abstract void addChildNode(Node child);

    /**
     * Get number of node children.
     *
     * @return Number of node children
     */
    public abstract int numberOfChildren();

    /**
     * Get the node child at given index.
     *
     * @param index Given index of node child
     * @return Node child
     */
    public abstract Node getChild(int index);
}
