package app.frameworks_and_drivers.view;

import app.entities.Post;
import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryMatchDataAccessObject;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.frameworks_and_drivers.view.components.CircularButton;
import app.frameworks_and_drivers.view.components.NavButton;
import app.interface_adapter.controller.CreatePostController;
import app.interface_adapter.controller.MatchInteractionController;
import app.interface_adapter.controller.OpenPostController;
import app.interface_adapter.controller.PostFeedController;
import app.interface_adapter.presenter.AddFriendListPresenter;
import app.interface_adapter.presenter.CreatePostPresenter;
import app.interface_adapter.presenter.FriendRequestPresenter;
import app.interface_adapter.presenter.MatchInteractionPresenter;
import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.usecase.create_post.CreatePostInteractor;
import app.usecase.handle_friend_request.HandleFriendRequestInteractor;
import app.usecase.match_interaction.MatchInteractionInteractor;
import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * A Swing view that displays the user's post feed along with a navigation bar and the ability to
 * create new posts or navigate to other parts of the app (Matching Room, My Profile).
 */
public class PostFeedView extends JPanel {
    private final User currentUser;
    private final UserSession session;
    private final JFrame frame;
    private final PostDataAccessInterface postDataAccessObject;

    /**
     * Constructs a PostFeedView with a default in-memory post data access object.
     *
     * @param currentUser the user currently logged in
     * @param session the session managing users and matches
     * @param frame the main application frame
     */
    public PostFeedView(User currentUser, UserSession session, JFrame frame) {
        this.currentUser = currentUser;
        this.session = session;
        this.frame = frame;
        this.postDataAccessObject = new InMemoryPostDataAccessObject();
    }

    /**
     * Constructs a PostFeedView with a custom post data access object.
     *
     * @param currentUser the user currently logged in
     * @param session the session managing users and matches
     * @param frame the main application frame
     * @param postDataAccessObject the post data access object (e.g., in-memory or persistent)
     */
    public PostFeedView(
            User currentUser,
            UserSession session,
            JFrame frame,
            PostDataAccessInterface postDataAccessObject) {
        this.currentUser = currentUser;
        this.session = session;
        this.frame = frame;
        this.postDataAccessObject = postDataAccessObject;
    }

    /**
     * Builds and returns the main post feed panel, including navigation and create post controls.
     *
     * @param controller the controller for handling post creation logic
     * @return the fully constructed post feed {@code JPanel}
     */
    public JPanel create(PostFeedController controller) {
        // MODIFIED: change to BorderLayout for full-frame layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Create a JPanel for title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Post Feed");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.CENTER);

        // Create a JPanel for Post Feed with Scroll
        JPanel postFeedPanel = new JPanel();
        postFeedPanel.setLayout(new BoxLayout(postFeedPanel, BoxLayout.Y_AXIS));

        // Get actual posts from data access layer
        java.util.List<Post> userPosts = postDataAccessObject.getPostsByUser(currentUser);

        if (userPosts.isEmpty()) {
            // Show a message if no posts
            JLabel noPostsLabel =
                    new JLabel("No posts yet. Create your first post!", SwingConstants.CENTER);
            noPostsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            postFeedPanel.add(noPostsLabel);
        } else {
            // Display actual posts
            for (Post post : userPosts) {
                JPanel postCard = getPost(post);
                postFeedPanel.add(Box.createVerticalStrut(10));
                postFeedPanel.add(postCard);
            }
        }

        JScrollPane scrollPane = new JScrollPane(postFeedPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        scrollPane.setBorder(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // "New" Post Button Row
        JPanel newPostWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        CircularButton newPost = new CircularButton("New");
        newPost.setPreferredSize(new Dimension(60, 60));
        newPost.setBackground(new Color(161, 220, 136));
        newPost.setForeground(Color.BLACK);
        newPost.setBorderPainted(false);

        newPostWrapper.add(newPost);
        newPostWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        mainPanel.add(scrollPane);
        // mainPanel.add(newPostWrapper);

        // Navigation bar row
        JPanel navPanel = new JPanel(new GridLayout(1, 3));
        NavButton btnMatching = new NavButton("Matching");
        NavButton btnShare = new NavButton("Share");
        // btnShare.setActive(true);
        NavButton btnProfile = new NavButton("My Profile");

        navPanel.add(btnMatching);
        navPanel.add(btnShare);
        navPanel.add(btnProfile);

        // Bottom panel combining "New" and NavBar
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(newPostWrapper, BorderLayout.NORTH);
        bottomPanel.add(navPanel, BorderLayout.SOUTH);
        bottomPanel.add(navPanel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Define what happens when the button newPost is clicked
        newPost.addActionListener(
                e -> {
                    try {

                        CreatePostPresenter createPostPresenter = new CreatePostPresenter(frame);
                        controller.createNewPost();
                        CreatePostController createPostController =
                                new CreatePostController(
                                        new CreatePostInteractor(postDataAccessObject, createPostPresenter));
                        CreatePostView createPostview =
                                new CreatePostView(
                                        currentUser, session, frame, postDataAccessObject);
                        JPanel createPostPanel = createPostview.create(createPostController);
                        frame.setContentPane(createPostPanel);
                        frame.revalidate();
                        frame.repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "Error occurred.");
                    }
                });

        // navigate to matching room
        btnMatching.addActionListener(
                e -> {
                    List<User> matches = (List<User>) session.getAllUsers();

                    MatchInteractionPresenter matchPresenter = new MatchInteractionPresenter();

                    InMemoryMatchDataAccessObject matchdao = new InMemoryMatchDataAccessObject();

                    AddFriendListPresenter addFriendPresenter = new AddFriendListPresenter();
                    AddFriendListInteractor addFriendInteractor =
                            new AddFriendListInteractor(addFriendPresenter);

                    HandleFriendRequestInteractor friendRequestInteractor =
                            new HandleFriendRequestInteractor(
                                    matchdao,
                                    addFriendInteractor,
                                    new FriendRequestPresenter(new FriendRequestViewModel()));

                    MatchInteractionInteractor interactor =
                            new MatchInteractionInteractor(
                                    matchdao,
                                    friendRequestInteractor,
                                    addFriendInteractor,
                                    matchPresenter);

                    MatchInteractionController matchcontroller =
                            new MatchInteractionController(interactor);

                    JPanel matchingPanel =
                            new MatchingRoomView(
                                    frame,
                                    currentUser,
                                    matches,
                                    session,
                                    matchcontroller,
                                    postDataAccessObject);

                    frame.setContentPane(matchingPanel);
                    frame.revalidate();
                    frame.repaint();
                });

        // navigate to profile
        btnProfile.addActionListener(
                e -> {
                    JPanel profilePanel =
                            new ProfileView(currentUser, frame, session, postDataAccessObject);
                    frame.setContentPane(profilePanel);
                    frame.revalidate();
                    frame.repaint();
                });

        return panel;
    }

    /**
     * Constructs a visual card for an individual post, showing the title, content snippet, and
     * image (if available). Clicking the card opens the full post.
     *
     * @param post the {@link Post} to display
     * @return a {@code JPanel} representing the post preview
     */
    private JPanel getPost(Post post) {
        JPanel postCard = new JPanel(new BorderLayout());
        postCard.setPreferredSize(new Dimension(450, 120));
        postCard.setMaximumSize(new Dimension(450, 120));
        postCard.setBackground(Color.WHITE);
        postCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        // Post title
        JLabel titleLabel = new JLabel(post.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // Post content (truncated if too long)
        String content = post.getText();
        if (content.length() > 100) {
            content = content.substring(0, 97) + "...";
        }
        JLabel contentLabel = new JLabel(content);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        // Post image if available
        if (post.getImage() != null) {
            ImageIcon icon =
                    new ImageIcon(post.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            postCard.add(imageLabel, BorderLayout.EAST);
        }

        // Text content panel
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(contentLabel, BorderLayout.CENTER);
        postCard.add(textPanel, BorderLayout.CENTER);

        // Make the entire card clickable
        postCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postCard.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        OpenPostView openPostView =
                                new OpenPostView(
                                        currentUser, session, frame, postDataAccessObject, post);
                        frame.setContentPane(
                                openPostView.create(new OpenPostController(postDataAccessObject)));
                        frame.revalidate();
                        frame.repaint();
                    }
                });

        return postCard;
    }
}
