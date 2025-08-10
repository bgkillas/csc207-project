package app.usecase.create_post;

import app.entities.User;
import java.io.File;

public interface CreatePostInputBoundary {
    void createPost(String title, String content, File image, User author);
}
