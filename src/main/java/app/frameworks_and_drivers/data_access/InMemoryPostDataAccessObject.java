package app.frameworks_and_drivers.data_access;

import app.entities.Post;
import app.entities.User;
import java.util.*;

/**
 * In-memory implementation of the PostDataAccessInterface. Stores and retrieves posts using an
 * ArrayList
 */
public class InMemoryPostDataAccessObject implements PostDataAccessInterface {
    private final List<Post> userPosts = new ArrayList();

    /**
     * Saves a new post.
     *
     * @param post the post to be saved
     */
    @Override
    public void savePost(Post post) {
        this.userPosts.add(post);
    }

    /**
     * Update pre-existing post in the postDAO. If not existing already, save as new post.
     *
     * @param post The post to be updated
     */
    @Override
    public void updatePost(Post post) {
        for (int i = 0; i < userPosts.size(); i++) {
            if (this.userPosts.get(i).getId() == post.getId()) {
                this.userPosts.set(i, post);
                return;
            }
        }
        // System.out.println("Post not found");
    }

    /**
     * Search and return Post with the given Post ID.
     *
     * @param id The UUID of the specific post we're looking for.
     * @return Post if it is found in DAO, null otherwise.
     */
    @Override
    public Post getPostById(UUID id) {
        for (Post post : this.userPosts) {
            if (post.getId() == id) {
                return post;
            }
        }
        // System.out.println("No post found with id " + id);
        return null;
    }

    /**
     * Retrieves all posts created by a specific user.
     *
     * @param user the user whose posts are being requested
     * @return a list of {@link Post} objects authored by the given user
     */
    @Override
    public List<Post> getPostsByUser(User user) {
        List<Post> posts = new ArrayList<>();
        for (Post post : this.userPosts) {
            if (post.getAuthor().equals(user)) {
                posts.add(post);
            }
        }
        return posts;
    }

    /**
     * Get all the posts saved in the DAO.
     *
     * @return Post if it is found in DAO, null otherwise.
     */
    @Override
    public List<Post> getAllPosts() {
        return new ArrayList<>(this.userPosts);
    }

    /**
     * Get all the posts of current user or the user's friend(s) saved in the DAO.
     *
     * @param currentUser given user.
     * @return Posts of currentUser and users in currentUser's friend list.
     */
    @Override
    public List<Post> getPostFeed(User currentUser) {
        List<Post> postFeedList = new ArrayList<>();

        // Loop through all the posts saved in the DAO.
        for (Post post : this.userPosts) {
            if (post.getAuthor() == null) {
                // this post doesn't have a valid user as the author, so it will be skipped and not
                // appear on post feed.
                continue;
            } else {
                // Here author is not null. We check if it is either the currentUser or
                // currentUser's friend.
                User author = post.getAuthor();
                if (author.equals(currentUser) || currentUser.getFriendList().contains(author)) {
                    // If so, post gets added to the post feed list.
                    postFeedList.add(post);
                }
            }
        }

        // Currently the list is ordered oldest to newest, however we want to show from newest to
        // oldest.
        Collections.reverse(postFeedList);
        return postFeedList;
    }
}
