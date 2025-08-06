package app.interface_adapter.controller;

import app.entities.User;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.usecase.create_post.CreatePostInteractor;
import java.io.File;

/**
 * Controller for handling the creation of new posts. This class receives input from the user
 * interface and gives the task of post creation to the CreatePostInteractor.
 */
public class CreatePostController {
    // This controller handles the request to create and post a new post.
    private final CreatePostInteractor interactor;

    /**
     * Constructs a CreatePostController using an in-memory post data access object. This
     * constructor is primarily used for testing or default behavior where persistent storage is not
     * required.
     */
    public CreatePostController() {
        this.interactor = new CreatePostInteractor(new InMemoryPostDataAccessObject());
    }

    /**
     * Constructs a CreatePostController with the specified interactor.
     *
     * @param interactor the interactor responsible for handling post creation logic
     */
    public CreatePostController(CreatePostInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Delegates the creation of a new post to the interactor.
     *
     * @param title the title of the post
     * @param content the body content of the post
     * @param image an optional image file to include with the post (can be null)
     * @param author the user creating the post
     */
    public void postNewPost(String title, String content, File image, User author) {
        interactor.createPost(title, content, image, author);
    }
}
