package app.frameworks_and_drivers.view.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/** A custom button with hover styling. */
public class DesignedButton extends JButton {
    private final Color defaultBg = new Color(238, 238, 238);
    private final Color hoverBg = Color.GRAY;
    private final Color defaultFg = Color.DARK_GRAY;
    private final Color hoverFg = Color.WHITE;

    /**
     * Creates a designed button with custom styling and hover effects.
     *
     * @param text Button label
     */
    public DesignedButton(String text) {
        super(text);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);

        setBackground(defaultBg);
        setForeground(defaultFg);

        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(
                new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        setBackground(hoverBg);
                        setForeground(hoverFg);
                    }

                    public void mouseExited(MouseEvent e) {
                        setBackground(defaultBg);
                        setForeground(defaultFg);
                    }
                });
    }
}
