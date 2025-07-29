package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavButton extends JButton {
    private final Color DEFAULT_BG = new Color(245, 245, 245);  // light gray
    private final Color HOVER_BG = new Color(230, 230, 230);    // hover color
    private final Color ACTIVE_BG = new Color(200, 200, 200);   // selected tab
    private boolean isActive = false;

    public NavButton(String text) {
        super(text);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setBackground(DEFAULT_BG);
        setForeground(Color.DARK_GRAY);
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!isActive) setBackground(HOVER_BG);
            }

            public void mouseExited(MouseEvent e) {
                if (!isActive) setBackground(DEFAULT_BG);
            }
        });
    }

    public void setActive(boolean active) {
        isActive = active;
        setEnabled(!active);
        setBackground(active ? ACTIVE_BG : DEFAULT_BG);
//        setFont(getFont().deriveFont(active ? Font.BOLD : Font.PLAIN));
    }
}