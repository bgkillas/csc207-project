package app.frameworks_and_drivers.view.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import javax.swing.*;

/**
 * A panel for displaying a single comment.
 * This panel shows the comment's author, text content, and date.
 * It also responds to mouse events like clicks and hover for interactivity.
 */
public class CommentViewPanel extends JPanel {
    /**
     * Constructs a new {CommentViewPanel} displaying the given comment information.
     *
     * @param author  the name or username of the comment's author
     * @param text    the text content of the comment
     * @param date    the timestamp when the comment was posted
     * @param onClick the action to perform when the panel is clicked
     */
    public CommentViewPanel(String author, String text, LocalDateTime date, Runnable onClick) {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(450, 15));
        setBackground(Color.WHITE);

        JLabel authorLabel = new JLabel(author);
        JLabel textLabel = new JLabel(text);

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String formatted = sdf.format(date.toString());
        JLabel dateLabel = new JLabel(date.toString());

        textLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        textLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(authorLabel, BorderLayout.WEST);
        add(textLabel, BorderLayout.CENTER);
        add(dateLabel, BorderLayout.EAST);

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
