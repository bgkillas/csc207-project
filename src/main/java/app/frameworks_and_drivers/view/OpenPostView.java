package app.frameworks_and_drivers.view;

import app.entities.Comment;
import app.entities.Post;
import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.frameworks_and_drivers.view.components.CommentViewPanel;
import app.frameworks_and_drivers.view.components.DesignedButton;
import app.frameworks_and_drivers.view.components.Placeholder;
import app.interface_adapter.controller.AddCommentController;
import app.interface_adapter.controller.PostFeedController;
import app.interface_adapter.presenter.AddCommentPresenter;
import app.usecase.add_comment.AddCommentInteractor;
import app.usecase.create_post.CreatePostInteractor;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

/**
 * A view for displaying the details of a post, including title, content, image, and comments. Users
 * can view existing comments and add a new one. This class implements AddCommentViewInterface to
 * render comment result feedback.
 */
public class OpenPostView extends JPanel implements AddCommentViewInterface {
    private final User currentUser;
    private final UserSession session;
    private final JFrame frame;
    private final PostDataAccessInterface postDataAccessObject;
    private final Post post;
    private JTextArea commentArea;

    /**
     * Debug-only constructor.
     *
     * @param user The current user
     * @param session The user's session
     * @param frame The main application frame
     */
    public OpenPostView(User user, UserSession session, JFrame frame) {
        this.currentUser = user;
        this.session = session;
        this.frame = frame;
        this.postDataAccessObject = new InMemoryPostDataAccessObject();
        this.post = null;
    }

    /**
     * Constructs an OpenPostView with the necessary data to show a post and interact with it.
     *
     * @param user The user currently logged in
     * @param session The user's session
     * @param frame The main application frame
     * @param postDataAccessObject Data access object for post and comment data
     * @param post The post to be displayed
     */
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

    /**
     * Creates the main UI panel for displaying post content and allowing comments.
     *
     * @return A fully assembled JPanel containing the view
     */
    public JPanel create() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Create a JPanel for title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Post Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        JButton back = new DesignedButton("← Back");
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

        // Create top panel - JPanel with title and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(back, BorderLayout.WEST);
        topPanel.add(titlePanel, BorderLayout.CENTER);

        // Create main panel - JPanel for main content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create post panel - JPanel for displaying post title & content
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        postPanel.setBackground(Color.WHITE);

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

        // Comment Section Bar
        JPanel commentSection = new JPanel(new BorderLayout());
        commentSection.setBorder(BorderFactory.createTitledBorder("Comments"));

        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));

        // Get actual comments from data access layer
        addComments(commentPanel);

        // Wrap commentPanel in a fixed-height container panel
        JPanel scrollableWrapper = new JPanel(new BorderLayout());
        scrollableWrapper.add(commentPanel, BorderLayout.NORTH);

        JScrollPane commentScrollPane = new JScrollPane(scrollableWrapper);
        // set max height for scroll area
        commentScrollPane.setPreferredSize(new Dimension(400, 120));

        commentArea = new JTextArea();
        Placeholder.setup(commentArea, "Enter your comment here!");
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);

        JButton addCommentButton = new DesignedButton("Comment");
        addCommentButton.addActionListener(
                e -> {
                    String comment = commentArea.getText();
                    AddCommentInteractor interactor =
                            new AddCommentInteractor(
                                    postDataAccessObject, new AddCommentPresenter(this));
                    AddCommentController commentController = new AddCommentController(interactor);
                    commentController.addComment(session, post, comment);

                    // refresh the page
                    OpenPostView openPostView =
                            new OpenPostView(
                                    currentUser, session, frame, postDataAccessObject, post);
                    frame.setContentPane(openPostView.create());
                    frame.revalidate();
                    frame.repaint();
                });

        JPanel addCommentPanel = new JPanel(new BorderLayout());
        addCommentPanel.add(commentArea);
        addCommentPanel.add(addCommentButton, BorderLayout.EAST);

        commentSection.add(commentScrollPane, BorderLayout.CENTER);
        commentSection.add(addCommentPanel, BorderLayout.SOUTH);

        mainPanel.add(postPanel, BorderLayout.CENTER);
        mainPanel.add(commentSection, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Displays a popup message in the UI to indicate the result of a comment submission.
     *
     * @param message The message to display
     * @param isSuccess True if successful, false if error
     */
    @Override
    public void render(String message, boolean isSuccess) {
        int messageType = isSuccess ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(this, message, isSuccess ? "Success" : "Error", messageType);
    }

    private void addComments(JPanel commentPanel) {
        // Get actual comments from data access layer
        if (post.getComments().isEmpty()) {
            // Show a message if no posts
            JLabel noPostsLabel =
                    new JLabel("No Comment yet. Be the first to connect!", SwingConstants.CENTER);
            noPostsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            commentPanel.add(noPostsLabel);
        } else {
            // Display filtered list of comments for the currentUser
            List<Comment> currentPostComments = post.getFilteredComments(currentUser);
            // reverse order to newest to oldest.
            Collections.reverse(currentPostComments);

            Runnable runnable =
                    new Runnable() {
                        @Override
                        public void run() {
                            return;
                        }
                    };
            for (Comment comment : currentPostComments) {
                CommentViewPanel commentViewPanel =
                        new CommentViewPanel(
                                comment.getAuthor().getName(),
                                comment.getText(),
                                comment.getDate(),
                                runnable);
                commentPanel.add(commentViewPanel);
                commentPanel.revalidate();
                commentPanel.repaint();
            }
        }
    }
}
