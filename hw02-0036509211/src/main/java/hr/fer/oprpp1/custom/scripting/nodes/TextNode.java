package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * @author Filip Vucic
 * Class which represents text node and extends {@link Node}.
 */
public class TextNode extends Node {

    /**
     * Text of the node.
     */
    private final String text;

    /**
     * Create new {@link TextNode} with given text.
     *
     * @param text Given text
     */
    public TextNode(String text) {
        this.text = text;
    }

    @Override
    public void addChildNode(Node child) {
        throw new UnsupportedOperationException("Echo node can not have children!");
    }

    @Override
    public int numberOfChildren() {
        return 0;
    }

    @Override
    public Node getChild(int index) {
        throw new UnsupportedOperationException("Echo node has no children!");
    }

    @Override
    public String toString() {
        return text;
    }
}
