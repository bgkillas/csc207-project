package app.usecase_tests;

import app.entities.User;
import app.entities.MatchFilter;
import app.usecase.matching.FindMatchesInteractor;
import app.usecase.matching.FindMatchesOutputBoundary;
import app.usecase.matching.FindMatchesRequestModel;
import app.usecase.matching.FindMatchesResponseModel;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class FindMatchesInteractorTest {
    @Test
    public void testFindMatches_mutualMatch() {
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
        user1.setMatchFilter(new MatchFilter(20, 30, "Any", "Any"));
        user2.setMatchFilter(new MatchFilter(20, 30, "Any", "Any"));

        AtomicReference<List<User>> matchesRef = new AtomicReference<>();
        FindMatchesOutputBoundary presenter =
                new FindMatchesOutputBoundary() {
                    @Override
                    public void present(FindMatchesResponseModel responseModel) {
                        matchesRef.set(responseModel.getMatches());
                    }
                };
        new FindMatchesInteractor(presenter)
                .findMatches(new FindMatchesRequestModel(user1, Arrays.asList(user1, user2)));
        List<User> matches = matchesRef.get();

        assertEquals(1, matches.size());
        assertEquals("John", matches.get(0).getName());
    }

    @Test
    public void testFindMatches_noMatch() {
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
        user1.setMatchFilter(new MatchFilter(20, 30, "Male", "Toronto"));
        user2.setMatchFilter(new MatchFilter(20, 30, "Female", "Toronto"));

        AtomicReference<List<User>> matchesRef = new AtomicReference<>();
        FindMatchesOutputBoundary presenter =
                new FindMatchesOutputBoundary() {
                    @Override
                    public void present(FindMatchesResponseModel responseModel) {
                        matchesRef.set(responseModel.getMatches());
                    }
                };
        new FindMatchesInteractor(presenter)
                .findMatches(new FindMatchesRequestModel(user1, Arrays.asList(user1, user2)));
        List<User> matches = matchesRef.get();

        assertEquals(0, matches.size());
    }
}
