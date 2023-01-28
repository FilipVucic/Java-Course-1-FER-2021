package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Localization provider interface.
 * @author Filip Vucic
 */
public interface ILocalizationProvider {

    /**
     * Add localization listener.
     * @param listener Listener
     */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * Remove localization listener.
     * @param listener Listener
     */
    void removeLocalizationListener(ILocalizationListener listener);

    /**
     * Get string of the key.
     * @param key Key
     * @return String
     */
    String getString(String key);

    /**
     * Get current language.
     * @return Current language
     */
    String getCurrentLanguage();
}
