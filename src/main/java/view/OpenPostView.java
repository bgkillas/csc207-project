package view;

import app.individual_story.CreatePostInteractor;
import entities.User;
import entities.UserSession;
import interface_adapter.controller.OpenPostController;
import interface_adapter.controller.PostFeedController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OpenPostView extends JPanel {
    private final User currentUser;
    private final UserSession session;
    private final JFrame frame;

    public OpenPostView(User user, UserSession session, JFrame frame) {
        this.currentUser = user;
        this.session = session;
        this.frame = frame;
    }

    public JPanel create(OpenPostController controller) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(500, 600));

        // Create a JPanel for title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Open Post");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        JButton back = new JButton("← Back");
        back.addActionListener(
                e -> {
                    frame.setContentPane(
                            new PostFeedView(currentUser, session, frame)
                                    .create(new PostFeedController(new CreatePostInteractor())));
                    frame.revalidate();
                    frame.repaint();
                });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(back, BorderLayout.WEST);
        topPanel.add(titlePanel, BorderLayout.CENTER);

        // Create a JPanel for main content
        JPanel mainPanel = new JPanel();

        // Navigation bar
        JPanel navPanel = new JPanel(new GridLayout(1, 3));
        JButton btnMatching = new JButton("Matching");
        JButton btnShare = new JButton("Share");
        JButton btnProfile = new JButton("My Profile");
        navPanel.add(btnMatching);
        navPanel.add(btnShare);
        navPanel.add(btnProfile);

        // Create a JPanel for Bottom NavBar
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(navPanel, BorderLayout.SOUTH);

        // add all components to panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // navigate to matching room
        btnMatching.addActionListener(
                e -> {
                    List<User> matchedUsers = session.getAllUsers();
                    JPanel matchingPanel =
                            new MatchingRoomView(frame, currentUser, matchedUsers, session);
                    frame.setContentPane(matchingPanel);
                    frame.revalidate();
                    frame.repaint();
                });

        // navigate to profile
        btnProfile.addActionListener(
                e -> {
                    JPanel profilePanel = new ProfileView(session.getUser(), frame, session);
                    frame.setContentPane(profilePanel);
                    frame.revalidate();
                    frame.repaint();
                });

        return panel;
    }
}
