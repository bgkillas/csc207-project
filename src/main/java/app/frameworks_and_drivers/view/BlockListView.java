package app.frameworks_and_drivers.view;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.view.components.NavButton;

import javax.swing.*;
import java.awt.*;

public class BlockListView extends JPanel {
    User user;
    UserSession session;
    JFrame frame;

    public BlockListView(User user, UserSession session, JFrame frame) {
        this.user = user;
        this.session = session;
        this.frame = frame;
    }

    public JComponent create() {
        this.setLayout(new BorderLayout());

        JButton back = new NavButton("back");
        back.addActionListener(
                e -> {
                    ProfileView profileView = new ProfileView(user, frame, session);
                    frame.setContentPane(profileView);
                    frame.revalidate();
                    frame.repaint();
                });

        JLabel title = new JLabel("Block List", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(back, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel blockList = new JPanel();
        blockList.setLayout(new BoxLayout(blockList, BoxLayout.Y_AXIS));
        blockList.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        blockList.setBackground(Color.WHITE);
        for (User user : user.getBlockList()) {
            JButton block = createActionButton(user.getName());
            block.setAlignmentX(Component.CENTER_ALIGNMENT);
            block.setFont(new Font("Arial", Font.PLAIN, 16));
            blockList.add(block);
            block.addActionListener(
                    e -> {
                        ProfileView profileView = new ProfileView(user, frame, session);
                        frame.setContentPane(profileView);
                        frame.revalidate();
                        frame.repaint();
                    });
        }
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(Color.WHITE);
        centerWrapper.add(blockList);
        this.add(centerWrapper, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(800, 600));
        return this;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(0xAF4C50)); // Red color
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }
}
