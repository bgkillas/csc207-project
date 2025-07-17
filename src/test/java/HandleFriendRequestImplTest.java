import app.teamStory.HandleFriendRequestImpl;
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

        // Senario: user1 has been matched with user0 in her matching room and clicks "connect"
        // button
        // which sends match request to user0. User0 finds this in his Match Request tab and also
        // clicks "connect"

        HandleFriendRequest handleFriendRequest = new HandleFriendRequestImpl();

        UserSession userSession = new UserSession(user0);

        assertTrue(
                userSession
                        .getIncomingMatches()
                        .isEmpty()); // user0's userSession has empty incoming matches
        assertTrue(
                userSession
                        .getOutgoingMatches()
                        .isEmpty()); // user0's userSession has empty outgoing matches

        handleFriendRequest.sendFriendRequest(userSession, user1);

        assertTrue(userSession.getOutgoingMatches().contains(user1)); // added into outgoing Match
        UserSession userSession2 = new UserSession(user1);

        assertFalse(userSession2.getIncomingMatches().contains(user0));
        // should not work yet since no connection to repo/database

    }
}
