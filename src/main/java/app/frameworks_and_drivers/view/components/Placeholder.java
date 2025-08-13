package app.frameworks_and_drivers.view.components;

import javax.swing.text.JTextComponent;

public class Placeholder {
    /**
     * Set up placeholder for some JTextComponent.
     *
     * @param textComponent - the type of textComponent given.
     * @param placeholder - the content to be in place.
     */
    public static void setup(JTextComponent textComponent, String placeholder) {
        textComponent.setText(placeholder);
        textComponent.addFocusListener(
                new java.awt.event.FocusAdapter() {
                    boolean cleared = false;

                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (!cleared) {
                            textComponent.setText("");
                            cleared = true;
                        }
                    }
                });
    }
}
