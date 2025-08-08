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
import java.util.concurrent.atomic.AtomicReference;

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
        // assertTrue(userSession0.getIncomingMatches().isEmpty());
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


        assertTrue(matchDataAccessObject.getIncomingFriendRequest(user1).contains(user0));

        // in memory DAO will pull info to this userSession but this process is not implemented in this project
        if (matchDataAccessObject.getIncomingFriendRequest(user1).contains(user0)) {
            // the pulling that would have occurred if implemented.
            userSession1.addIncomingMatch(matchDataAccessObject.getIncomingFriendRequest(user1).get(0));
        }

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
        assertTrue(userSession1.getUser().getFriendList().contains(user0));
        assertTrue(user0.getFriendList().contains(userSession1.getUser()));
    }

    @Test
    public void testSendFriendRequestToNullUser() {
        UserSession session =
                new UserSession(
                        new User("Stan", 20, "m", "Toronto", "", List.of(), List.of(), List.of()));
        AtomicReference<String> failureMessage = new AtomicReference<>();

        HandleFriendRequestOutputBoundary presenter =
                new HandleFriendRequestOutputBoundary() {
                    @Override
                    public void presentFriendRequestSuccess(HandleFriendRequestOutputData data) {
                        fail("Should not succeed");
                    }

                    @Override
                    public void presentFriendRequestFailure(String message) {
                        failureMessage.set(message);
                    }
                };

        HandleFriendRequestInteractor interactor =
                new HandleFriendRequestInteractor(
                        new InMemoryMatchDataAccessObject(), (a, b) -> {}, presenter);

        interactor.sendFriendRequest(session, null);
        assertEquals("Cannot send friend request: user does not exist.", failureMessage.get());
    }

    @Test
    public void testSendFriendRequestToSelf() {
        User user = new User("Stan", 20, "m", "Toronto", "", List.of(), List.of(), List.of());
        UserSession session = new UserSession(user);
        AtomicReference<String> failureMessage = new AtomicReference<>();

        HandleFriendRequestOutputBoundary presenter =
                new HandleFriendRequestOutputBoundary() {
                    public void presentFriendRequestSuccess(HandleFriendRequestOutputData data) {
                        fail("Should not succeed");
                    }

                    public void presentFriendRequestFailure(String message) {
                        failureMessage.set(message);
                    }
                };

        HandleFriendRequestInteractor interactor =
                new HandleFriendRequestInteractor(
                        new InMemoryMatchDataAccessObject(), (a, b) -> {}, presenter);

        interactor.sendFriendRequest(session, user);
        assertEquals("Cannot send friend request to yourself.", failureMessage.get());
    }

    @Test
    public void testSendDuplicateFriendRequest() {
        User user0 = new User("Stan", 20, "m", "Toronto", "", List.of(), List.of(), List.of());
        User user1 = new User("Jess", 20, "f", "NY", "", List.of(), List.of(), List.of());
        UserSession session = new UserSession(user0);
        session.getOutgoingMatches().add(user1);

        AtomicReference<String> failureMessage = new AtomicReference<>();

        HandleFriendRequestOutputBoundary presenter =
                new HandleFriendRequestOutputBoundary() {
                    public void presentFriendRequestSuccess(HandleFriendRequestOutputData data) {
                        fail("Should not succeed");
                    }

                    public void presentFriendRequestFailure(String message) {
                        failureMessage.set(message);
                    }
                };

        HandleFriendRequestInteractor interactor =
                new HandleFriendRequestInteractor(
                        new InMemoryMatchDataAccessObject(), (a, b) -> {}, presenter);

        interactor.sendFriendRequest(session, user1);
        assertEquals("Already friends or request already sent.", failureMessage.get());
    }

    @Test
    public void testAcceptNonexistentFriendRequest() {
        User user0 = new User("Stan", 20, "m", "Toronto", "", List.of(), List.of(), List.of());
        User user1 = new User("Jess", 20, "f", "NY", "", List.of(), List.of(), List.of());
        UserSession session = new UserSession(user1); // Jess is current user

        AtomicReference<String> failureMessage = new AtomicReference<>();

        HandleFriendRequestOutputBoundary presenter =
                new HandleFriendRequestOutputBoundary() {
                    public void presentFriendRequestSuccess(HandleFriendRequestOutputData data) {
                        fail("Should not succeed");
                    }

                    public void presentFriendRequestFailure(String message) {
                        failureMessage.set(message);
                    }
                };

        HandleFriendRequestInteractor interactor =
                new HandleFriendRequestInteractor(
                        new InMemoryMatchDataAccessObject(), (a, b) -> {}, presenter);

        interactor.acceptFriendRequest(session, user0); // Stan sent no request
        assertEquals("Friend request does not exist.", failureMessage.get());
    }

    @Test
    public void testDeclineNonexistentFriendRequest() {
        User user0 = new User("Stan", 20, "m", "Toronto", "", List.of(), List.of(), List.of());
        User user1 = new User("Jess", 20, "f", "NY", "", List.of(), List.of(), List.of());
        UserSession session = new UserSession(user1); // Jess is current user

        AtomicReference<String> failureMessage = new AtomicReference<>();

        HandleFriendRequestOutputBoundary presenter =
                new HandleFriendRequestOutputBoundary() {
                    public void presentFriendRequestSuccess(HandleFriendRequestOutputData data) {
                        fail("Should not succeed");
                    }

                    public void presentFriendRequestFailure(String message) {
                        failureMessage.set(message);
                    }
                };

        HandleFriendRequestInteractor interactor =
                new HandleFriendRequestInteractor(
                        new InMemoryMatchDataAccessObject(), (a, b) -> {}, presenter);

        interactor.declineFriendRequest(session, user0); // Stan sent no request
        assertEquals("Friend request does not exist.", failureMessage.get());
    }
}
