package app.usecase_tests;

import app.entities.*;
import app.frameworks_and_drivers.data_access.*;
import app.frameworks_and_drivers.view.AddCommentViewInterface;
import app.frameworks_and_drivers.view.OpenPostView;
import app.interface_adapter.presenter.AddCommentPresenter;
import app.usecase.add_comment.AddCommentInteractor;
import app.usecase.add_comment.AddCommentOutputData;
import org.junit.Ignore;
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
    public void testAddFirstComment() {
        List<String> emptyList = new ArrayList<>();
        User user0 = new User("Stan", 20, "male", "Toronto", "I luv lord", emptyList, emptyList, emptyList);
        User user1 = new User("user01", 18, "female", "North York", "Let's go out!", emptyList, emptyList, emptyList);

        PostDataAccessInterface postDataAccessObject = new InMemoryPostDataAccessObject();
        UserSession userSession1 = new UserSession(user1);

        Post newPost = new Post("Check this out!", "Lord's new release is FIRE!", null,
                LocalDateTime.now(), user1, new ArrayList<>());
        postDataAccessObject.savePost(newPost);
        userSession1.addPost(newPost);

        assertTrue(newPost.getComments().isEmpty());

        UserSession userSession0 = new UserSession(user0);
        String comment = "I agree!";

        AddCommentOutputBoundary presenter = new AddCommentOutputBoundary() {
            @Override
            public void presentAddCommentSuccess(AddCommentOutputData outputData) {
                assertEquals("I agree!", outputData.getText());
                assertEquals("Stan", outputData.getAuthor());
                assertEquals("Comment added successfully.", outputData.getMessage());
                assertNotNull(outputData.getDate());
            }

            @Override
            public void presentAddCommentFailure(String message) {
                fail("Should not fail for a valid comment.");
            }
        };

        AddCommentInputBoundary interactor = new AddCommentInteractor(postDataAccessObject, presenter);
        interactor.addComment(userSession0, newPost, comment);

        List<Comment> comments = postDataAccessObject.getPostsByUser(user1).get(0).getComments();
        assertEquals(1, comments.size());
        assertEquals(user0.getName(), comments.get(0).getAuthor().getName());
    }

    @Test
    public void testAddSecondComment() {
        List<String> emptyList = new ArrayList<>();

        User user0 = new User("Stan", 20, "male", "Toronto", "I luv lord", emptyList, emptyList, emptyList);
        User user1 = new User("user01", 18, "female", "North York", "Let's go out!", emptyList, emptyList, emptyList);

        PostDataAccessInterface postDataAccessObject = new InMemoryPostDataAccessObject();
        UserSession userSession0 = new UserSession(user0);
        UserSession userSession1 = new UserSession(user1);

        Post newPost = new Post("Check this out!", "Lord's new release is FIRE!", null,
                LocalDateTime.now(), user1, new ArrayList<>());
        postDataAccessObject.savePost(newPost);
        userSession1.addPost(newPost);

        // Add the first comment directly
        newPost.getComments().add(new Comment("I agree!", user0, LocalDateTime.now()));

        AddCommentOutputBoundary presenter = new AddCommentOutputBoundary() {
            @Override
            public void presentAddCommentSuccess(AddCommentOutputData outputData) {
                assertEquals("I disagree.", outputData.getText());
                assertEquals("Stan", outputData.getAuthor());
                assertEquals("Comment added successfully.", outputData.getMessage());
                assertNotNull(outputData.getDate());
            }

            @Override
            public void presentAddCommentFailure(String message) {
                fail("Should not fail for a valid comment.");
            }
        };

        AddCommentInputBoundary interactor = new AddCommentInteractor(postDataAccessObject, presenter);
        interactor.addComment(userSession0, newPost, "I disagree.");

        List<Comment> comments = postDataAccessObject.getPostsByUser(user1).get(0).getComments();
        assertEquals(2, comments.size());
        assertEquals("I disagree.", comments.get(1).getText());
        assertEquals(user0.getName(), comments.get(1).getAuthor().getName());
    }

    @Test
    public void testEmptyCommentFails() {
        AddCommentOutputBoundary presenter = new AddCommentOutputBoundary() {
            @Override
            public void presentAddCommentSuccess(AddCommentOutputData outputData) {
                fail("Should not succeed with empty comment.");
            }

            @Override
            public void presentAddCommentFailure(String message) {
                assertEquals("Comment cannot be empty.", message);
            }
        };

        AddCommentInputBoundary interactor = new AddCommentInteractor(new InMemoryPostDataAccessObject(), presenter);
        User user = new User("u", 20, "f", "loc", "", List.of(), List.of(), List.of());
        UserSession session = new UserSession(user);
        Post post = new Post("t", "b", null, LocalDateTime.now(), user, new ArrayList<>());
        interactor.addComment(session, post, "  ");
    }

    @Test
    public void testPlaceholderCommentFails() {
        AddCommentOutputBoundary presenter = new AddCommentOutputBoundary() {
            @Override
            public void presentAddCommentSuccess(AddCommentOutputData outputData) {
                fail("Should not succeed with placeholder comment.");
            }

            @Override
            public void presentAddCommentFailure(String message) {
                assertEquals("Please enter a comment here.", message);
            }
        };

        AddCommentInputBoundary interactor = new AddCommentInteractor(new InMemoryPostDataAccessObject(), presenter);
        User user = new User("u", 20, "f", "loc", "", List.of(), List.of(), List.of());
        UserSession session = new UserSession(user);
        Post post = new Post("t", "b", null, LocalDateTime.now(), user, new ArrayList<>());
        interactor.addComment(session, post, "Enter your comment here!");
    }

    @Test
    public void testAddCommentOutputData() {
        LocalDateTime now = LocalDateTime.now();
        AddCommentOutputData output = new AddCommentOutputData("Nice post", "Alice", now, "Success!");

        assertEquals("Nice post", output.getText());
        assertEquals("Alice", output.getAuthor());
        assertEquals(now, output.getDate());
        assertEquals("Success!", output.getMessage());
    }
}
