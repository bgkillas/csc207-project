package app.entity_tests;

import app.entities.MatchFilter;
import app.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private MatchFilter defaultFilter;

    @BeforeEach
    void setUp() {
        user = new User(
                "testuser",
                20,
                "female",
                "Toronto",
                "I love music!",
                new ArrayList<>(List.of("pop", "rock")),
                new ArrayList<>(List.of("Taylor Swift", "Drake")),
                new ArrayList<>(List.of("Shake It Off", "Hotline Bling"))
        );
        defaultFilter = new MatchFilter(0, 100, "N/A", "N/A");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("testuser", user.getName());
        assertEquals(20, user.getAge());
        assertEquals("female", user.getGender());
        assertEquals("Toronto", user.getLocation());
        assertEquals("I love music!", user.getBio());

        assertEquals(List.of("pop", "rock"), user.getFavGenres());
        assertEquals(List.of("Taylor Swift", "Drake"), user.getFavArtists());
        assertEquals(List.of("Shake It Off", "Hotline Bling"), user.getFavSongs());

        assertEquals(defaultFilter.getMinAge(), user.getMatchFilter().getMinAge());
        assertEquals(defaultFilter.getMaxAge(), user.getMatchFilter().getMaxAge());
        assertEquals(defaultFilter.getPreferredGender(), user.getMatchFilter().getPreferredGender());
        assertEquals(defaultFilter.getPreferredLocation(), user.getMatchFilter().getPreferredLocation());

        assertTrue(user.getFriendList().isEmpty());
    }

    @Test
    void testSetters() {
        user.setAge(25);
        assertEquals(25, user.getAge());

        user.setGender("non-binary");
        assertEquals("non-binary", user.getGender());

        user.setLocation("Vancouver");
        assertEquals("Vancouver", user.getLocation());

        user.setBio("New bio");
        assertEquals("New bio", user.getBio());

        user.setFavGenres(List.of("jazz"));
        assertEquals(List.of("jazz"), user.getFavGenres());

        user.setFavArtists(List.of("Adele"));
        assertEquals(List.of("Adele"), user.getFavArtists());

        user.setFavSongs(List.of("Hello"));
        assertEquals(List.of("Hello"), user.getFavSongs());
    }

    @Test
    void testSetAndGetMatchFilter() {
        MatchFilter newFilter = new MatchFilter(18, 30, "female", "Montreal");
        user.setMatchFilter(newFilter);
        assertEquals(newFilter, user.getMatchFilter());
    }

    @Test
    void testAddFriend() {
        User friend = new User("bob", 22, "male", "NY", "", List.of(), List.of(), List.of());

        user.addFriend(friend);
        assertTrue(user.getFriendList().contains(friend));
    }

    @Test
    void testAddSelfOrNullFriendDoesNothing() {
        user.addFriend(user); // should do nothing
        user.addFriend(null); // should do nothing
        assertTrue(user.getFriendList().isEmpty());
    }

    @Test
    void testAddDuplicateFriend() {
        User friend = new User("bob", 22, "male", "NY", "", List.of(), List.of(), List.of());
        user.addFriend(friend);
        user.addFriend(friend);
        assertEquals(1, user.getFriendList().size());
    }

    @Test
    void testPushFriendList() {
        User friend = new User("alice", 23, "female", "Paris", "", List.of(), List.of(), List.of());
        user.addFriend(friend);
        assertTrue(user.getFriendList().contains(friend));
    }
}
