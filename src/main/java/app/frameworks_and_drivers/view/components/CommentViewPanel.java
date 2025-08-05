package app.frameworks_and_drivers.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class CommentViewPanel extends JPanel {
    public CommentViewPanel(String author, String text, LocalDateTime date, Runnable onClick) {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(450, 15));
        setBackground(Color.WHITE);

        JLabel authorLabel = new JLabel(author);
        JLabel textLabel = new JLabel(text);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formatted = sdf.format(date.toString());
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
