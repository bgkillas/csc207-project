package app.interface_adapter.presenter;

import app.entities.MatchFilter;
import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryMatchDataAccessObject;
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

public class SetupMatchFilterPresenter implements SetupMatchFilterOutputBoundary {

    private final JFrame frame;
    private final UserSession session;

    public SetupMatchFilterPresenter(JFrame frame, UserSession session) {
        this.frame = frame;
        this.session = session;
    }

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

        InMemoryMatchDataAccessObject matchDAO = new InMemoryMatchDataAccessObject();

        AddFriendListPresenter addFriendPresenter = new AddFriendListPresenter();
        AddFriendListInteractor addFriendInteractor = new AddFriendListInteractor(addFriendPresenter);

        HandleFriendRequestInteractor friendRequestInteractor = new HandleFriendRequestInteractor(
                matchDAO, addFriendInteractor, new FriendRequestPresenter(new FriendRequestViewModel())
        );

        MatchInteractionInteractor interactor = new MatchInteractionInteractor(
                matchDAO,
                friendRequestInteractor,
                addFriendInteractor,
                presenter
        );
        MatchInteractionController controller = new MatchInteractionController(interactor);


        JPanel matchingRoomPanel =
                new MatchingRoomView(frame, currentUser, matches, session, controller);
        frame.setTitle("Matching Room");
        frame.setContentPane(matchingRoomPanel);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }

}
