package app.usecase_tests;

import app.entities.MatchFilter;
import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.*;
import app.interface_adapter.presenter.AddFriendListPresenter;
import app.interface_adapter.presenter.FriendRequestPresenter;
import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.add_friend_list.AddFriendListInputBoundary;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.usecase.add_friend_list.AddFriendListOutputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestInputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestInteractor;
import app.usecase.handle_friend_request.HandleFriendRequestOutputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestOutputData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HandleFriendRequestInteractorTest {

    @Test
    public void testHandleFriendRequest() {
        List<String> emptyList = new ArrayList<>();
        MatchFilter generalMF = new MatchFilter(0, 100, null, null);

        // Step 1: create two users
        User user0 =
                new User(
                        "Stan",
                        20,
                        "male",
                        "Toronto",
                        "I luv lord",
                        emptyList,
                        emptyList,
                        emptyList);
        User user1 =
                new User(
                        "user01",
                        18,
                        "female",
                        "North York",
                        "Let's go out!",
                        emptyList,
                        emptyList,
                        emptyList);

        // Step 2: set up DataAccessObjects
        UserDataAccessInterface userDataAccessObject = new InMemoryUserDataAccessObject();
        MatchDataAccessInterface matchDataAccessObject = new InMemoryMatchDataAccessObject();
        PostDataAccessInterface postDataAccessObject = new InMemoryPostDataAccessObject();

        // Step 3: create user sessions
        UserSession userSession0 = new UserSession(user0);
        UserSession userSession1 = new UserSession(user1);

        // Step 4: assert initial state
        assertTrue(userSession0.getIncomingMatches().isEmpty());
        assertTrue(userSession0.getOutgoingMatches().isEmpty());
        assertTrue(userSession1.getIncomingMatches().isEmpty());

        // Step 5: send request from user0 to user1
        HandleFriendRequestOutputBoundary dummyPresenter =
                new HandleFriendRequestOutputBoundary() {
                    @Override
                    public void presentFriendRequestSuccess(
                            HandleFriendRequestOutputData outputData) {
                        System.out.println("Sent request: " + outputData.getMessage());
                    }

                    @Override
                    public void presentFriendRequestFailure(String errorMessage) {
                        System.out.println("Failed to send request: " + errorMessage);
                    }
                };

        AddFriendListOutputBoundary addFriendPresenter = new AddFriendListPresenter();
        AddFriendListInputBoundary addFriendInteractor =
                new AddFriendListInteractor(addFriendPresenter);

        HandleFriendRequestInputBoundary sendInteractor =
                new HandleFriendRequestInteractor(
                        matchDataAccessObject, addFriendInteractor, dummyPresenter);
        sendInteractor.sendFriendRequest(userSession0, user1);

        // Step 6: verify user0 has outgoing request
        assertTrue(userSession0.getOutgoingMatches().contains(user1));

        // Step 7: reload userSession1 to reflect DataAccessObject changes
        userSession1 = new UserSession(user1);

        assertTrue(userSession1.getIncomingMatches().contains(user0));

        // Step 8: user1 accepts the request
        FriendRequestViewModel viewModel = new FriendRequestViewModel();
        viewModel.setIncomingRequests(List.of(user0)); // optional: simulate UI state

        HandleFriendRequestOutputBoundary acceptPresenter = new FriendRequestPresenter(viewModel);
        HandleFriendRequestInputBoundary acceptInteractor =
                new HandleFriendRequestInteractor(
                        matchDataAccessObject, addFriendInteractor, acceptPresenter);

        acceptInteractor.acceptFriendRequest(userSession1, user0);

        // Step 9: assertions after accept
        assertFalse(userSession1.getIncomingMatches().contains(user0));
        assertTrue(user1.getFriendList().contains(user0));
        assertTrue(user0.getFriendList().contains(user1));
    }
}
