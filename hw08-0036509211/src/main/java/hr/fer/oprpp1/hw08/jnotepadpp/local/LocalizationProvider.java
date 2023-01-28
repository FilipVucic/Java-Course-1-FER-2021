package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization provider which extends {@link AbstractLocalizationProvider}.
 * @author Filip Vucic
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * Instance of {@link LocalizationProvider}.
     */
    private static final LocalizationProvider instance = new LocalizationProvider();

    /**
     * Language.
     */
    private String language;

    /**
     * {@link ResourceBundle} bundle.
     */
    private ResourceBundle bundle;

    /**
     * Create new {@link LocalizationProvider}.
     */
    private LocalizationProvider() {
        language = "en";
        setBundle();
    }

    /**
     * Get instance of {@link LocalizationProvider}.
     * @return {@link LocalizationProvider}
     */
    public static LocalizationProvider getInstance() {
        return instance;
    }

    /**
     * Set language of {@link LocalizationProvider}.
     * @param language Language
     */
    public void setLanguage(String language) {
        this.language = language;
        setBundle();
        fire();
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }

    /**
     * Set bundle.
     */
    private void setBundle() {
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
    }
}
