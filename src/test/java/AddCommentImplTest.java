import app.addComment.AddCommentImpl;
import data_access.InMemoryMatchDataAccessObject;
import data_access.InMemoryPostDataAccessObject;
import data_access.MatchDataAccessInterface;
import data_access.PostDataAccessInterface;
import entities.*;
import org.junit.Test;
import usecase.AddComment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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


        // Scenario: Stan wants to make a comment on user01's post

        MatchDataAccessInterface matchDAO = new InMemoryMatchDataAccessObject();
        PostDataAccessInterface postDAO = new InMemoryPostDataAccessObject();

        // user1 logs in to the app and posts something
        UserSession userSession1 = new UserSession(user1, matchDAO, postDAO);

        Post newPost = new Post("Check this out!", "Lord's new release is FIRE!", null,
                LocalDateTime.now(), user1, new ArrayList<Comment>());
        postDAO.savePost(user1, newPost);      // DAO updates
        userSession1.addPost(newPost);         // Session updates

        // this post has no comment yet
        assertTrue(newPost.getComments().isEmpty());

        // user0 (Stan) logs in to the app and finds the new post in his post feed.
        UserSession userSession0 = new UserSession(user0);

        // Stan makes a comment on user1's post
        String comment = "I agree!";
        AddComment addcomment = new AddCommentImpl(postDAO);
        addcomment.addComment(userSession0, newPost, comment);

        // Check that comment is stored with the post in DAO
        assertFalse(postDAO.getPostsByUser(user1).get(0).getComments().isEmpty());
        assertEquals(postDAO.getPostsByUser(user1).get(0).getComments().get(0).getAuthor(), user0.getName());

    }
}
