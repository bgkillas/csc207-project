package app.frameworks_and_drivers.view.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/** A custom navigation button with hover and active state styling. */
public class NavButton extends JButton {
    private final Color defaultBg = new Color(245, 245, 245); // light gray
    private final Color hoverBg = new Color(230, 230, 230); // hover color
    private final Color activeBg = new Color(200, 200, 200); // selected tab
    private boolean isActive = false;

    /**
     * Creates a navigation button with custom styling and hover effects.
     *
     * @param text Button label
     */
    public NavButton(String text) {
        super(text);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setBackground(defaultBg);
        setForeground(Color.DARK_GRAY);
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(
                new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        if (!isActive) {
                            setBackground(hoverBg);
                        }
                    }

                    public void mouseExited(MouseEvent e) {
                        if (!isActive) {
                            setBackground(defaultBg);
                        }
                    }
                });
    }

    /**
     * Sets this button as active or inactive. Disables the button and applies active styling if
     * true.
     *
     * @param active Whether the button should appear active
     */
    public void setActive(boolean active) {
        isActive = active;
        setEnabled(!active);
        setBackground(active ? activeBg : defaultBg);
        // setFont(getFont().deriveFont(active ? Font.BOLD : Font.PLAIN));
    }
}
