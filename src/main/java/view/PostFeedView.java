package view;

import entities.User;
import entities.UserSession;
import interface_adapter.controller.PostFeedViewController;
import view.components.CircularButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** View for the post feed panel. */
public class PostFeedView {
    public static JPanel create(
            PostFeedViewController controller,
            JFrame frame,
            User currentUser,
            UserSession session) {
        // MODIFIED: change to BorderLayout for full-frame layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(500, 600)); // MODIFIED: standard app size

        // Create a JPanel for title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Post Feed");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22)); // NEW: emphasized font
        titlePanel.add(titleLabel);
        panel.add(titlePanel, BorderLayout.NORTH); // MODIFIED: use NORTH in BorderLayout

        // Post Feed with Scroll
        JPanel postFeedPanel = new JPanel();
        postFeedPanel.setLayout(new BoxLayout(postFeedPanel, BoxLayout.Y_AXIS));
        postFeedPanel.setBackground(Color.LIGHT_GRAY);

        // add mock "post cards" into it
        for (int i = 1; i <= 6; i++) {
            JPanel postCard = getPost(i);
            postFeedPanel.add(Box.createVerticalStrut(10));
            postFeedPanel.add(postCard);
        }

        JScrollPane scrollPane = new JScrollPane(postFeedPanel); // MODIFIED: no bounds set
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER); // MODIFIED: use CENTER

        // "New" Post Button Row
        JPanel newPostWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // NEW
        CircularButton newPost = new CircularButton("New");
        newPost.setPreferredSize(new Dimension(60, 40)); // MODIFIED: more readable button
        newPost.setBackground(new Color(161, 220, 136));
        newPost.setForeground(Color.BLACK);
        newPost.setBorderPainted(false);
        newPostWrapper.add(newPost);

        // Navigation bar row
        JPanel navPanel = new JPanel(new GridLayout(1, 3));
        JButton btnMatching = new JButton("Matching");
        JButton btnShare = new JButton("Share");
        JButton btnProfile = new JButton("My Profile");
        navPanel.add(btnMatching);
        navPanel.add(btnShare);
        navPanel.add(btnProfile);

        // Bottom panel combining "New" and NavBar
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(newPostWrapper, BorderLayout.NORTH); // NEW
        bottomPanel.add(navPanel, BorderLayout.SOUTH);
        panel.add(bottomPanel, BorderLayout.SOUTH); // MODIFIED

        // Define what happens when the button newPost is clicked
        newPost.addActionListener(
                e -> {
                    try {
                        controller.createNewPost();
                        JOptionPane.showMessageDialog(panel, "Redirecting...");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "Error occurred.");
                    }
                });

        // navigate to matching room
        btnMatching.addActionListener(
                e -> {
                    List matches = (List) session.getAllUsers();
                    JPanel matchingPanel =
                            new MatchingRoomView(
                                    frame, currentUser, (java.util.List<User>) matches, session);
                    frame.setContentPane(matchingPanel);
                    frame.revalidate();
                    frame.repaint();
                });
        // navigate to profile
        btnProfile.addActionListener(
                e -> {
                    JPanel profilePanel = new ProfileView(currentUser, frame, session);
                    frame.setContentPane(profilePanel);
                    frame.revalidate();
                    frame.repaint();
                });

        return panel;
    }

    private static JPanel getPost(int i) {
        JPanel postCard = new JPanel();
        postCard.setPreferredSize(new Dimension(450, 40)); // MODIFIED: wider than before
        postCard.setMaximumSize(new Dimension(450, 40)); // MODIFIED: fill parent better
        postCard.setBackground(new Color(255, 255, 255));
        postCard.setBackground(Color.WHITE);
        JLabel label = new JLabel("Post " + i);
        postCard.add(label);
        return postCard;
    }
    // Test with mock user
    //   public static void main(String[] args) throws NoSuchAlgorithmException {
    //        final JFrame frame = new JFrame("post feed");
    //        frame.setSize(500, 600);
    //        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    //        frame.setLocationRelativeTo(null);
    //
    //        CreatePostInteractor interactor = new CreatePostInteractor();
    //        PostFeedViewController controller = new PostFeedViewController(interactor);
    //
    //
    //        User currentUser = new User(
    //                "Java",
    //                1222,
    //                "male",
    //                "Toronto",
    //                "Bio of user",
    //                new ArrayList<>(),
    //                new ArrayList<>(),
    //                new ArrayList<>()
    //        );
    //
    //        UserSession session = new UserSession();
    //        session.initiateSpotify();
    //        session.setUser(currentUser);
    //        session.addUser(currentUser);
    //
    //        JPanel view = PostFeedView.create(controller, frame, currentUser, session);
    //        frame.setContentPane(view);
    //        frame.setVisible(true);
    //    }

}
