package app.frameworks_and_drivers.view.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * A UI component that displays a preview of a post in a clickable panel format.
 * When clicked, it runs a specified action (e.g., opening the full post).
 * On hover, the panel changes background color and cursor style to indicate interactivity.
 */
public class PostPreviewPanel extends JPanel {
    /**
     * Constructs a PostPreviewPanel with the given title and click behavior.
     *
     * @param title   the title text to display in the preview panel
     * @param onClick the action to perform when the panel is clicked
     */
    public PostPreviewPanel(String title, Runnable onClick) {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(450, 40));
        setBackground(Color.WHITE);
        // setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.CENTER);

        addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        onClick.run();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        setBackground(new Color(245, 245, 245));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setCursor(Cursor.getDefaultCursor());
                        setBackground(Color.WHITE);
                    }
                });
    }
}
