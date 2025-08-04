package app.usecase.create_post;

import app.entities.Post;
import app.entities.User;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import java.awt.Image;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class CreatePostInteractor implements CreatePost {
    private final PostDataAccessInterface postDataAccess;

    public CreatePostInteractor(PostDataAccessInterface postDataAccess) {
        this.postDataAccess = postDataAccess;
    }

    public CreatePostInteractor() {
        this.postDataAccess = new InMemoryPostDataAccessObject();
    }

    @Override
    public void createPost(String title, String content, File image, User author) {
        Image postImage = null;

        // Convert file to Image if it exists
        if (image != null && image.exists()) {
            try {
                postImage = ImageIO.read(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + e.getMessage());
            }
        }

        // Create new post
        Post newPost =
                new Post(title, content, postImage, LocalDateTime.now(), author, new ArrayList<>());

        // Save post in DataAccessObject
        postDataAccess.savePost(author, newPost);
    }
}
