package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

import java.util.Objects;

/**
 * @author Filip Vucic
 * Class which represents echo node and extends {@link Node}.
 */
public class EchoNode extends Node {

    /**
     * Elements of {@link EchoNode}.
     */
    private final Element[] elements;

    /**
     * Create new {@link EchoNode} with given elements.
     *
     * @param elements Elements
     */
    public EchoNode(Element[] elements) {
        Objects.requireNonNull(elements, "Elements can not be null!");

        this.elements = elements;
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
        StringBuilder echoString = new StringBuilder();

        echoString.append("{$ = ");

        for (Element element : elements) {
            echoString.append(element.asText());
            echoString.append(" ");
        }

        echoString.append("$}");

        return echoString.toString();
    }
}
