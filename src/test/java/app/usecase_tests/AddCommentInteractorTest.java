package app.usecase_tests;

import app.entities.*;
import app.frameworks_and_drivers.data_access.*;
import app.frameworks_and_drivers.view.AddCommentViewInterface;
import app.frameworks_and_drivers.view.OpenPostView;
import app.interface_adapter.presenter.AddCommentPresenter;
import app.usecase.add_comment.AddCommentInteractor;
import org.junit.Test;
import app.usecase.add_comment.AddCommentInputBoundary;
import app.usecase.add_comment.AddCommentOutputBoundary;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddCommentInteractorTest {

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

        PostDataAccessInterface postDataAccessObject = new InMemoryPostDataAccessObject();

        // user1 logs in to the app and posts something
        UserSession userSession1 = new UserSession(user1);

        Post newPost =
                new Post(
                        "Check this out!",
                        "Lord's new release is FIRE!",
                        null,
                        LocalDateTime.now(),
                        user1,
                        new ArrayList<>());
        postDataAccessObject.savePost(newPost); // DataAccessObject updates
        userSession1.addPost(newPost); // Session updates

        // this post has no comment yet
        assertTrue(newPost.getComments().isEmpty());

        // Stan (user0) logs in to the app and opens that specific post in his post feed.
        UserSession userSession0 = new UserSession(user0);

        // Stan can see the OpenPostView
        AddCommentViewInterface view = new OpenPostView(user0, userSession0, new JFrame());

        // Stan makes a comment on user1's post
        String comment = "I agree!";
        AddCommentOutputBoundary presenter = new AddCommentPresenter(view);
        AddCommentInputBoundary interactor =
                new AddCommentInteractor(postDataAccessObject, presenter);
        interactor.addComment(userSession0, newPost, comment);

        // Check that comment is stored with the post in DataAccessObject
        assertFalse(postDataAccessObject.getPostsByUser(user1).get(0).getComments().isEmpty());
        assertEquals(
                postDataAccessObject.getPostsByUser(user1).get(0).getComments().get(0).getAuthor(),
                user0.getName());

        String comment2 = "I disagree.";
        interactor.addComment(userSession0, newPost, comment2);

        assertEquals(2, postDataAccessObject.getPostsByUser(user1).get(0).getComments().size());
    }
}
