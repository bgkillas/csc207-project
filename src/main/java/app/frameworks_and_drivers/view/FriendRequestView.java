package app.frameworks_and_drivers.view;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryMatchDataAccessObject;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.interface_adapter.controller.FriendRequestController;
import app.interface_adapter.controller.MatchInteractionController;
import app.interface_adapter.presenter.AddFriendListPresenter;
import app.interface_adapter.presenter.FriendRequestPresenter;
import app.interface_adapter.presenter.MatchInteractionPresenter;
import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.usecase.handle_friend_request.HandleFriendRequestInteractor;
import app.usecase.match_interaction.MatchInteractionInteractor;
import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * A Swing view that displays incoming friend requests and allows the user to accept or decline each
 * one. This view shows one request at a time in a card layout with basic profile info and
 * interactive buttons for responding to the request. Once the user responds, the next request is
 * shown.
 */
public class FriendRequestView extends JPanel {
    private int currentIndex = 0;

    /**
     * Constructs a new {FriendRequestView}.
     *
     * @param frame the main application window
     * @param currentUser the user currently logged in
     * @param session the active user session
     * @param controller the controller handling friend request actions
     * @param viewModel the view model containing pending friend requests
     * @param postDataAccessObject the data access object for post data
     */
    public FriendRequestView(
            JFrame frame,
            User currentUser,
            UserSession session,
            FriendRequestController controller,
            FriendRequestViewModel viewModel,
            PostDataAccessInterface postDataAccessObject) {
        List<User> requests = viewModel.getAllRequests();
        requests.removeIf(u -> u.equals(currentUser));

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        this.setBackground(Color.WHITE);

        // Top bar
        JLabel title = new JLabel("Connect Request", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel countLabel = new JLabel("", SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        MatchInteractionPresenter matchPresenter = new MatchInteractionPresenter();
        InMemoryMatchDataAccessObject matchDataAccessObject = new InMemoryMatchDataAccessObject();

        AddFriendListPresenter addFriendPresenter = new AddFriendListPresenter();
        AddFriendListInteractor addFriendInteractor =
                new AddFriendListInteractor(addFriendPresenter);

        HandleFriendRequestInteractor friendRequestInteractor =
                new HandleFriendRequestInteractor(
                        matchDataAccessObject,
                        addFriendInteractor,
                        new FriendRequestPresenter(new FriendRequestViewModel()));

        MatchInteractionInteractor matchInteractor =
                new MatchInteractionInteractor(
                        matchDataAccessObject,
                        friendRequestInteractor,
                        addFriendInteractor,
                        matchPresenter);

        MatchInteractionController matchController =
                new MatchInteractionController(matchInteractor);

        JButton back = new JButton("← Back");
        back.addActionListener(
                e -> {
                    JPanel matchingRoom =
                            new MatchingRoomView(
                                    frame,
                                    currentUser,
                                    session.getAllUsers(),
                                    session,
                                    matchController,
                                    postDataAccessObject);
                    frame.setContentPane(matchingRoom);
                    frame.revalidate();
                });

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(back, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(countLabel, BorderLayout.EAST);

        this.add(topBar, BorderLayout.NORTH);

        // Card panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        cardPanel.setBackground(Color.WHITE);

        JLabel profilePic = new JLabel("?", SwingConstants.CENTER);
        profilePic.setFont(new Font("Arial", Font.PLAIN, 64));
        profilePic.setPreferredSize(new Dimension(120, 120));

        JLabel info = new JLabel("", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel score = new JLabel("50%", SwingConstants.CENTER); // 可自定义
        score.setFont(new Font("Arial", Font.BOLD, 24));
        score.setForeground(new Color(0x2E8B57));

        cardPanel.add(profilePic, BorderLayout.WEST);
        cardPanel.add(info, BorderLayout.CENTER);
        cardPanel.add(score, BorderLayout.EAST);

        this.add(cardPanel, BorderLayout.CENTER);

        // Action buttons
        JButton acceptBtn = createStyledButton("accept", new Color(0x4CAF50));
        JButton declineBtn = createStyledButton("decline", new Color(0xF44336));

        JPanel actionPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.add(acceptBtn);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // optional padding

        JPanel rightPanel = new JPanel();
        rightPanel.add(declineBtn);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // optional padding

        actionPanel.add(leftPanel, BorderLayout.WEST);
        actionPanel.add(rightPanel, BorderLayout.EAST);

        this.add(actionPanel, BorderLayout.SOUTH);

        // Display logic
        Runnable updateCard =
                () -> {
                    if (!viewModel.hasRequests() || currentIndex >= requests.size()) {
                        info.setText("No more requests.");
                        score.setText("");
                        countLabel.setText("");
                        acceptBtn.setEnabled(false);
                        declineBtn.setEnabled(false);
                        return;
                    }

                    User other = viewModel.getCurrentUser();

                    info.setText(
                            "<html><b>"
                                    + other.getName()
                                    + "</b><br/>"
                                    + other.getAge()
                                    + "<br/>"
                                    + other.getLocation()
                                    + "<br/><i>"
                                    + other.getBio()
                                    + "</i></html>");
                    countLabel.setText((currentIndex + 1) + " / " + requests.size());
                };

        acceptBtn.addActionListener(
                e -> {
                    User other = requests.get(currentIndex);
                    controller.acceptRequest(session, other);
                    // viewModel.removeCurrentRequest();
                    currentIndex++;
                    updateCard.run();
                });

        declineBtn.addActionListener(
                e -> {
                    User other = requests.get(currentIndex);
                    controller.declineRequest(session, other);
                    // viewModel.removeCurrentRequest();
                    currentIndex++;
                    updateCard.run();
                });

        updateCard.run();
    }

    /**
     * Creates a styled button with specified text and background color.
     *
     * @param text the button text
     * @param backgroundColor the background color of the button
     * @return the styled {@code JButton}
     */
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
}
