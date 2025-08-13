package app.usecase.create_post;

import app.entities.User;
import java.io.File;

/**
 * Input boundary for the Create Post use case.
 */
public interface CreatePostInputBoundary {
     /**
      * Create a new post.
      *
      * @param title title of the post
      * @param content content/body of the post
      * @param image optional image file (nullable)
      * @param author authoring user
      */
    void createPost(String title, String content, File image, User author);
}
