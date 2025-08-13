package app.entity_tests;

import app.entities.Match;
import app.entities.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

    @Test
    public void testConstructorAndGetters() {
        User userA =
                new User(
                        "Sally",
                        25,
                        "Female",
                        "New York City",
                        "Bio",
                        List.of(),
                        List.of(),
                        List.of());
        User userB =
                new User("George", 30, "Male", "Edmonton", "Bio", List.of(), List.of(), List.of());
        List<String> sharedArtists = List.of("Artist1", "Artist2");
        int score = 90;
        Match match = new Match(userB, score, sharedArtists);

        assertEquals(userB, match.getMatchUser());
        assertEquals(score, match.getCompatibilityScore());
        assertEquals(sharedArtists, match.getSharedArtists());
        assertNotNull(match);
    }

    @Test
    public void testAlternateConstructor() {
        List<String> artistsA = List.of("Artist1", "Artist2", "Artist3");
        List<String> artistsB = List.of("Artist2", "Artist3", "Artist4");
        User userA = new User("Sally", 25, "Female", "NYC", "Bio", List.of(), artistsA, List.of());
        User userB = new User("George", 30, "Male", "LA", "Bio", List.of(), artistsB, List.of());
        Match match = new Match(userA, userB);

        assertEquals(userB, match.getMatchUser());
        // Expected score computed based on shared artists positions (no genres provided)
        // Shared: Artist2 (i=1, j=0) => (3-1) + (3-0) = 5; Artist3 (i=2, j=1) => (3-2)+(3-1)=3;
        // Total=8
        int expectedScore = 8;
        assertEquals(expectedScore, match.getCompatibilityScore());
        List<String> expectedShared = List.of("Artist2", "Artist3");
        assertEquals(expectedShared, match.getSharedArtists());
    }

    @Test
    public void testUniqueMatchId() {
        User userA = new User("Sally", 25, "Female", "NYC", "Bio", List.of(), List.of(), List.of());
        User userB = new User("George", 30, "Male", "LA", "Bio", List.of(), List.of(), List.of());
        Match match1 = new Match(userB, 80, List.of());
        Match match2 = new Match(userB, 80, List.of());
        assertNotEquals(match1, match2); // Different instances
    }
}
