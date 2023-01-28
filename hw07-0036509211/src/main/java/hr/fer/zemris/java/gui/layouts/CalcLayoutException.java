package hr.fer.zemris.java.gui.layouts;

/**
 * {@link CalcLayout} exception which extends {@link RuntimeException}.
 *
 * @author Filip Vucic
 */
public class CalcLayoutException extends RuntimeException {

    /**
     * Create new {@link CalcLayoutException}.
     */
    public CalcLayoutException() {
    }

    /**
     * Create new {@link CalcLayoutException} with given message
     *
     * @param message Message
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
