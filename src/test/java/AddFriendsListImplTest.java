import app.teamStory.AddFriendsListImpl;
import entities.MatchFilter;
import entities.User;
import org.junit.Test;
import usecase.teamStory.AddFriendsList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class AddFriendsListImplTest {
    @Test
    public void testAddFriends() {
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
        AddFriendsList addFriendsList = new AddFriendsListImpl();

        addFriendsList.addFriend(user0, user1);

        assertTrue(user0.getFriendList().contains(user1)); // user0 has user1 added as friend
        assertTrue(user1.getFriendList().contains(user0)); // user0 has user1 added as friend
    }
}
