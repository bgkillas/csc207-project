package app.frameworks_and_drivers.data_access;

import app.entities.Post;
import app.entities.User;
import java.util.*;

/**
 * In-memory implementation of the PostDataAccessInterface. Stores and retrieves user posts using a
 * HashMap.
 */
public class InMemoryPostDataAccessObject implements PostDataAccessInterface {
    private final Map<User, List<Post>> userPosts = new HashMap<>();

    /**
     * Retrieves all posts created by a specific user.
     *
     * @param user the user whose posts are being requested
     * @return a list of {@link Post} objects authored by the given user
     */
    @Override
    public List<Post> getPostsByUser(User user) {
        return new ArrayList<>(userPosts.getOrDefault(user, new ArrayList<>()));
    }

    /**
     * Saves a new post for the given user. If the user has no posts yet, a new list is created.
     *
     * @param user the author of the post
     * @param post the post to be saved
     */
    @Override
    public void savePost(User user, Post post) {
        userPosts.computeIfAbsent(user, k -> new ArrayList<>()).add(post);
    }
}
