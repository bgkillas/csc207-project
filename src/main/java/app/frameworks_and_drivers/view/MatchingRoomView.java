package app.frameworks_and_drivers.view;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryMatchDataAccessObject;
import app.frameworks_and_drivers.view.components.NavButton;
import app.interface_adapter.controller.FriendRequestController;
import app.interface_adapter.controller.PostFeedController;
import app.interface_adapter.presenter.AddFriendListPresenter;
import app.interface_adapter.presenter.FriendRequestPresenter;
import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.add_friend_list.AddFriendListInputBoundary;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.usecase.add_friend_list.AddFriendListOutputBoundary;
import app.usecase.create_post.CreatePostInteractor;
import app.usecase.handle_friend_request.HandleFriendRequestInteractor;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class MatchingRoomView extends JPanel {

    private int currentIndex = 0;

    public MatchingRoomView(
            JFrame frame, User currentUser, List<User> matches, UserSession session) {

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800, 600));
        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        // this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        // this.setBackground(Color.WHITE);

        // Jpanel for Title
        JLabel title = new JLabel("Matching Room", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        // mail icon
        ImageIcon envelopeIcon = new ImageIcon(MatchingRoomView.class.getResource("/mail.png"));
        JButton mailIcon = new JButton(envelopeIcon);
        mailIcon.setBackground(new Color(245, 245, 245));
        // JLabel mailIcon = new JLabel("\u2709", SwingConstants.LEFT);
        // mailIcon.setFont(new Font("Arial", Font.PLAIN, 24));
        // mailIcon.setForeground(Color.DARK_GRAY);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(mailIcon, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        // ConnectRequestView
        mailIcon.addActionListener(
                e -> {
                    FriendRequestViewModel viewModel = new FriendRequestViewModel();
                    viewModel.setIncomingRequests(session.getIncomingMatches());

                    FriendRequestPresenter presenter = new FriendRequestPresenter(viewModel);

                    AddFriendListOutputBoundary addFriendPresenter = new AddFriendListPresenter();
                    AddFriendListInputBoundary addFriendInteractor =
                            new AddFriendListInteractor(addFriendPresenter);

                    HandleFriendRequestInteractor interactor =
                            new HandleFriendRequestInteractor(
                                    new InMemoryMatchDataAccessObject(),
                                    addFriendInteractor,
                                    presenter);

                    FriendRequestController controller = new FriendRequestController(interactor);

                    ConnectRequestView connectRequestView =
                            new ConnectRequestView(
                                    frame, currentUser, session, controller, viewModel);

                    frame.setContentPane(connectRequestView);
                    frame.revalidate();
                    frame.repaint();
                });

        // mailIcon.addMouseListener(
        // new java.awt.event.MouseAdapter() {
        // @Override
        // public void mouseClicked(java.awt.event.MouseEvent e) {
        // frame.setContentPane(new ConnectRequestView(frame, currentUser,
        // session));
        // frame.revalidate();
        // frame.repaint();
        // }
        // });

        // User card panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // matched user's profile picture
        JLabel profilePic = new JLabel("?", SwingConstants.CENTER);
        profilePic.setFont(new Font("Arial", Font.PLAIN, 64));
        profilePic.setPreferredSize(new Dimension(120, 120));

        // matched user's profile
        JLabel profileInfo = new JLabel("", SwingConstants.CENTER);
        profileInfo.setFont(new Font("Arial", Font.PLAIN, 16));

        // match compatibility score
        JLabel score = new JLabel("", SwingConstants.CENTER);
        score.setFont(new Font("Arial", Font.BOLD, 28));
        score.setForeground(new Color(0x2E8B57));

        JPanel innerCard =
                new JPanel(new GridLayout(1, 3)); // made each take up 1/3 rather than 1/2
        innerCard.setBackground(Color.WHITE);
        // innerCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        // Existing visible border
        // Border visibleBorder = BorderFactory.createLineBorder(Color.GRAY, 2);
        // Add empty padding around it (top, left, bottom, right)
        // Border padding = BorderFactory.createEmptyBorder(50, 0, 50, 0);
        // Combine both
        // innerCard.setBorder(BorderFactory.createCompoundBorder(padding, visibleBorder));

        // JPanel right = new JPanel(new BorderLayout());
        // right.add(profileInfo, BorderLayout.CENTER);
        // right.add(score, BorderLayout.EAST);
        // innerCard.add(right);

        innerCard.add(profilePic);
        innerCard.add(profileInfo);
        innerCard.add(score);

        mainPanel.add(innerCard, BorderLayout.CENTER);

        // connect / skip buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());

        JButton connectBtn = createStyledButton("connect", new Color(0x4CAF50));
        JButton skipBtn = createStyledButton("skip", new Color(0xF44336));

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.add(connectBtn);
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.add(skipBtn);

        leftButtonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        rightButtonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        actionPanel.add(leftButtonPanel, BorderLayout.WEST);
        actionPanel.add(rightButtonPanel, BorderLayout.EAST);

        // nav bar
        JPanel navPanel = new JPanel(new GridLayout(1, 3));
        NavButton matchingBtn = new NavButton("Matching");
        // matchingBtn.setActive(true);
        NavButton shareBtn = new NavButton("Share");
        NavButton yourProfileBtn = new NavButton("My Profile");

        navPanel.add(matchingBtn);
        navPanel.add(shareBtn);
        navPanel.add(yourProfileBtn);

        // Bottom control panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        bottomPanel.add(actionPanel);
        // bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(navPanel);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH); // ✅ ONLY ONE add to BorderLayout.SOUTH

        // display logic
        Runnable updateDisplay =
                () -> {
                    if (currentIndex >= matches.size()) {
                        profileInfo.setText("No more matches.");
                        score.setText("");
                        connectBtn.setEnabled(false);
                        skipBtn.setEnabled(false);
                        return;
                    }
                    User match = matches.get(currentIndex);

                    // Update profile picture
                    Image profileImg = match.getProfilePicture();
                    if (profileImg != null) {
                        ImageIcon icon =
                                new ImageIcon(
                                        profileImg.getScaledInstance(120, 120, Image.SCALE_SMOOTH));
                        profilePic.setIcon(icon);
                    } else {
                        profilePic.setIcon(null);
                        profilePic.setText("?");
                    }

                    profileInfo.setText(
                            "<html><b>"
                                    + match.getName()
                                    + "</b><br/>"
                                    + match.getAge()
                                    + "<br/>"
                                    + match.getLocation()
                                    + "<br/>"
                                    + "\""
                                    + match.getBio()
                                    + "\"</html>");
                    score.setText("97%");
                };

        connectBtn.addActionListener(
                e -> {
                    currentUser.getFriendList().add(matches.get(currentIndex));
                    currentIndex++;
                    updateDisplay.run();
                });

        skipBtn.addActionListener(
                e -> {
                    currentIndex++;
                    updateDisplay.run();
                });

        yourProfileBtn.addActionListener(
                e -> {
                    frame.setContentPane(new ProfileView(currentUser, frame, session));
                    frame.revalidate();
                    frame.repaint();
                });

        shareBtn.addActionListener(
                e -> {
                    PostFeedController controller =
                            new PostFeedController(new CreatePostInteractor());
                    JPanel postFeedPanel =
                            new PostFeedView(currentUser, session, frame).create(controller);
                    frame.setContentPane(postFeedPanel);
                    frame.revalidate();
                    frame.repaint();
                });

        updateDisplay.run();
    }

    private static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        button.setMaximumSize(new Dimension(120, 40));
        return button;
    }

    public static void showInFrame(User currentUser, List<User> matches) {
        JFrame frame = new JFrame("JRMC Matching Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        // build a dummy session to avoid null
        UserSession dummySession = new UserSession();
        dummySession.setUser(currentUser);

        MatchingRoomView view = new MatchingRoomView(frame, currentUser, matches, dummySession);
        frame.setContentPane(view);
        frame.setVisible(true);
    }
}
