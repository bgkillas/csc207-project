import app.teamStory.HandleFriendRequestImpl;
import data_access.InMemoryMatchDataAccessObject;
import data_access.InMemoryPostDataAccessObject;
import data_access.MatchDataAccessInterface;
import data_access.PostDataAccessInterface;
import entities.MatchFilter;
import entities.User;
import entities.UserSession;
import org.junit.Test;
import usecase.teamStory.HandleFriendRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandleFriendRequestImplTest {
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



        // Senario: user0 has been matched with user1 in his matching room and clicks "connect" button
        // which sends friend request to user1. User1 finds this in her Friend Request tab and clicks "accept"

        MatchDataAccessInterface matchDAO = new InMemoryMatchDataAccessObject();
        PostDataAccessInterface postDAO = new InMemoryPostDataAccessObject();

        // user1 logs in to the app and checks her incoming friend request list.
        UserSession userSession1 = new UserSession(user1, matchDAO, postDAO);

        List<User> incoming = userSession1.getIncomingMatches();
        System.out.print("User1's Incoming(before): ");
        for (User user : incoming) {
            System.out.print(user.getName() + " ");
        }
        // She notices no one requested her to be friends and go cry in her bed.
        System.out.println();

        // user0 logs in to the app.
        UserSession userSession0 = new UserSession(user0, matchDAO, postDAO);

        // Initially, user0's userSession has empty incomingFriendRequest / outgoingFriendRequest
        assertTrue(userSession0.getIncomingMatches().isEmpty());
        assertTrue(userSession0.getOutgoingMatches().isEmpty());

        // user0 has been matched with user1 in his matching room and clicks "connect" button
        // which sends friend request to user1
        HandleFriendRequest handleFriendRequest = new HandleFriendRequestImpl(matchDAO);
        handleFriendRequest.sendFriendRequest(userSession0, user1);

        assertTrue(userSession0.getOutgoingMatches().contains(user1)); // user1 is added into the outgoing Match

        // user1 logs in again.
        userSession1 = new UserSession(user1, matchDAO, postDAO);

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
        HandleFriendRequest handleFriendRequest2 = new HandleFriendRequestImpl(matchDAO);
        handleFriendRequest2.acceptFriendRequest(userSession1, user0);

        // user0's request has been accepted, so user0 is no longer in the request list
        assertFalse(userSession1.getIncomingMatches().contains(user0));

        // They've been added into each other's friends list
        assertTrue(user1.getFriendList().contains(user0));
        assertTrue(user0.getFriendList().contains(user1));
    }
}
