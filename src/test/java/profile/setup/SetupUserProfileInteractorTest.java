package profile.setup;

import entities.User;
import entities.UserSession;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import usecase.teamStory.SetupUserProfileInteractor;
import usecase.teamStory.SetupUserProfileOutputBoundary;

/**
 * Tests that the SetupUserProfileInteractor correctly updates the user profile and calls the
 * presenter with the updated user profile
 */
public class SetupUserProfileInteractorTest {

    @Test
    public void testSetupUserProfile() {
        // Creates a test User with default values
        final User user =
                new User(
                        "testUser",
                        18,
                        "",
                        "",
                        "",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());

        // Initialize a UserSession with the test user
        final UserSession session = new UserSession(user);

        // Define a mock presenter that checks if the updated user values are correct
        final SetupUserProfileOutputBoundary presenter =
                updatedUser -> {
                    assertEquals("Example bio", updatedUser.getBio());
                    assertEquals(26, updatedUser.getAge());
                    assertEquals("Female", updatedUser.getGender());
                    assertEquals("Vancouver", updatedUser.getLocation());
                };

        // Create the interactor and pass in the mock presenter and session
        final SetupUserProfileInteractor interactor =
                new SetupUserProfileInteractor(presenter, session);

        // Call the use case method with new profile data
        interactor.setup("Example bio", 26, "Female", "Vancouver");

        // Check if the user in the session has been updated correctly
        assertEquals("Example bio", user.getBio());
        assertEquals(26, user.getAge());
        assertEquals("Female", user.getGender());
        assertEquals("Vancouver", user.getLocation());
    }
}
