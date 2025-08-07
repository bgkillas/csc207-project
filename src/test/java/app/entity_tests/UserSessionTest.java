package app.entities;

import app.frameworks_and_drivers.data_access.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionTest {

    private User user;
    private User friend;
    private Match match;
    private Post post;

    @BeforeEach
    void setUp() {
        user =
                new User(
                        "user",
                        20,
                        "other",
                        "city",
                        "bio",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        friend =
                new User(
                        "friend",
                        22,
                        "female",
                        "another city",
                        "music lover",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        match = new Match(user, friend);
        post = new Post("1", "hello world", null, LocalDateTime.now(), user, new ArrayList<>());
    }

    @Test
    void testConstructor() {
        UserSession session = new UserSession(user);

        assertEquals(user, session.getUser());
        // assertTrue(session.getIncomingMatches().isEmpty());
        assertTrue(session.getOutgoingMatches().isEmpty());
        assertTrue(session.getMatches().isEmpty());
        assertTrue(session.getPosts().isEmpty());

        //        assertEquals(2, session.getAllUsers().size());
    }

    @Test
    void testAddAndGetUsers() {
        UserSession session = new UserSession(user);
        session.addUser(friend);

        // assertEquals(6, session.getAllUsers().size()); // TODO do to extra example users
        assertTrue(session.getAllUsers().contains(friend));
    }

    @Test
    void testIncomingAndOutgoingMatches() {
        UserSession session = new UserSession(user);

        session.addIncomingMatch(friend);
        session.addOutgoingMatch(friend);

        assertTrue(session.getIncomingMatches().contains(friend));
        assertTrue(session.getOutgoingMatches().contains(friend));
    }

    @Test
    void testAddAndGetMatches() {
        UserSession session = new UserSession(user);
        session.addMatch(match);

        assertEquals(1, session.getMatches().size());
        assertTrue(session.getMatches().contains(match));
    }

    @Test
    void testAddAndGetPosts() {
        UserSession session = new UserSession(user);
        session.addPost(post);

        assertEquals(1, session.getPosts().size());
        assertTrue(session.getPosts().contains(post));
    }

    @Test
    void testSetPosts() {
        UserSession session = new UserSession(user);
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        session.setPosts(posts);

        assertEquals(posts, session.getPosts());
    }

    @Test
    void testSetUser() {
        User anotherUser =
                new User(
                        "another",
                        25,
                        "male",
                        "town",
                        "bio2",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        UserSession session = new UserSession();

        session.setUser(anotherUser);
        assertEquals(anotherUser, session.getUser());
    }
}
