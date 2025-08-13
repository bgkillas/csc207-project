package app.usecase.create_post;

import app.entities.User;
import java.io.File;

/** TODO. */
public interface CreatePostInputBoundary {
    /** TODO. */
    void createPost(String title, String content, File image, User author);
}
