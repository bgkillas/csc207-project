package view;

import entities.Post;
import entities.User;
import entities.UserSession;
import interface_adapter.controller.CreatePostController;
import interface_adapter.controller.OpenPostController;
import interface_adapter.controller.PostFeedController;
import view.components.CircularButton;
import view.components.NavButton;
import view.components.PostPreviewPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** View for the post feed panel. */
public class PostFeedView extends JPanel {
    private final User currentUser;
    private final UserSession session;
    private final JFrame frame;

    public PostFeedView(User currentUser, UserSession session, JFrame frame) {
        this.currentUser = currentUser;
        this.session = session;
        this.frame = frame;
    }

    public JPanel create(PostFeedController controller) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(500, 600));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Create a JPanel for title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Post Feed");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22)); // NEW: emphasized font
        titlePanel.add(titleLabel);
        panel.add(titlePanel, BorderLayout.NORTH); // MODIFIED: use NORTH in BorderLayout

        // Create a JPanel for Post Feed with Scroll
        JPanel postFeedPanel = new JPanel();
        postFeedPanel.setLayout(new BoxLayout(postFeedPanel, BoxLayout.Y_AXIS));

        // add mock "post cards" into it
        for (int i = 1; i <= 10; i++) {
            JPanel postCard = getPost(new Post());
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
        NavButton btnMatching = new NavButton("Matching");
        NavButton btnShare = new NavButton("Share");
        btnShare.setActive(true);
        NavButton btnProfile = new NavButton("My Profile");

        navPanel.add(btnMatching);
        navPanel.add(btnShare);
        navPanel.add(btnProfile);

        // Bottom panel combining "New" and NavBar
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(newPostWrapper, BorderLayout.NORTH);
        bottomPanel.add(navPanel, BorderLayout.SOUTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Define what happens when the button newPost is clicked
        newPost.addActionListener(
                e -> {
                    try {
                        controller.createNewPost();
                        CreatePostView createPostview = new CreatePostView(currentUser, session, frame);
                        CreatePostController control = new CreatePostController();
                        JPanel nextView = createPostview.create(control);

                        frame.setContentPane(nextView);
                        frame.revalidate();
                        frame.repaint();
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

    private JPanel getPost(Post post) {

//        JPanel postCard = new JPanel();
//        postCard.setPreferredSize(new Dimension(450, 40));
//        postCard.setMaximumSize(new Dimension(450, 40));
//        postCard.setBackground(new Color(255, 255, 255));
//        postCard.setBackground(Color.WHITE);
//        JLabel label = new JLabel("Post " + i);
//        postCard.add(label);

        PostPreviewPanel postPanel = new PostPreviewPanel(post.getTitle(), () -> {
            OpenPostView openPostView = new OpenPostView(currentUser, session, frame);
            frame.setContentPane(openPostView.create(new OpenPostController()));
            frame.revalidate();
            frame.repaint();
        });

        return postPanel;
    }

}
