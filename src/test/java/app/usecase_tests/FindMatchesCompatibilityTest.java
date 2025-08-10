package app.usecase_tests;

import app.entities.User;
import app.usecase.matching.FindMatchesInteractor;
import app.usecase.matching.FindMatchesOutputBoundary;
import app.usecase.matching.FindMatchesRequestModel;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FindMatchesCompatibilityTest {
    @Test
    public void testCalculateCompatibilityScore_highScore() {
        User user1 =
                new User(
                        "Kim",
                        25,
                        "Female",
                        "Toronto",
                        "Bio",
                        Arrays.asList("Pop", "Rock", "Jazz"),
                        Arrays.asList(
                                "Artist1",
                                "Artist2",
                                "Artist3",
                                "Artist4",
                                "Artist5",
                                "Artist6",
                                "Artist7",
                                "Artist8",
                                "Artist9",
                                "Artist10"),
                        Arrays.asList());
        User user2 =
                new User(
                        "John",
                        26,
                        "Male",
                        "Toronto",
                        "Bio",
                        Arrays.asList("Pop", "Rock", "Jazz"),
                        Arrays.asList(
                                "Artist1",
                                "Artist2",
                                "Artist3",
                                "Artist4",
                                "Artist5",
                                "Artist6",
                                "Artist7",
                                "Artist8",
                                "Artist9",
                                "Artist10"),
                        Arrays.asList());
        // With high similarity, they should end up matched by the interactor
        AtomicReference<List<User>> matchesRef = new AtomicReference<>();
        FindMatchesOutputBoundary presenter =
                responseModel -> matchesRef.set(responseModel.getMatches());
        new FindMatchesInteractor(presenter)
                .findMatches(new FindMatchesRequestModel(user1, Arrays.asList(user1, user2)));
        assertTrue(matchesRef.get().contains(user2));
    }

    @Test
    public void testIsCompatible_true() {
        // Same as above: verify they match
        testCalculateCompatibilityScore_highScore();
    }

    @Test
    public void testIsCompatible_false() {
        User user1 =
                new User(
                        "Kim",
                        25,
                        "Female",
                        "Toronto",
                        "Bio",
                        Arrays.asList("Pop", "Rock", "Jazz"),
                        Arrays.asList(
                                "Artist1",
                                "Artist2",
                                "Artist3",
                                "Artist4",
                                "Artist5",
                                "Artist6",
                                "Artist7",
                                "Artist8",
                                "Artist9",
                                "Artist10"),
                        Arrays.asList());
        User user2 =
                new User(
                        "John",
                        26,
                        "Male",
                        "Toronto",
                        "Bio",
                        Arrays.asList("Classical", "EDM", "Country"),
                        Arrays.asList(
                                "Artist11",
                                "Artist12",
                                "Artist13",
                                "Artist14",
                                "Artist15",
                                "Artist16",
                                "Artist17",
                                "Artist18",
                                "Artist19",
                                "Artist20"),
                        Arrays.asList());
        AtomicReference<List<User>> matchesRef = new AtomicReference<>();
        FindMatchesOutputBoundary presenter =
                responseModel -> matchesRef.set(responseModel.getMatches());
        new FindMatchesInteractor(presenter)
                .findMatches(new FindMatchesRequestModel(user1, Arrays.asList(user1, user2)));
        assertFalse(matchesRef.get().contains(user2));
    }
}
