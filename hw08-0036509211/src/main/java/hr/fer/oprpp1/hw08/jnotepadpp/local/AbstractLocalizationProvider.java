package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract localization provider which implements {@link ILocalizationProvider}.
 * @author Filip Vucic
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    /**
     * Listeners.
     */
    private List<ILocalizationListener> listeners;

    /**
     * Initialize {@link AbstractLocalizationProvider}.
     */
    public AbstractLocalizationProvider() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all listeners that the localization has changed.
     */
    public void fire() {
        for (ILocalizationListener listener : listeners) {
            listener.localizationChanged();
        }
    }
}
