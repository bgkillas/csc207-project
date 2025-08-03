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
        user =
                new User(
                        "testuser",
                        20,
                        "female",
                        "Toronto",
                        "I love music!",
                        new ArrayList<>(List.of("pop", "rock")),
                        new ArrayList<>(List.of("Taylor Swift", "Drake")),
                        new ArrayList<>(List.of("Shake It Off", "Hotline Bling")));
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
        assertEquals(
                defaultFilter.getPreferredGender(), user.getMatchFilter().getPreferredGender());
        assertEquals(
                defaultFilter.getPreferredLocation(), user.getMatchFilter().getPreferredLocation());

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

    @Test
    void testAddBlockAlsoRemovesFriend() {
        User friend = new User("alice", 22, "female", "Toronto", "", List.of(), List.of(), List.of());
        user.addFriend(friend);
        assertTrue(user.getFriendList().contains(friend));

        user.addBlock(friend);
        assertTrue(user.getBlockList().contains(friend));
        assertFalse(user.getFriendList().contains(friend));
    }

    @Test
    void testBlockSelfDoesNothing() {
        user.addBlock(user);
        assertTrue(user.getBlockList().isEmpty());
    }

    @Test
    void testBlockNullDoesNothing() {
        user.addBlock(null);
        assertTrue(user.getBlockList().isEmpty());
    }

    @Test
    void testAddDuplicateBlock() {
        User bob = new User("bob", 22, "male", "NY", "", List.of(), List.of(), List.of());
        user.addBlock(bob);
        assertEquals(1, user.getBlockList().size());

        user.addBlock(bob); // 重复 block
        assertEquals(1, user.getBlockList().size());
    }



    @Test
    void testRemoveBlock() {
        User blocked = new User("bob", 22, "male", "NY", "", List.of(), List.of(), List.of());
        user.addBlock(blocked);
        assertTrue(user.getBlockList().contains(blocked));

        user.removeBlock(blocked);
        assertFalse(user.getBlockList().contains(blocked));
    }

    @Test
    void testRemoveBlockWithNullAndSelf() {
        user.removeBlock(null);
        user.removeBlock(user);
    }

    @Test
    void testHasBlock() {
        User blocked = new User("bob", 22, "male", "NY", "", List.of(), List.of(), List.of());
        user.addBlock(blocked);
        assertTrue(user.hasBlock(blocked));
    }

    @Test
    void testProfilePictureSetAndGet() {
        java.awt.Image dummyImage = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_RGB);
        user.setProfilePicture(dummyImage);
        assertEquals(dummyImage, user.getProfilePicture());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User("x", 20, "f", "toronto", "", List.of(), List.of(), List.of());
        User user2 = new User("x", 20, "f", "toronto", "", List.of(), List.of(), List.of());

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
        assertEquals(user1, user1);
    }

    @Test
    void testEqualsNullReturnsFalse() {
        assertNotEquals(user, null);
    }

    @Test
    void testEqualsDifferentClassReturnsFalse() {
        assertNotEquals("not a user", user);
    }
    @Test
    void testEqualsSameClassDifferentInstance() {
        User user1 = new User("a", 20, "f", "toronto", "", List.of(), List.of(), List.of());
        User user2 = new User("b", 22, "f", "toronto", "", List.of(), List.of(), List.of());
        assertNotEquals(user1, user2);
    }

    @Test
    void testAddSelfAsFriendDoesNothing() {
        user.addFriend(user);
        assertTrue(user.getFriendList().isEmpty());
    }

}
