package app.entities;

import java.time.LocalDateTime;

/**
 * Represents a comment made by a user. Each comment contains text content, an author identifier,
 * and the date and time it was created.
 */
public class Comment {
    private String text;
    private String author;
    private LocalDateTime date;

    /**
     * Constructs a new {Comment} with the given text, author, and timestamp.
     *
     * @param text the content of the comment
     * @param author the author of the comment
     * @param date the time the comment was created
     */
    public Comment(String text, String author, LocalDateTime date) {
        this.text = text;
        this.author = author;
        this.date = date;
    }

    /**
     * Returns the text content of the comment.
     *
     * @return the comment text
     */
    public String getText() {
        return text;
    }

    /**
     * Updates the text content of the comment.
     *
     * @param text the new comment text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the author of the comment.
     *
     * @return the author's identifier (e.g., username)
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the comment.
     *
     * @param author the author's identifier
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the timestamp when the comment was created.
     *
     * @return the date and time of comment creation
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Updates the timestamp of the comment.
     *
     * @param date the new date and time of the comment
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
