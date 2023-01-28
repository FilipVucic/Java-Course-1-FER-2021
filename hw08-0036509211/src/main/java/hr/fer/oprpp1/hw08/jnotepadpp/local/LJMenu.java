package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.*;
import java.io.Serial;

/**
 * Localizable {@link JMenu}.
 * @author Filip Vucic
 */
public class LJMenu extends JMenu {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Create new {@link LJMenu} with given key and {@link ILocalizationProvider}.
     * @param key Key
     * @param lp {@link ILocalizationProvider}
     */
    public LJMenu(String key, ILocalizationProvider lp) {
        setText(lp.getString(key));
        lp.addLocalizationListener(() -> setText(lp.getString(key)));
    }
}
