package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

import java.util.Objects;

/**
 * @author Filip Vucic
 * Class which represents document node and extends {@link Node}.
 */
public class DocumentNode extends Node {

    /**
     * {@link DocumentNode} children.
     */
    private ArrayIndexedCollection children;

    @Override
    public void addChildNode(Node child) {
        Objects.requireNonNull(child, "Child can not be null!");

        if (children == null) {
            children = new ArrayIndexedCollection();
        }

        children.add(child);
    }

    @Override
    public int numberOfChildren() {
        if (children == null) {
            return 0;
        }

        return children.size();
    }

    @Override
    public Node getChild(int index) {
        return (Node) children.get(index);
    }

    @Override
    public String toString() {
        StringBuilder documentString = new StringBuilder();
        for (int i = 0; i < children.size(); i++) {
            Node child = (Node) children.get(i);
            documentString.append(child.toString());
        }

        return documentString.toString();
    }
}
