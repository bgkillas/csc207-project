package app.usecase.create_post;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreatePostOutputData {

    private final UUID postId;
    private final LocalDateTime timestamp;

    public CreatePostOutputData(UUID postId, LocalDateTime timestamp) {
        this.postId = postId;
        this.timestamp = timestamp;
    }

    public UUID getPostId() {
        return postId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
