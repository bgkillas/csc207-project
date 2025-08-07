package app.usecase.create_post;

import app.entities.Post;
import app.entities.User;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import java.awt.Image;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Use case interactor for creating a new post.
 *
 * <p>This class handles the logic for creating a new Post entity based on the user's input,
 * optionally loading an image, and saving it through the data access layer. The outcome is passed
 * to the presenter through the output boundary.
 */
public class CreatePostInteractor implements CreatePostInputBoundary {
    private final PostDataAccessInterface postDataAccessObject;
    private final CreatePostOutputBoundary presenter;

    /**
     * Creates the CreatePost use case interactor.
     *
     * @param postDataAccessObject The data access object used to persist posts.
     * @param presenter The presenter that will handle success or failure output.
     */
    public CreatePostInteractor(
            PostDataAccessInterface postDataAccessObject, CreatePostOutputBoundary presenter) {
        this.postDataAccessObject = postDataAccessObject;
        this.presenter = presenter;
    }

    /**
     * Creates a new post with the given details.
     *
     * @param title the title of the post
     * @param content the content of the post
     * @param image an optional image file to include with the post (can be null)
     * @param author the user creating the post
     */
    @Override
    public void createPost(String title, String content, File image, User author) {
        if (title == null || title.trim().isEmpty()) {
            presenter.presentCreatePostFailure("Post title cannot be empty.");
            return;
        }

        if (content == null || content.trim().isEmpty()) {
            presenter.presentCreatePostFailure("Post content cannot be empty.");
            return;
        }

        if (author == null) {
            presenter.presentCreatePostFailure("Author cannot be null.");
            return;
        }

        try {
            Image postImage = null;

            // Convert file to Image if it exists
            if (image != null && image.exists()) {
                try {
                    postImage = ImageIO.read(image);
                } catch (Exception e) {
                    presenter.presentCreatePostFailure("Failed to load image: " + e.getMessage());
                    return;
                }
            }

            // Create new post
            Post newPost = new Post(title, content, postImage, LocalDateTime.now(), author, new ArrayList<>());

            // Save post in DataAccessObject
            postDataAccessObject.savePost(author, newPost);

            CreatePostOutputData outputData = new CreatePostOutputData(
                    newPost.getTitle(),
                    newPost.getText(),
                    author.getName(),
                    LocalDateTime.now(),
                    newPost.getImage() != null,
                    "Post created successfully."
            );
            presenter.presentCreatePostSuccess(outputData);

        } catch (Exception e) {
            presenter.presentCreatePostFailure("Failed to create post: " + e.getMessage());
        }
    }
}
