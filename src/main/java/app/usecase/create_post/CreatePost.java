package app.usecase.create_post;

import app.entities.User;
import java.io.File;

/**
 * Input boundary for the Create Post use case. This interface defines the contract for creating a
 * new post, including its title, content, optional image, and author.
 */
public interface CreatePost {
    /**
     * Creates a new post with the given title, content, image, and author.
     *
     * @param title the title of the post
     * @param content the body/content of the post
     * @param image an optional image file to include with the post (can be null)
     * @param author the user creating the post
     */
    void createPost(String title, String content, File image, User author);
}
