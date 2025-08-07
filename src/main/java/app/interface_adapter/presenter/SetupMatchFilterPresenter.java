package app.interface_adapter.presenter;

import app.entities.MatchFilter;
import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryMatchDataAccessObject;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.frameworks_and_drivers.view.MatchingRoomView;
import app.interface_adapter.controller.MatchInteractionController;
import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.usecase.handle_friend_request.HandleFriendRequestInteractor;
import app.usecase.match_interaction.MatchInteractionInteractor;
import app.usecase.matchfilter.SetupMatchFilterOutputBoundary;
import app.usecase.matching.MatchServiceImpl;
import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Presenter for handling the successful setup of a match filter. Transitions the UI from the match
 * filter setup view to the matching room view, and displays a confirmation message with the
 * selected filter values. Also initializes the necessary interactors and controllers for user
 * interactions in the matching room.
 */
public class SetupMatchFilterPresenter implements SetupMatchFilterOutputBoundary {

    private final JFrame frame;
    private final UserSession session;
    private final PostDataAccessInterface postDataAccessObject;
    private boolean first;

    /**
     * Constructs the presenter with the necessary application state and data access objects.
     *
     * @param frame the shared application window to update the content pane
     * @param session the current user session containing user data
     * @param postDataAccessObject the data access object for user-generated posts
     */
    public SetupMatchFilterPresenter(
            JFrame frame,
            UserSession session,
            PostDataAccessInterface postDataAccessObject,
            boolean first) {
        this.frame = frame;
        this.session = session;
        this.postDataAccessObject = postDataAccessObject;
        this.first = first;
    }

    /**
     * Called when the match filter is successfully created. Displays a success message and
     * initializes the matching room view with the matched users. Sets up all necessary
     * dependencies, including friend interaction logic and UI transition.
     *
     * @param filter the MatchFilter object representing the user's selected preferences
     */
    @Override
    public void prepareSuccessView(MatchFilter filter) {
        JOptionPane.showMessageDialog(
                frame,
                "Match filter set!\nAge: "
                        + filter.getMinAge()
                        + "-"
                        + filter.getMaxAge()
                        + "\nGender: "
                        + filter.getPreferredGender()
                        + "\nLocation: "
                        + filter.getPreferredLocation());

        User currentUser = session.getUser();
        List<User> allUsers = session.getAllUsers();
        List<User> matches = new MatchServiceImpl().findMatches(currentUser, allUsers);

        MatchInteractionPresenter presenter = new MatchInteractionPresenter();

        InMemoryMatchDataAccessObject matchDataAccessObject = new InMemoryMatchDataAccessObject();

        AddFriendListPresenter addFriendPresenter = new AddFriendListPresenter();
        AddFriendListInteractor addFriendInteractor =
                new AddFriendListInteractor(addFriendPresenter);

        HandleFriendRequestInteractor friendRequestInteractor =
                new HandleFriendRequestInteractor(
                        matchDataAccessObject,
                        addFriendInteractor,
                        new FriendRequestPresenter(new FriendRequestViewModel()));

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        matchDataAccessObject,
                        friendRequestInteractor,
                        addFriendInteractor,
                        presenter);
        MatchInteractionController controller = new MatchInteractionController(interactor);

        if (!first) {
            matches = session.getIncomingMatches();
        }
        first = false;
        JPanel matchingRoomPanel =
                new MatchingRoomView(
                        frame, currentUser, matches, session, controller, postDataAccessObject);
        frame.setTitle("Matching Room");
        frame.setContentPane(matchingRoomPanel);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }
}
