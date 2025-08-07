package app.frameworks_and_drivers.view;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.interface_adapter.controller.CreatePostController;
import app.interface_adapter.controller.PostFeedController;
import app.interface_adapter.presenter.CreatePostPresenter;
import app.usecase.create_post.CreatePostInteractor;
import java.awt.*;
import java.io.File;
import javax.swing.*;

/**
 * A Swing view that allows users to create a new post. This view contains fields for title and
 * content, an optional image upload, and a post button to submit the post. After posting, the user
 * is navigated back to the post feed.
 */
public class CreatePostView {
    private final User currentUser;
    private final UserSession session;
    private final JFrame frame;
    private final PostDataAccessInterface postDataAccessObject;

    private JTextField titleField;
    private JTextArea contentArea;
    private JLabel imagePreview;
    private File imageFile;

    /**
     * Constructs a new CreatePostView.
     *
     * @param user the currently logged-in user
     * @param session the user session object
     * @param frame the main application frame
     * @param postDataAccessObject the data access object used for post operations
     */
    public CreatePostView(
            User user,
            UserSession session,
            JFrame frame,
            PostDataAccessInterface postDataAccessObject) {
        this.currentUser = user;
        this.session = session;
        this.frame = frame;
        this.postDataAccessObject = postDataAccessObject;
    }

    /**
     * Creates the full post creation view panel. Users can enter a post title and content, upload
     * an image, and submit the post. On submission, the post is saved and the user is navigated
     * back to the post feed.
     *
     * @param controller the controller handling post creation logic
     * @return a JPanel containing all UI components for creating a post
     */
    public JPanel create(CreatePostController controller) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 600));

        // Create a JPanel for title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Create Post");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        JButton back = new JButton("← Back");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(back, BorderLayout.WEST);
        topPanel.add(titlePanel, BorderLayout.CENTER);

        back.addActionListener(
                e -> {
                    CreatePostPresenter createPostPresenter = new CreatePostPresenter(frame);

                    frame.setContentPane(
                            new PostFeedView(currentUser, session, frame, postDataAccessObject)
                                    .create(
                                            new PostFeedController(
                                                    new CreatePostInteractor(
                                                            postDataAccessObject, createPostPresenter))));
                    frame.revalidate();
                    frame.repaint();
                });

        // Create a JPanel for main content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        titleField = new JTextField(30);
        contentArea = new JTextArea(10, 30);
        imagePreview = new JLabel("No image selected");

        JButton uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(
                e -> {
                    JFileChooser chooser = new JFileChooser();
                    if (chooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
                        imageFile = chooser.getSelectedFile();
                        imagePreview.setText(imageFile.getName());
                    }
                });

        JButton postButton = new JButton("Post");
        postButton.addActionListener(
                e -> {
                    String title = titleField.getText();
                    String content = contentArea.getText();
                    controller.postNewPost(title, content, imageFile, currentUser);

                    CreatePostPresenter createPostPresenter = new CreatePostPresenter(frame);

                    // Navigate back to post feed after posting
                    frame.setContentPane(
                            new PostFeedView(currentUser, session, frame, postDataAccessObject)
                                    .create(
                                            new PostFeedController(
                                                    new CreatePostInteractor(
                                                            postDataAccessObject, createPostPresenter))));
                    frame.revalidate();
                    frame.repaint();
                });

        JPanel postTitlePanel = new JPanel();
        JPanel postContentPanel = new JPanel();
        JPanel postImageUploadPanel = new JPanel();

        postTitlePanel.add(new JLabel("Title:"));
        postTitlePanel.add(titleField);
        postTitlePanel.add(postButton);
        postContentPanel.add(new JScrollPane(contentArea));
        postImageUploadPanel.add(imagePreview);
        postImageUploadPanel.add(uploadButton);

        mainPanel.add(postTitlePanel);
        mainPanel.add(postContentPanel);
        mainPanel.add(postImageUploadPanel);

        // Navigation bar
        // JPanel navPanel = new JPanel(new GridLayout(1, 3));
        // JButton btnMatching = new JButton("Matching");
        // JButton btnShare = new JButton("Share");
        // JButton btnProfile = new JButton("My Profile");
        // navPanel.add(btnMatching);
        // navPanel.add(btnShare);
        // navPanel.add(btnProfile);

        // Create a JPanel for Bottom NavBar
        // JPanel bottomPanel = new JPanel(new BorderLayout());
        // bottomPanel.add(navPanel, BorderLayout.SOUTH);

        // add all components to panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);
        // panel.add(bottomPanel, BorderLayout.SOUTH);

        // navigate to matching room
        // btnMatching.addActionListener(
        // e -> {
        // java.util.List<User> matchedUsers = session.getAllUsers();
        // JPanel matchingPanel =
        // new MatchingRoomView(frame, session.getUser(), matchedUsers,
        // session);
        // frame.setContentPane(matchingPanel);
        // frame.revalidate();
        // frame.repaint();
        // });

        // // navigate to profile
        // btnProfile.addActionListener(
        // e -> {
        // JPanel profilePanel = new ProfileView(session.getUser(), frame,
        // session);
        // frame.setContentPane(profilePanel);
        // frame.revalidate();
        // frame.repaint();
        // });

        return panel;
    }
}
