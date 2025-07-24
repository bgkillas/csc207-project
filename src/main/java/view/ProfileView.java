package view;

import entities.User;
import entities.UserSession;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import app.teamStory.MatchServiceImpl;

public class ProfileView extends JPanel {
    private final User user;
    private final JFrame frame;
    private final UserSession userSession;

    /**
     * Constructs a ProfileView for the given user.
     *
     * @param user the user whose profile is to be displayed
     * @param frame the JFrame to which this view will be added
     * @param userSession the user session containing all users and matches
     */
    public ProfileView(User user, JFrame frame, UserSession userSession) {
        this.user = user;
        this.frame = frame;
        this.userSession = userSession;
        create();
    }

    /** Initializes the profile view components. */
    public void create() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Top panel with profile info
        JPanel profilePanel = createProfilePanel();
        add(profilePanel, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createProfilePanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(Color.WHITE);

        // Top title bar
        JPanel topBar = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        topBar.add(title, BorderLayout.CENTER);
        mainPanel.add(topBar);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        // Profile picture placeholder
        JLabel profilePic = new JLabel("?", SwingConstants.CENTER);
        profilePic.setFont(new Font("Arial", Font.PLAIN, 64));
        profilePic.setPreferredSize(new Dimension(120, 120));
        profilePic.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User info display
        JLabel nameLabel =
                new JLabel("<html><h2>" + user.getName() + "</h2></html>", SwingConstants.CENTER);
        JLabel detailsLabel =
                new JLabel(
                        "<html>"
                                + user.getAge()
                                + " • "
                                + user.getGender()
                                + "<br>"
                                + user.getLocation()
                                + "<br><br>"
                                + "\""
                                + user.getBio()
                                + "\"</html>",
                        SwingConstants.CENTER);

        // Music taste display
        JLabel genresLabel =
                new JLabel(
                        "<html><b>Favorite Genres:</b><br>"
                                + String.join(", ", user.getFavGenres())
                                + "</html>",
                        SwingConstants.CENTER);
        JLabel artistsLabel =
                new JLabel(
                        "<html><b>Favorite Artists:</b><br>"
                                + String.join(", ", user.getFavArtists())
                                + "</html>",
                        SwingConstants.CENTER);
        JLabel songsLabel =
                new JLabel(
                        "<html><b>Favorite Songs:</b><br>"
                                + String.join(", ", user.getFavSongs())
                                + "</html>",
                        SwingConstants.CENTER);

        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        genresLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        artistsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        songsLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        genresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        artistsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        songsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(profilePic);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(nameLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(detailsLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(genresLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(artistsLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(songsLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(contentPanel);

        return mainPanel;
    }

    private JPanel createButtonPanel() {
        // Create two panels: one for action buttons and one for navigation
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 40));

        // Action buttons panel (edit profile, buddy list, block list)
        JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton editProfileBtn = createActionButton("edit profile");
        JButton buddyListBtn = createActionButton("buddy list");
        JButton blockListBtn = createActionButton("block list");

        actionPanel.add(editProfileBtn);
        actionPanel.add(buddyListBtn);
        actionPanel.add(blockListBtn);

        // Bottom navigation bar (matching, share, your profile)
        JPanel navPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton matchingBtn = createNavButton("matching");
        JButton shareBtn = createNavButton("share");
        JButton yourProfileBtn = createNavButton("your profile");

        yourProfileBtn.setBackground(Color.DARK_GRAY);
        yourProfileBtn.setForeground(Color.WHITE);

        // Add the matching button action
        matchingBtn.addActionListener(
                e -> {
                    MatchServiceImpl matchService = new MatchServiceImpl();
                    List<User> matches = matchService.findMatches(user, userSession.getAllUsers());

                    JPanel matchingRoomPanel =
                            new MatchingRoomView(frame, user, matches, userSession);
                    frame.setContentPane(matchingRoomPanel);
                    frame.revalidate();
                    frame.repaint();
                });

        buddyListBtn.addActionListener(
                e -> {
                    BuddyListView buddyList = new BuddyListView(user, userSession, frame);
                    frame.setContentPane(buddyList.create());
                    frame.revalidate();
                    frame.repaint();
                });

        navPanel.add(matchingBtn);
        navPanel.add(shareBtn);
        navPanel.add(yourProfileBtn);

        // Add both panels to the combined panel
        combinedPanel.add(actionPanel, BorderLayout.CENTER);
        combinedPanel.add(navPanel, BorderLayout.SOUTH);

        return combinedPanel;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(0x4CAF50)); // Green color
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.DARK_GRAY);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }
}
