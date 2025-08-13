package app.entity_tests;

import app.entities.Post;
import app.entities.User;
import app.entities.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
    private User testUser;
    private LocalDateTime testTimestamp;
    private List<Comment> testComments;
    private Image testImage;

    @BeforeEach
    void setUp() {
        // Create test user
        List<String> favGenres = new ArrayList<>();
        favGenres.add("Rock");
        favGenres.add("Pop");

        List<String> favArtists = new ArrayList<>();
        favArtists.add("Coldplay");

        List<String> favSongs = new ArrayList<>();
        favSongs.add("The Scientist");

        testUser =
                new User(
                        "John Doe",
                        25,
                        "Male",
                        "Toronto",
                        "Music lover",
                        favGenres,
                        favArtists,
                        favSongs);

        testTimestamp = LocalDateTime.of(2025, 12, 25, 10, 30);

        testComments = new ArrayList<>();
        User katy = new User("Katy", 20, null, null, null, null, null, null);
        User george = new User("George", 20, null, null, null, null, null, null);
        testComments.add(new Comment("Great post!", katy, LocalDateTime.now()));
        testComments.add(new Comment("I agree!", george, LocalDateTime.now()));

        // test image
        testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    void testConstructorWithAllParameters() {
        Post post =
                new Post(
                        "Test Title",
                        "Test Content",
                        testImage,
                        testTimestamp,
                        testUser,
                        testComments);

        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getText());
        assertEquals(testImage, post.getImage());
        assertEquals(testTimestamp, post.getTimestamp());
        assertEquals(testUser, post.getAuthor());
        assertEquals(testComments, post.getComments());
    }

    @Test
    void testDefaultConstructor() {
        Post post = new Post();

        assertEquals("Untitled", post.getTitle());
        assertEquals("", post.getText());
        assertNull(post.getImage());
        assertNotNull(post.getTimestamp()); // Should be current time
        assertNull(post.getAuthor());
        assertNotNull(post.getComments());
        assertTrue(post.getComments().isEmpty());
    }

    @Test
    void testGetTitle() {
        Post post =
                new Post("My Post", "Content", null, testTimestamp, testUser, new ArrayList<>());
        assertEquals("My Post", post.getTitle());
    }

    @Test
    void testGetText() {
        Post post =
                new Post(
                        "Title",
                        "This is the post content",
                        null,
                        testTimestamp,
                        testUser,
                        new ArrayList<>());
        assertEquals("This is the post content", post.getText());
    }

    @Test
    void testGetImage() {
        Post post =
                new Post("Title", "Content", testImage, testTimestamp, testUser, new ArrayList<>());
        assertEquals(testImage, post.getImage());
    }

    @Test
    void testGetImageWhenNull() {
        Post post = new Post("Title", "Content", null, testTimestamp, testUser, new ArrayList<>());
        assertNull(post.getImage());
    }

    @Test
    void testGetAuthor() {
        Post post = new Post("Title", "Content", null, testTimestamp, testUser, new ArrayList<>());
        assertEquals(testUser, post.getAuthor());
    }

    @Test
    void testGetComments() {
        Post post = new Post("Title", "Content", null, testTimestamp, testUser, testComments);
        assertEquals(testComments, post.getComments());
    }

    @Test
    void testSetComments() {
        User charlie = new User("Charlie", 20, null, null, null, null, null, null);

        Post post = new Post("Title", "Content", null, testTimestamp, testUser, new ArrayList<>());
        List<Comment> newComments = new ArrayList<>();
        newComments.add(new Comment("New comment", charlie, LocalDateTime.now()));

        post.setComments(newComments);
        assertEquals(newComments, post.getComments());
    }

    @Test
    void testSetCommentsWithNull() {
        Post post = new Post("Title", "Content", null, testTimestamp, testUser, testComments);
        post.setComments(null);
        assertNull(post.getComments());
    }

    @Test
    void testPostWithEmptyTitle() {
        Post post = new Post("", "Content", null, testTimestamp, testUser, new ArrayList<>());
        assertEquals("", post.getTitle());
    }

    @Test
    void testPostWithNullTitle() {
        Post post = new Post(null, "Content", null, testTimestamp, testUser, new ArrayList<>());
        assertNull(post.getTitle());
    }

    @Test
    void testPostWithEmptyText() {
        Post post = new Post("Title", "", null, testTimestamp, testUser, new ArrayList<>());
        assertEquals("", post.getText());
    }

    @Test
    void testPostWithNullText() {
        Post post = new Post("Title", null, null, testTimestamp, testUser, new ArrayList<>());
        assertNull(post.getText());
    }

    @Test
    void testPostWithNullAuthor() {
        Post post = new Post("Title", "Content", null, testTimestamp, null, new ArrayList<>());
        assertNull(post.getAuthor());
    }

    @Test
    void testPostWithNullTimestamp() {
        Post post = new Post("Title", "Content", null, null, testUser, new ArrayList<>());
        assertNull(post.getTimestamp());
    }

    @Test
    void testPostWithEmptyCommentsList() {
        Post post = new Post("Title", "Content", null, testTimestamp, testUser, new ArrayList<>());
        assertNotNull(post.getComments());
        assertTrue(post.getComments().isEmpty());
    }

    @Test
    void testPostWithNullCommentsList() {
        Post post = new Post("Title", "Content", null, testTimestamp, testUser, null);
        assertNull(post.getComments());
    }

    @Test
    void testMultiplePostsWithSameData() {
        Post post1 = new Post("Title", "Content", testImage, testTimestamp, testUser, testComments);
        Post post2 = new Post("Title", "Content", testImage, testTimestamp, testUser, testComments);

        assertEquals(post1.getTitle(), post2.getTitle());
        assertEquals(post1.getText(), post2.getText());
        assertEquals(post1.getImage(), post2.getImage());
        assertEquals(post1.getTimestamp(), post2.getTimestamp());
        assertEquals(post1.getAuthor(), post2.getAuthor());
        assertEquals(post1.getComments(), post2.getComments());
    }

    @Test
    void testPostWithLongText() {
        String longText =
                "This is a very long post content ..................................."
                    + " ...................................................................... "
                    + "........................................................................";

        Post post =
                new Post("Long Post", longText, null, testTimestamp, testUser, new ArrayList<>());
        assertEquals(longText, post.getText());
    }

    @Test
    void testPostWithSpecialCharacters() {
        String specialText = "Post with special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?";
        Post post =
                new Post(
                        "Special Characters Post",
                        specialText,
                        null,
                        testTimestamp,
                        testUser,
                        new ArrayList<>());
        assertEquals(specialText, post.getText());
    }

    @Test
    void testGetFilteredComments() {
        User blockedUser = new User("Blocked", 20, null, null, null, null, null, null);
        Comment blockedComment =
                new Comment(
                        "This is a comment of a blocked user.", blockedUser, LocalDateTime.now());

        Post post =
                new Post(
                        "Title", "Content", null, testTimestamp, testUser, List.of(blockedComment));

        assertTrue(post.getFilteredComments(testUser).contains(blockedComment));

        // Now this user is blocked for Filtered Comment doesn't include this comment anymore.
        testUser.addBlock(blockedUser);
        assertFalse(post.getFilteredComments(testUser).contains(blockedComment));
    }
}
