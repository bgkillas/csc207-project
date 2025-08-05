package app.entity_tests;

import static org.junit.jupiter.api.Assertions.*;

import app.entities.Comment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
public class CommentTest {

    @Test
    public void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Comment comment = new Comment("Hello", "Alice", now);

        assertEquals("Hello", comment.getText());
        assertEquals("Alice", comment.getAuthor());
        assertEquals(now, comment.getDate());
    }

    @Test
    public void testSetText() {
        Comment comment = new Comment("Old text", "Bob", LocalDateTime.now());
        comment.setText("New text");
        assertEquals("New text", comment.getText());
    }

    @Test
    public void testSetAuthor() {
        Comment comment = new Comment("Text", "OldAuthor", LocalDateTime.now());
        comment.setAuthor("NewAuthor");
        assertEquals("NewAuthor", comment.getAuthor());
    }

    @Test
    public void testSetDate() {
        LocalDateTime oldDate = LocalDateTime.of(2020, 1, 1, 12, 0);
        LocalDateTime newDate = LocalDateTime.of(2021, 2, 2, 15, 30);
        Comment comment = new Comment("Text", "Author", oldDate);
        comment.setDate(newDate);
        assertEquals(newDate, comment.getDate());
    }
}