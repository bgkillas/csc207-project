package app.usecase_tests;

import app.frameworks_and_drivers.data_access.*;
import app.interface_adapter.presentor.FriendRequestPresenter;
import app.interface_adapter.presentor.MatchingRoomPresenter;
import app.usecase.handle_friend_request.HandleFriendRequestInteractor;
import app.entities.MatchFilter;
import app.entities.User;
import app.entities.UserSession;
import app.interface_adapter.presentor.AddFriendListPresenter;
import org.junit.Test;
import app.usecase.handle_friend_request.HandleFriendRequestInputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestOutputBoundary;
import app.usecase.add_friend_list.AddFriendListInputBoundary;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.usecase.add_friend_list.AddFriendListOutputBoundary;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandleFriendRequestInteractorTest {
    @Test
    public void testHandleFriendRequest() {
        List<String> emptyList = new ArrayList<String>();
        MatchFilter generalMF = new MatchFilter(0, 100, null, null);

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

        // Senario: user0 has been matched with user1 in his matching room and clicks "connect"
        // button
        // which sends friend request to user1. User1 finds this in her Friend Request tab and
        // clicks "accept"

        UserDataAccessInterface userDAO = new InMemoryUserDataAccessObject();
        MatchDataAccessInterface matchDAO = new InMemoryMatchDataAccessObject();
        PostDataAccessInterface postDAO = new InMemoryPostDataAccessObject();

        // user1 logs in to the app and checks her incoming friend request list.
        UserSession userSession1 = new UserSession(user1, userDAO, matchDAO, postDAO);

        List<User> incoming = userSession1.getIncomingMatches();
        System.out.print("User1's Incoming(before): ");
        for (User user : incoming) {
            System.out.print(user.getName() + " ");
        }
        // She notices no one requested her to be friends and go cry in her bed.
        System.out.println();

        // user0 logs in to the app.
        UserSession userSession0 = new UserSession(user0, userDAO, matchDAO, postDAO);

        // Initially, user0's userSession has empty incomingFriendRequest / outgoingFriendRequest
        assertTrue(userSession0.getIncomingMatches().isEmpty());
        assertTrue(userSession0.getOutgoingMatches().isEmpty());

        // user0 has been matched with user1 in his matching room and clicks "connect" button
        // which sends friend request to user1
        HandleFriendRequestOutputBoundary presenter = new MatchingRoomPresenter();
        AddFriendListOutputBoundary presenter2 = new AddFriendListPresenter();
        AddFriendListInputBoundary interactor2 = new AddFriendListInteractor(presenter2);
        HandleFriendRequestInputBoundary handleFriendRequestInputBoundary =
                new HandleFriendRequestInteractor(matchDAO, interactor2, presenter);
        handleFriendRequestInputBoundary.sendFriendRequest(userSession0, user1);

        assertTrue(
                userSession0
                        .getOutgoingMatches()
                        .contains(user1)); // user1 is added into the outgoing Match

        // user1 logs in again.
        userSession1 = new UserSession(user1, userDAO, matchDAO, postDAO);

        // since matchDAO has been updated with the new friend request,
        // user1 can see her session updated with new friend request!
        assertTrue(userSession1.getIncomingMatches().contains(user0));

        // Prints out the incoming friend request
        incoming = userSession1.getIncomingMatches();
        System.out.print("User1's Incoming(after): ");
        for (User user : incoming) {
            System.out.print(user.getName() + " ");
        }

        // user1 looks at user0's profile and finds interest. She clicks accept.
        HandleFriendRequestOutputBoundary presenter3 = new FriendRequestPresenter();
        HandleFriendRequestInputBoundary handleFriendRequestInputBoundary2 =
                new HandleFriendRequestInteractor(matchDAO, interactor2, presenter3);
        handleFriendRequestInputBoundary2.acceptFriendRequest(userSession1, user0);

        // user0's request has been accepted, so user0 is no longer in the request list
        assertFalse(userSession1.getIncomingMatches().contains(user0));

        // They've been added into each other's friends list
        assertTrue(user1.getFriendList().contains(user0));
        assertTrue(user0.getFriendList().contains(user1));
    }
}
