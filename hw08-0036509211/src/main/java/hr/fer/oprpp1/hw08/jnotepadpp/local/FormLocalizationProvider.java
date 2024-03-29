package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Form Localization Provider which extends {@link LocalizationProviderBridge}.
 * @author Filip Vucic
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Create new {@link FormLocalizationProvider} with given {@link ILocalizationProvider} and {@link JFrame}.
     * @param provider Provider
     * @param frame Frame
     */
    public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
        super(provider);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });
    }
}
