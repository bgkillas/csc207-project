package app.individual_story;

import usecase.CreatePost;
import data_access.PostDataAccessInterface;
import entities.Post;
import entities.User;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreatePostInteractor implements CreatePost {
    private final PostDataAccessInterface postDataAccess;

    public CreatePostInteractor(PostDataAccessInterface postDataAccess) {
        this.postDataAccess = postDataAccess;
    }

    public CreatePostInteractor() {
        this.postDataAccess = new data_access.InMemoryPostDataAccessObject();
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
        Post newPost = new Post(
                title,
                content,
                postImage,
                LocalDateTime.now(),
                author,
                new ArrayList<>()
        );

        // Save post in DAO
        postDataAccess.savePost(author, newPost);
    }
}
