package usecase;

import java.awt.Image;
import java.io.File;

public interface CreatePost {
    void createPost(String title, String content, File image, entities.User author);
}
