package view;

import app.createPost.CreatePostInteractor;
import entities.User;
import entities.UserSession;
import interface_adapter.controller.PostFeedViewController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MatchingRoomView extends JPanel {

    private int currentIndex = 0;

    public MatchingRoomView(
            JFrame frame, User currentUser, List<User> matches, UserSession session) {

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        this.setBackground(Color.WHITE);

        // Top title bar
        JPanel topBar = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Matching Room", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        topBar.add(title, BorderLayout.CENTER);

        // mail icon
        JLabel notification = new JLabel("\u2709", SwingConstants.LEFT);
        notification.setFont(new Font("Arial", Font.PLAIN, 24));
        notification.setForeground(Color.DARK_GRAY);
        topBar.add(notification, BorderLayout.WEST);

        // ConnectRequestView
        notification.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        frame.setContentPane(new ConnectRequestView(frame, currentUser, session));
                        frame.revalidate();
                        frame.repaint();
                    }
                });

        this.add(topBar, BorderLayout.NORTH);

        // User card panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        cardPanel.setBackground(Color.WHITE);

        JLabel profilePic = new JLabel("?", SwingConstants.CENTER);
        profilePic.setFont(new Font("Arial", Font.PLAIN, 64));
        profilePic.setPreferredSize(new Dimension(120, 120));

        JLabel info = new JLabel("", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel score = new JLabel("", SwingConstants.CENTER);
        score.setFont(new Font("Arial", Font.BOLD, 28));
        score.setForeground(new Color(0x2E8B57));

        JPanel innerCard = new JPanel(new GridLayout(1, 2));
        innerCard.add(profilePic);

        JPanel right = new JPanel(new BorderLayout());
        right.add(info, BorderLayout.CENTER);
        right.add(score, BorderLayout.EAST);
        innerCard.add(right);

        cardPanel.add(innerCard, BorderLayout.CENTER);
        this.add(cardPanel, BorderLayout.CENTER);

        // connect / skip buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionPanel.setOpaque(false); // transparent background

        JButton connectBtn = createStyledButton("connect", new Color(0x4CAF50));
        JButton skipBtn = createStyledButton("skip", new Color(0xF44336));

        actionPanel.add(Box.createHorizontalGlue());
        actionPanel.add(connectBtn);
        actionPanel.add(Box.createHorizontalStrut(20)); // spacing between buttons
        actionPanel.add(skipBtn);
        actionPanel.add(Box.createHorizontalGlue());

        // nav bar
        JPanel navPanel = new JPanel(new GridLayout(1, 3));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton matchingBtn = new JButton("matching");
        JButton shareBtn = new JButton("share");
        JButton yourProfileBtn = new JButton("your profile");
        navPanel.add(matchingBtn);
        navPanel.add(shareBtn);
        navPanel.add(yourProfileBtn);

        // Bottom control panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        actionPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        navPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        bottomPanel.add(actionPanel);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(navPanel);

        this.add(bottomPanel, BorderLayout.SOUTH); // ✅ ONLY ONE add to BorderLayout.SOUTH

        // display logic
        Runnable updateDisplay =
                () -> {
                    if (currentIndex >= matches.size()) {
                        info.setText("No more matches.");
                        score.setText("");
                        connectBtn.setEnabled(false);
                        skipBtn.setEnabled(false);
                        return;
                    }
                    User match = matches.get(currentIndex);
                    info.setText(
                            "<html><b>"
                                    + match.getName()
                                    + "</b><br/>"
                                    + match.getAge()
                                    + "<br/>"
                                    + match.getLocation()
                                    + "<br/>"
                                    + "\""
                                    + match.getBio()
                                    + "\"</html>");
                    score.setText("97%");
                };

        connectBtn.addActionListener(
                e -> {
                    currentUser.getFriendList().add(matches.get(currentIndex));
                    currentIndex++;
                    updateDisplay.run();
                });

        skipBtn.addActionListener(
                e -> {
                    currentIndex++;
                    updateDisplay.run();
                });

        yourProfileBtn.addActionListener(
                e -> {
                    frame.setContentPane(new ProfileView(currentUser, frame, session));
                    frame.revalidate();
                    frame.repaint();
                });

        shareBtn.addActionListener(
                e -> {
                    PostFeedViewController controller =
                            new PostFeedViewController(new CreatePostInteractor());
                    JPanel postFeedPanel =
                            PostFeedView.create(controller, frame, currentUser, session);
                    frame.setContentPane(postFeedPanel);
                    frame.revalidate();
                    frame.repaint();
                });

        updateDisplay.run();
    }

    private static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        button.setMaximumSize(new Dimension(120, 40));
        return button;
    }

    public static void showInFrame(User currentUser, List<User> matches) {
        JFrame frame = new JFrame("JRMC Matching Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        // build a dummy session to avoid null
        UserSession dummySession = new UserSession();
        dummySession.setUser(currentUser);

        MatchingRoomView view = new MatchingRoomView(frame, currentUser, matches, dummySession);
        frame.setContentPane(view);
        frame.setVisible(true);
    }
}
