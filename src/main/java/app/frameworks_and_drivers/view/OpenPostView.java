package app.frameworks_and_drivers.view;

import app.entities.Post;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.usecase.create_post.CreatePostInteractor;
import app.entities.User;
import app.entities.UserSession;
import app.interface_adapter.controller.OpenPostController;
import app.interface_adapter.controller.PostFeedController;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OpenPostView extends JPanel {
    private final User currentUser;
    private final UserSession session;
    private final JFrame frame;
    private final PostDataAccessInterface postDAO;
    private final Post post;

    // Constructor just to satisfy debugMenu functionality
    public OpenPostView(User user, UserSession session, JFrame frame) {
        this.currentUser = user;
        this.session = session;
        this.frame = frame;
        this.postDAO = new InMemoryPostDataAccessObject();
        this.post = null;
    }

    public OpenPostView(User user, UserSession session, JFrame frame, PostDataAccessInterface postDAO, Post post) {
        this.currentUser = user;
        this.session = session;
        this.frame = frame;
        this.postDAO = postDAO;
        this.post = post;
    }

    public JPanel create(OpenPostController controller) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(500, 600));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Create a JPanel for title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Post Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        JButton back = new JButton("← Back");
        back.addActionListener(
                e -> {
                    frame.setContentPane(
                            new PostFeedView(currentUser, session, frame, postDAO)
                                    .create(new PostFeedController(new CreatePostInteractor(postDAO))));
                    frame.revalidate();
                    frame.repaint();
                });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(back, BorderLayout.WEST);
        topPanel.add(titlePanel, BorderLayout.CENTER);

        // Create a JPanel for main content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Display post content
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        postPanel.setBackground(Color.WHITE);
        postPanel.setMaximumSize(new Dimension(450, 400));

        // Post title
        JLabel postTitleLabel = new JLabel(post.getTitle());
        postTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        postTitleLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        // Post content
        JTextArea contentArea = new JTextArea(post.getText());
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setBorder(null);

        // Post image if available
        if (post.getImage() != null) {
            ImageIcon icon = new ImageIcon(post.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
            postPanel.add(imageLabel, BorderLayout.SOUTH);
        }

        postPanel.add(postTitleLabel, BorderLayout.NORTH);
        postPanel.add(contentScrollPane, BorderLayout.CENTER);

        mainPanel.add(postPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Comment Section Bar
        JPanel commentSection = new JPanel(new BorderLayout());
        commentSection.setBorder(BorderFactory.createTitledBorder("Comments"));

        JTextArea commentArea = new JTextArea(3, 30);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        JScrollPane commentScrollPane = new JScrollPane(commentArea);

        JButton addCommentButton = new JButton("Add Comment");
        addCommentButton.addActionListener(e -> {
            //Comment something
        });

        commentSection.add(commentScrollPane, BorderLayout.CENTER);
        commentSection.add(addCommentButton, BorderLayout.SOUTH);

        mainPanel.add(commentSection);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
