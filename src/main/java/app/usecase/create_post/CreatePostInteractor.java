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

/**
 * Interactor for the Create Post use case. This class handles the logic of creating a new post,
 * optionally loading an image from file, and saving the post via PostDataAccessInterface
 */
public class CreatePostInteractor implements CreatePostInputBoundary {
    private final PostDataAccessInterface postDataAccess;
    private final CreatePostOutputBoundary presenter;
    private final ImageLoader imageLoader;

    /**
     * Constructs a CreatePostInteractor with the specified data access object.
     *
     * @param postDataAccess the data access interface used to persist posts
     */
    public CreatePostInteractor(
            PostDataAccessInterface postDataAccess,
            CreatePostOutputBoundary presenter,
            ImageLoader imageLoader) {
        this.postDataAccess = postDataAccess;
        this.presenter = presenter;
        this.imageLoader = imageLoader;
    }

    /** TODO. */
    // Convenience constructor for tests or simple wiring when presenter/image loader not needed
    public CreatePostInteractor(PostDataAccessInterface postDataAccess) {
        this(postDataAccess, null, null);
    }

    /**
     * Constructs a CreatePostInteractor using an in-memory data access object. This constructor is
     * primarily for testing.
     */
    public CreatePostInteractor() {
        this(new InMemoryPostDataAccessObject());
    }

    /**
     * Creates a new post and saves it using the configured data access object. If an image file is
     * provided, it attempts to load it into an Image object. A new Post is then created with the
     * current timestamp and associated with the author.
     *
     * @param title the title of the post
     * @param content the textual content of the post
     * @param image an optional image file to be included (can be null)
     * @param author the user creating the post
     */
    @Override
    public void createPost(String title, String content, File image, User author) {
        Image postImage = null;

        // Convert file to Image if it exists
        if (image != null && image.exists()) {
            try {
                if (imageLoader != null) {
                    postImage = imageLoader.load(image);
                } else {
                    postImage = ImageIO.read(image);
                }
            } catch (Exception e) {
                System.err.println("Failed to load image: " + e.getMessage());
            }
        }

        // Create new post
        Post newPost =
                new Post(title, content, postImage, LocalDateTime.now(), author, new ArrayList<>());

        // Save post in DataAccessObject
        postDataAccess.savePost(newPost);
        if (presenter != null) {
            presenter.present(new CreatePostOutputData(newPost.getId(), newPost.getTimestamp()));
        }
    }
}
