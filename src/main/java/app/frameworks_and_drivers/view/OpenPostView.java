package app.frameworks_and_drivers.view;

import app.entities.Comment;
import app.entities.Post;
import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.frameworks_and_drivers.view.components.CommentViewPanel;
import app.interface_adapter.controller.AddCommentController;
import app.interface_adapter.controller.OpenPostController;
import app.interface_adapter.controller.PostFeedController;
import app.interface_adapter.presenter.AddCommentPresenter;
import app.usecase.add_comment.AddCommentInputBoundary;
import app.usecase.add_comment.AddCommentInteractor;
import app.usecase.create_post.CreatePostInteractor;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class OpenPostView extends JPanel implements AddCommentViewInterface{
    private final User currentUser;
    private final UserSession session;
    private final JFrame frame;
    private final PostDataAccessInterface postDataAccessObject;
    private final Post post;
    private JTextArea commentArea;

    // Constructor just to satisfy debugMenu functionality
    public OpenPostView(User user, UserSession session, JFrame frame) {
        this.currentUser = user;
        this.session = session;
        this.frame = frame;
        this.postDataAccessObject = new InMemoryPostDataAccessObject();
        this.post = null;
    }

    public OpenPostView(
            User user,
            UserSession session,
            JFrame frame,
            PostDataAccessInterface postDataAccessObject,
            Post post) {
        this.currentUser = user;
        this.session = session;
        this.frame = frame;
        this.postDataAccessObject = postDataAccessObject;
        this.post = post;
    }

    public JPanel create(OpenPostController controller) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 600));
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
                            new PostFeedView(currentUser, session, frame, postDataAccessObject)
                                    .create(
                                            new PostFeedController(
                                                    new CreatePostInteractor(
                                                            postDataAccessObject))));
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
            ImageIcon icon =
                    new ImageIcon(post.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
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

        JPanel commentPanel = new JPanel(new BorderLayout());

        // Get actual posts from data access layer
        if (post.getComments().isEmpty()) {
            // Show a message if no posts
            JLabel noPostsLabel =
                    new JLabel("No Comment yet. Be the first to connect!", SwingConstants.CENTER);
            noPostsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            commentPanel.add(noPostsLabel);
        } else {
            // Display actual comments
            List<Comment> currentPostComments = post.getComments();

            for (Comment comment : currentPostComments) {
                CommentViewPanel commentViewPanel =
                        new CommentViewPanel(
                        comment.getAuthor(),
                        comment.getText(),
                        comment.getDate(),
                        new Runnable() {
                            @Override
                            public void run() {
                                return;
                            }
                        });
                commentPanel.add(commentViewPanel);
            }
        }

        JScrollPane commentScrollPane = new JScrollPane(commentPanel);

        JTextArea commentArea = new JTextArea(3, 1);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);

        JButton addCommentButton = new JButton("Add Comment");
        addCommentButton.addActionListener(
                e -> {
                    // Comment something
                    String comment = commentArea.getText();
                    AddCommentInteractor interactor =
                            new AddCommentInteractor(postDataAccessObject, new AddCommentPresenter(this));
                    AddCommentController commentController = new AddCommentController(interactor); //TODO: make it another instance attribute
                    commentController.addComment(session, post, comment);

                });

        commentSection.add(commentScrollPane, BorderLayout.NORTH);
        commentSection.add(commentArea, BorderLayout.CENTER);
        commentSection.add(addCommentButton, BorderLayout.SOUTH);

        mainPanel.add(commentSection);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void render(String message, boolean isSuccess) {
        int messageType = isSuccess ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(this, message, isSuccess ? "Success" : "Error", messageType);
    }
}
