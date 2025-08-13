package app.frameworks_and_drivers.data_access;

import app.entities.Post;
import app.entities.User;
import java.util.List;
import java.util.UUID;

/**
 * Interface for accessing and managing post data associated with users. This abstracts the storage
 * and retrieval of posts, allowing for various implementations (e.g., in-memory, database-backed).
 */
public interface PostDataAccessInterface {
    /**
     * Saves a new post.
     *
     * @param post The post to be saved
     */
    void savePost(Post post);

    /**
     * Update pre-existing post in the postDataAccessObject.
     *
     * @param post The post to be updated
     */
    void updatePost(Post post);

    /**
     * Get all the posts' IDs in the DataAccessObject.
     *
     * @return list of UUID of posts in DataAccessObject.
     */
    Post getPostById(UUID id);

    /**
     * Retrieves all posts made by a specific user.
     *
     * @param user The user whose posts are to be retrieved
     * @return A list of posts created by the user
     */
    List<Post> getPostsByUser(User user);

    /**
     * Get all the posts saved in the DataAccessObject.
     *
     * @return Post if it is found in DataAccessObject, null otherwise.
     */
    List<Post> getAllPosts();

    /**
     * Get all the posts of current user or the user's friend(s) saved in the DataAccessObject.
     *
     * @param user given user.
     * @return Posts of currentUser and users in currentUser's friend list.
     */
    List<Post> getPostFeed(User user);
}
