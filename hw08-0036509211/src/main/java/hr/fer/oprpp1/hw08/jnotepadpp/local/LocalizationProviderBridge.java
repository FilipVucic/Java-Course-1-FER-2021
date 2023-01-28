package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Localization Provider Bridge which extends {@link AbstractLocalizationProvider}.
 * @author Filip Vucic
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * {@link ILocalizationProvider}.
     */
    private final ILocalizationProvider provider;

    /**
     * Bridge connected flag.
     */
    private boolean connected;

    /**
     * Language
     */
    private String language;

    /**
     * Listener.
     */
    private final ILocalizationListener listener = new ILocalizationListener() {
        @Override
        public void localizationChanged() {
            language = provider.getCurrentLanguage();
            fire();
        }
    };

    /**
     * Create new {@link LocalizationProviderBridge} with given {@link ILocalizationProvider}.
     * @param provider {@link ILocalizationProvider}
     */
    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.provider = provider;
        this.language = provider.getCurrentLanguage();
    }

    /**
     * Connect bridge.
     */
    public void connect() {
        if (!connected) {
            if (!getCurrentLanguage().equals(language)) {
                fire();
            }
            provider.addLocalizationListener(listener);
            connected = true;
        }
    }

    /**
     * Disconnect bridge.
     */
    public void disconnect() {
        provider.removeLocalizationListener(listener);
        connected = false;
    }

    @Override
    public String getString(String key) {
        return provider.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return provider.getCurrentLanguage();
    }

}
