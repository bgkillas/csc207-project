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
        initialize();
    }

    /**
     * Initializes the profile view components.
     */
    private void initialize() {
        setLayout(new BorderLayout());

        // Top panel with profile info
        JPanel profilePanel = createProfilePanel();
        add(profilePanel, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Profile picture placeholder
        JLabel profilePic = new JLabel("?");
        profilePic.setPreferredSize(new Dimension(50, 50));
        profilePic.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // User info
        JLabel nameLabel = new JLabel("Name: " + user.getName());
        JLabel ageLabel = new JLabel("Age: " + user.getAge());
        JLabel genderLabel = new JLabel("Gender: " + user.getGender());
        JLabel locationLabel = new JLabel("Location: " + user.getLocation());
        JLabel bioLabel = new JLabel("Bio: " + user.getBio());

        panel.add(profilePic);
        panel.add(nameLabel);
        panel.add(ageLabel);
        panel.add(genderLabel);
        panel.add(locationLabel);
        panel.add(bioLabel);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton editProfileBtn = createButton("edit profile");
        JButton buddyListBtn = createButton("buddy list");
        JButton blockListBtn = createButton("block list");
        JButton matchingBtn = createButton("matching");
        JButton shareBtn = createButton("share");
        JButton yourProfileBtn = createButton("your profile");

        // Style "your profile" button differently
        yourProfileBtn.setBackground(Color.DARK_GRAY);
        yourProfileBtn.setForeground(Color.WHITE);

        // Add the matching button action
        matchingBtn.addActionListener(e -> {
            // Create matches using MatchService
            MatchServiceImpl matchService = new MatchServiceImpl();
            List<User> matches = matchService.findMatches(user, userSession.getAllUsers());

            // Create and show the matching room view, passing the userSession
            JPanel matchingRoomPanel = new MatchingRoomView(frame, user, matches, userSession);
            frame.setContentPane(matchingRoomPanel);
            frame.revalidate();
            frame.repaint();
        });

        // Add buttons to panel
        panel.add(editProfileBtn);
        panel.add(buddyListBtn);
        panel.add(blockListBtn);
        panel.add(matchingBtn);
        panel.add(shareBtn);
        panel.add(yourProfileBtn);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.LIGHT_GRAY);
        return button;
    }
}
