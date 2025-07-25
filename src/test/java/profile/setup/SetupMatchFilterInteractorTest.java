package profile.setup;

import entities.MatchFilter;
import entities.User;
import entities.UserSession;
import org.junit.Test;
import usecase.team_story.SetupMatchFilterInteractor;
import usecase.team_story.SetupMatchFilterOutputBoundary;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the SetupMatchFilterInteractor correctly sets a match filter for the current user and
 * calls the presenter with the updated filter
 */
public class SetupMatchFilterInteractorTest {

    @Test
    public void testSetupMatchFilter() {
        // Create a test user and initialize a session with it
        final User testUser =
                new User(
                        "testUser",
                        22,
                        "Male",
                        "Toronto",
                        "Test bio",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        final UserSession session = new UserSession(testUser);

        // Define a presenter that will test if the filter passed to it is correct
        final SetupMatchFilterOutputBoundary presenter =
                filter -> {
                    assertEquals("20", filter.getMinAge());
                    assertEquals("30", filter.getMaxAge());
                    assertEquals("Female", filter.getPreferredGender());
                    assertEquals("Montreal", filter.getPreferredLocation());
                };

        // Create the interactor using the presenter and session
        final SetupMatchFilterInteractor interactor =
                new SetupMatchFilterInteractor(presenter, session);

        // Run the use case with sample match filter preferences
        interactor.setupFilter(20, 30, "Female", "Montreal");

        // Verify that the user's match filter was also updated correctly
        final MatchFilter filter = testUser.getMatchFilter();
        assertEquals("20", filter.getMinAge());
        assertEquals("30", filter.getMaxAge());
        assertEquals("Female", filter.getPreferredGender());
        assertEquals("Montreal", filter.getPreferredLocation());
    }
}
