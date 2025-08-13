package app.frameworks_and_drivers.view;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryMatchDataAccessObject;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.frameworks_and_drivers.view.components.NavButton;
import app.interface_adapter.controller.MatchInteractionController;
import app.interface_adapter.controller.PostFeedController;
import app.interface_adapter.controller.SetupUserProfileController;
import app.interface_adapter.presenter.*;
import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.usecase.create_post.CreatePostInteractor;
import app.usecase.handle_friend_request.HandleFriendRequestInteractor;
import app.usecase.match_interaction.MatchInteractionInteractor;
import app.usecase.matching.FindMatchesInteractor;
import app.usecase.matching.FindMatchesOutputBoundary;
import app.usecase.matching.FindMatchesRequestModel;
import app.usecase.matching.FindMatchesResponseModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List; // Needed for generics List<User>
import javax.swing.*;

/**
 * A panel that displays a user's profile, including bio, music preferences, and profile actions.
 * Shows edit/buddy/block options for the current user, or block/unblock for other users. Includes
 * bottom navigation to Matching, Share (Post Feed), and My Profile views.
 */
public class ProfileView extends JPanel {
    private final User user;
    private final JFrame frame;
    private final UserSession userSession;
    private final PostDataAccessInterface postDataAccessObject;
    private final SetupUserProfileController profileSetupController;

    /**
     * Constructs a ProfileView for the given user.
     *
     * @param user the user whose profile is to be displayed
     * @param frame the JFrame to which this view will be added
     * @param userSession the user session containing all users and matches
     */
    public ProfileView(
            User user,
            JFrame frame,
            UserSession userSession,
            PostDataAccessInterface postDataAccessObject,
            SetupUserProfileController profileSetupController) {
        this.user = user;
        this.frame = frame;
        this.userSession = userSession;
        this.postDataAccessObject = postDataAccessObject;
        this.profileSetupController = profileSetupController;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800, 600));
        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        create();
    }

    /** Initializes the profile view components. */
    public void create() {
        JPanel panel = new JPanel(new BorderLayout());

        // Jpanel for Title
        JLabel title = new JLabel(user.getName() + "'s Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        // top panel with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(title);

        // main panel with profile info
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JPanel profilePanel = createProfilePanel();
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        mainPanel.add(profilePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Bottom panel with navButtons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        NavButton matchingBtn = new NavButton("Matching");
        NavButton shareBtn = new NavButton("Share");
        NavButton myProfileBtn = new NavButton("My Profile");

        JPanel navPanel = new JPanel(new GridLayout(1, 3));

        navPanel.add(matchingBtn);
        navPanel.add(shareBtn);
        navPanel.add(myProfileBtn);

        bottomPanel.add(navPanel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // local variable currentUser is the user of this userSession
        User currentUser = userSession.getUser();

        // Add the matching button action
        matchingBtn.addActionListener(
                e -> {
                    // Use FindMatchesInteractor instead of MatchServiceImpl
                    final java.util.concurrent.atomic.AtomicReference<List<User>> matchesRef =
                            new java.util.concurrent.atomic.AtomicReference<>();
                    FindMatchesOutputBoundary presenter =
                            new FindMatchesOutputBoundary() {
                                @Override
                                public void present(FindMatchesResponseModel responseModel) {
                                    matchesRef.set(responseModel.getMatches());
                                }
                            };
                    FindMatchesInteractor findInteractor = new FindMatchesInteractor(presenter);
                    findInteractor.findMatches(
                            new FindMatchesRequestModel(currentUser, userSession.getAllUsers()));
                    List<User> matches = matchesRef.get();

                    InMemoryMatchDataAccessObject matchDataAccessObject =
                            new InMemoryMatchDataAccessObject();

                    FriendRequestViewModel requestViewModel = new FriendRequestViewModel();
                    FriendRequestPresenter requestPresenter =
                            new FriendRequestPresenter(requestViewModel);
                    AddFriendListPresenter addFriendPresenter = new AddFriendListPresenter();
                    MatchInteractionPresenter matchPresenter =
                            new MatchInteractionPresenter(); // 弹窗提示

                    AddFriendListInteractor addFriendInteractor =
                            new AddFriendListInteractor(addFriendPresenter);
                    HandleFriendRequestInteractor friendRequestInteractor =
                            new HandleFriendRequestInteractor(
                                    matchDataAccessObject, addFriendInteractor, requestPresenter);

                    MatchInteractionInteractor interactor =
                            new MatchInteractionInteractor(
                                    matchDataAccessObject,
                                    friendRequestInteractor,
                                    addFriendInteractor,
                                    matchPresenter);

                    MatchInteractionController controller =
                            new MatchInteractionController(interactor);

                    JPanel matchingRoomPanel =
                            new MatchingRoomView(
                                    frame,
                                    currentUser,
                                    userSession.getMatchesTemp(),
                                    userSession,
                                    controller,
                                    postDataAccessObject);

                    frame.setContentPane(matchingRoomPanel);
                    frame.revalidate();
                    frame.repaint();
                });

        myProfileBtn.addActionListener(
                e -> {
                    ProfileView profileView =
                            new ProfileView(
                                    currentUser,
                                    frame,
                                    userSession,
                                    postDataAccessObject,
                                    profileSetupController);
                    frame.setContentPane(profileView);
                    frame.revalidate();
                    frame.repaint();
                });
        shareBtn.addActionListener(
                e -> {
                    frame.setContentPane(
                            new PostFeedView(currentUser, userSession, frame, postDataAccessObject)
                                    .create(
                                            new PostFeedController(
                                                    new CreatePostInteractor(
                                                            postDataAccessObject))));
                    frame.revalidate();
                    frame.repaint();
                });

        add(panel);
    }

    private JPanel createProfilePanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        // contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        // profile picture
        Image profileImg = user.getProfilePicture();
        JLabel profilePic;

        if (profileImg != null) {
            ImageIcon icon =
                    new ImageIcon(profileImg.getScaledInstance(120, 120, Image.SCALE_SMOOTH));
            profilePic = new JLabel(icon);
        } else {
            profilePic = new JLabel("?", SwingConstants.CENTER);
            profilePic.setFont(new Font("Arial", Font.PLAIN, 64));
        }
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

    private boolean isUser() {
        return user == userSession.getUser();
    }

    private JPanel createButtonPanel() {

        // Action buttons panel (edit profile, buddy list, block list)
        JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton editProfileBtn = createActionButton("edit profile");
        JButton buddyListBtn = createActionButton("buddy list");
        JButton blockListBtn = createActionButton("block list");

        JButton blockBtn = createActionButton("block");
        JButton unBlockBtn = createActionButton("unblock");

        editProfileBtn.addActionListener(
                e -> {
                    JPanel profileSetupPanel =
                            ProfileSetupView.create(profileSetupController, userSession.getUser());
                    frame.setContentPane(profileSetupPanel);
                    frame.setTitle("Edit Profile");
                    frame.setPreferredSize(new Dimension(800, 600));
                    frame.pack();
                    frame.revalidate();
                    frame.repaint();
                });

        buddyListBtn.addActionListener(
                e -> {
                    BuddyListView buddyList =
                            new BuddyListView(user, userSession, frame, postDataAccessObject);
                    frame.setContentPane(buddyList.create());
                    frame.revalidate();
                    frame.repaint();
                });
        blockListBtn.addActionListener(
                e -> {
                    BlockListView blockList =
                            new BlockListView(user, userSession, frame, postDataAccessObject);
                    frame.setContentPane(blockList.create());
                    frame.revalidate();
                    frame.repaint();
                });
        blockBtn.addActionListener(
                e -> {
                    userSession.getUser().addBlock(user);
                    ProfileView profileView =
                            new ProfileView(
                                    user,
                                    frame,
                                    userSession,
                                    postDataAccessObject,
                                    profileSetupController);
                    frame.setContentPane(profileView);
                    frame.revalidate();
                    frame.repaint();
                });
        unBlockBtn.addActionListener(
                e -> {
                    userSession.getUser().removeBlock(user);
                    ProfileView profileView =
                            new ProfileView(
                                    user,
                                    frame,
                                    userSession,
                                    postDataAccessObject,
                                    profileSetupController);
                    frame.setContentPane(profileView);
                    frame.revalidate();
                    frame.repaint();
                });

        if (isUser()) {
            actionPanel.add(editProfileBtn);
            actionPanel.add(buddyListBtn);
            actionPanel.add(blockListBtn);
        } else {
            if (userSession.getUser().hasBlock(user)) {
                actionPanel.add(unBlockBtn);
            } else {
                actionPanel.add(blockBtn);
            }
        }
        return actionPanel;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(0x4CAF50)); // Green color
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }
}
