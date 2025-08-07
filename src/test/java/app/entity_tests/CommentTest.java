package app.entity_tests;

import static org.junit.jupiter.api.Assertions.*;

import app.entities.Comment;
import app.entities.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CommentTest {

    @Test
    public void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        User alice = new User("Alice", 20, null, null,
                null, null, null, null);
        Comment comment = new Comment("Hello", alice, now);

        assertEquals("Hello", comment.getText());
        assertEquals("Alice", comment.getAuthor().getName());
        assertEquals(now, comment.getDate());
    }

    @Test
    public void testSetText() {
        User bob = new User("Bob", 20, null, null,
                null, null, null, null);
        Comment comment = new Comment("Old text", bob, LocalDateTime.now());
        comment.setText("New text");
        assertEquals("New text", comment.getText());
    }

    @Test
    public void testSetAuthor() {
        User oldAuthor = new User("OldAuthor", 20, null, null,
                null, null, null, null);
        User newAuthor = new User("NewAuthor", 20, null, null,
                null, null, null, null);
        Comment comment = new Comment("Text", oldAuthor, LocalDateTime.now());
        comment.setAuthor(newAuthor);
        assertEquals("NewAuthor", comment.getAuthor().getName());
    }

    @Test
    public void testSetDate() {
        User author = new User("Author", 20, null, null,
                null, null, null, null);
        LocalDateTime oldDate = LocalDateTime.of(2020, 1, 1, 12, 0);
        LocalDateTime newDate = LocalDateTime.of(2021, 2, 2, 15, 30);
        Comment comment = new Comment("Text", author, oldDate);
        comment.setDate(newDate);
        assertEquals(newDate, comment.getDate());
    }
}
