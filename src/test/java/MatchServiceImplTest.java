import app.teamStory.MatchServiceImpl;
import entities.User;
import entities.MatchFilter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;

public class MatchServiceImplTest {
    @Test
    public void testFindMatches_mutualMatch() {
        User user1 = new User("Kim", 25, "Female", "Toronto", "Bio",
                Arrays.asList("Pop", "Rock", "Jazz"),
                Arrays.asList("Artist1", "Artist2", "Artist3", "Artist4", "Artist5", "Artist6", "Artist7", "Artist8", "Artist9", "Artist10"),
                Arrays.asList());
        User user2 = new User("John", 26, "Male", "Toronto", "Bio",
                Arrays.asList("Pop", "Rock", "Jazz"),
                Arrays.asList("Artist1", "Artist2", "Artist3", "Artist4", "Artist5", "Artist6", "Artist7", "Artist8", "Artist9", "Artist10"),
                Arrays.asList());
        user1.setMatchFilter(new MatchFilter(20, 30, "Any", "Any"));
        user2.setMatchFilter(new MatchFilter(20, 30, "Any", "Any"));
        MatchServiceImpl matchService = new MatchServiceImpl();
        List<User> matches = matchService.findMatches(user1, Arrays.asList(user1, user2));
        assertEquals(1, matches.size());
        assertEquals("John", matches.get(0).getName());
    }

    @Test
    public void testFindMatches_noMatch() {
        User user1 = new User("Kim", 25, "Female", "Toronto", "Bio",
                Arrays.asList("Pop", "Rock", "Jazz"),
                Arrays.asList("Artist1", "Artist2", "Artist3", "Artist4", "Artist5", "Artist6", "Artist7", "Artist8", "Artist9", "Artist10"),
                Arrays.asList());
        User user2 = new User("John", 26, "Male", "Toronto", "Bio",
                Arrays.asList("Classical", "EDM", "Country"),
                Arrays.asList("Artist11", "Artist12", "Artist13", "Artist14", "Artist15", "Artist16", "Artist17", "Artist18", "Artist19", "Artist20"),
                Arrays.asList());
        user1.setMatchFilter(new MatchFilter(20, 30, "Male", "Toronto"));
        user2.setMatchFilter(new MatchFilter(20, 30, "Female", "Toronto"));
        MatchServiceImpl matchService = new MatchServiceImpl();
        List<User> matches = matchService.findMatches(user1, Arrays.asList(user1, user2));
        assertEquals(0, matches.size());
    }
}
