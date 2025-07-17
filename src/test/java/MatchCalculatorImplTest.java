import app.teamStory.MatchCalculatorImpl;
import entities.User;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MatchCalculatorImplTest {
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
        MatchCalculatorImpl calc = new MatchCalculatorImpl();
        int score = calc.calculateCompatibilityScore(user1, user2);
        assertTrue(score > 85);
    }

    @Test
    public void testIsCompatible_true() {
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
        MatchCalculatorImpl calc = new MatchCalculatorImpl();
        assertTrue(calc.isCompatible(user1, user2));
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
        MatchCalculatorImpl calc = new MatchCalculatorImpl();
        assertFalse(calc.isCompatible(user1, user2));
    }
}
