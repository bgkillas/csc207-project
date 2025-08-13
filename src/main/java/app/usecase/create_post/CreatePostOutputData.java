package app.usecase.create_post;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Output data returned by the Create Post use case.
 */
public class CreatePostOutputData {

    private final UUID postId;
    private final LocalDateTime timestamp;

    /**
     * Construct output data for a created post.
     *
     * @param postId the unique identifier of the newly created post
     * @param timestamp the creation timestamp
     */
    public CreatePostOutputData(UUID postId, LocalDateTime timestamp) {
        this.postId = postId;
        this.timestamp = timestamp;
    }

    /**
     * @return the post id
     */
    public UUID getPostId() {
        return postId;
    }

    /**
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
