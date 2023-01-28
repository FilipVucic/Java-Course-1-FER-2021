package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Strategy of modifying strings.
 * @param <String>
 */
public interface Modifier<String> {

    /**
     * Modify given string.
     * @param text String
     * @return Modified string
     */
    String modify(String text);
}
