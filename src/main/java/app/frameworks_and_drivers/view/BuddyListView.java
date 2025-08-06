package app.frameworks_and_drivers.view;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.frameworks_and_drivers.view.components.NavButton;
import java.awt.*;
import javax.swing.*;

/**
 * A Swing view that displays the user's list of friends ("buddies").
 * Each friend is represented as a button that navigates to their profile view when clicked.
 * The view also includes a back button that returns to the current user's profile.
 */
public class BuddyListView extends JPanel {
    User user;
    UserSession session;
    JFrame frame;
    PostDataAccessInterface postDataAccessObject;

    /**
     * Constructs a new BuddyListView for the given user and session context.
     *
     * @param user                 the current user whose buddy list is being displayed
     * @param session              the active user session
     * @param frame                the main application window
     * @param postDataAccessObject the data access object for post-related operations
     */
    public BuddyListView(User user, UserSession session, JFrame frame,
                         PostDataAccessInterface postDataAccessObject) {
        this.user = user;
        this.session = session;
        this.frame = frame;
        this.postDataAccessObject = postDataAccessObject;
    }

    /**
     * Creates and returns the full buddy list UI as a JComponent.
     * Each buddy is shown as a button, and clicking a button transitions to that buddy's profile
     * view.
     *
     * @return the constructed buddy list panel
     */
    public JComponent create() {
        this.setLayout(new BorderLayout());

        JButton back = new NavButton("back");
        back.addActionListener(
                e -> {
                    ProfileView profileView = new ProfileView(user, frame, session,
                            postDataAccessObject);
                    frame.setContentPane(profileView);
                    frame.revalidate();
                    frame.repaint();
                });

        JLabel title = new JLabel("Buddy List", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(back, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel buddyList = new JPanel();
        buddyList.setLayout(new BoxLayout(buddyList, BoxLayout.Y_AXIS));
        buddyList.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        buddyList.setBackground(Color.WHITE);
        for (User user : user.getFriendList()) {
            JButton buddy = createActionButton(user.getName());
            buddy.setAlignmentX(Component.CENTER_ALIGNMENT);
            buddy.setFont(new Font("Arial", Font.PLAIN, 16));
            buddyList.add(buddy);
            buddy.addActionListener(
                    e -> {
                        ProfileView profileView = new ProfileView(user, frame, session,
                                postDataAccessObject);
                        frame.setContentPane(profileView);
                        frame.revalidate();
                        frame.repaint();
                    });
        }
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(Color.WHITE);
        centerWrapper.add(buddyList);
        this.add(centerWrapper, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(800, 600));
        return this;
    }

    /**
     * Creates a styled button for a buddy's name.
     *
     * @param text the name to display on the button
     * @return a customized {@link JButton} representing the buddy
     */
    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(0x4CAF50)); // Green color
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }
}
