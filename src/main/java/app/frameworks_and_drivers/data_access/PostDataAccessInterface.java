package app.frameworks_and_drivers.data_access;

import app.entities.Post;
import app.entities.User;
import java.util.List;

/**
 * Interface for accessing and managing post data associated with users. This abstracts the storage
 * and retrieval of posts, allowing for various implementations (e.g., in-memory, database-backed).
 */
public interface PostDataAccessInterface {
    /**
     * Retrieves all posts made by a specific user.
     *
     * @param user The user whose posts are to be retrieved
     * @return A list of posts created by the user
     */
    List<Post> getPostsByUser(User user);

    /**
     * Saves a new post for the specified user.
     *
     * @param user The user creating the post
     * @param post The post to be saved
     */
    void savePost(User user, Post post);
}
