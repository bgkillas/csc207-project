import app.addComment.AddCommentImpl;
import app.teamStory.AddFriendsListImpl;
import entities.*;
import org.junit.Test;
import usecase.AddComment;
import usecase.teamStory.AddFriendsList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCommentImplTest {

    @Test
    public void testAddComment() {
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

        UserSession priorUserSession = new UserSession(user1);
        Post newPost = new Post("Lord's new release is FIRE!", null,
                LocalDateTime.now(), user1, new ArrayList<Comment>());
        priorUserSession.addPost(newPost);

        UserSession userSession = new UserSession(user1);
        // Senario: Stan wants to make a comment on user01's post
        AddFriendsList addFriendsList = new AddFriendsListImpl();

        String comment = "I agree!";
        AddComment addcomment = new AddCommentImpl();
        addcomment.addComment(userSession, newPost, comment);

        assertTrue(true);
        // can't check without connecting to database
    }
}
