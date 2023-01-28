package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.*;
import java.io.Serial;

/**
 * Localizable {@link Action}.
 * @author Filip Vucic
 */
public abstract class LocalizableAction extends AbstractAction {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Create new {@link LocalizableAction} with given key and {@link ILocalizationProvider}.
     * @param key Key
     * @param lp {@link ILocalizationProvider}
     */
    public LocalizableAction(String key, ILocalizationProvider lp) {
        changeLang(lp, key);
        lp.addLocalizationListener(() -> changeLang(lp, key));
    }

    /**
     * Change language.
     * @param lp {@link ILocalizationProvider}
     * @param key Key
     */
    private void changeLang(ILocalizationProvider lp, String key) {
        putValue(NAME, lp.getString(key));
        putValue(SHORT_DESCRIPTION, lp.getString(key + "desc"));
    }
}
