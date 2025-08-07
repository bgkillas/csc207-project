package app.entities;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a social media post created by a user. A post may include a title, text content, an
 * optional image, a timestamp, the author who created it, and a list of comments.
 */
public class Post {
    private UUID id;
    private String title;
    private String text;
    private Image image;
    private LocalDateTime timestamp;
    private User author;
    private List<Comment> comments;

    /**
     * Constructs a Post with specified values.
     *
     * @param title The title of the post
     * @param text The textual content of the post
     * @param image An optional image associated with the post
     * @param timestamp The time the post was created
     * @param author The user who created the post
     * @param comments The list of comments associated with the post
     */
    public Post(
            String title,
            String text,
            Image image,
            LocalDateTime timestamp,
            User author,
            List<Comment> comments) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.text = text;
        this.image = image;
        this.timestamp = timestamp;
        this.author = author;
        this.comments = comments;
    }

    /**
     * Constructs a default Post with no content, no image, current timestamp, and no author or
     * comments.
     */
    public Post() {
        this.id = UUID.randomUUID();
        this.title = "Untitled";
        this.text = "";
        this.image = null;
        this.timestamp = LocalDateTime.now();
        this.author = null;
        this.comments = new ArrayList<Comment>();
    }

    /**
     * Returns the id of this post.
     *
     * @return UUID of this.
     */
    public UUID getId() {return this.id;}

    /**
     * Sets the id of this post.
     *
     * @param id The id to be set.
     */
    public void setId(UUID id) {this.id = id;}

    /**
     * Sets the list of comments on the post.
     *
     * @param comments The list of comments to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Returns the list of comments associated with this post.
     *
     * @return a list of {@link Comment} objects
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Returns the title of the post.
     *
     * @return the post's title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the user who authored the post.
     *
     * @return the User who created the post
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Returns the main text content of the post.
     *
     * @return the post's text content
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the image attached to the post, if any.
     *
     * @return an Image or null if no image is attached
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Returns the timestamp indicating when the post was created.
     *
     * @return a LocalDateTime object representing the creation time
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Returns String of comments seperated by commas.
     * This is a temporary method used mainly for debugging.
     *
     * @return String of comments
     */
    public String getAllCommentsInString() {
        if (this.comments == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (Comment comment : this.comments) {
            result.append(comment.getText());
            result.append(", ");
        }
        result.delete(result.length() - 2, result.length());
        return result.toString();
    }

    public List<Comment> getFilteredComments(User user) {
        List<Comment> filteredComments = new ArrayList<>();
        for (Comment comment : this.comments) {
            User author = comment.getAuthor();
            if (user.hasBlock(author)) {
                // do not add this comment to the filtered comment list.
            }
            else {
                filteredComments.add(comment);
            }
        }
        return filteredComments;
    }
}
