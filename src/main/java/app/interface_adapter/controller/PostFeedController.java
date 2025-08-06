package app.interface_adapter.controller;

import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.usecase.create_post.CreatePostInteractor;

/**
 * Controller for the Post Feed feature.
 * Responsible for handling user actions related to viewing the post feed and post creation.
 */
public class PostFeedController {
    // This controller handles the request to view the post feed and start creating a new post.
    private final CreatePostInteractor interactor;

    /**
     * Constructs a PostFeedController with a given CreatePostInteractor.
     *
     * @param interactor The interactor to handle post creation logic
     */
    public PostFeedController(CreatePostInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Default constructor that initializes the controller with an in-memory data access object.
     */
    public PostFeedController() {
        this.interactor = new CreatePostInteractor(new InMemoryPostDataAccessObject());
    }

    /**
     * Handles the user's request to create a new post.
     * The actual UI navigation is managed by the view layer.
     */
    public void createNewPost() {
        // This method is called when the user clicks the "new" button
        // Direct navigation to CreatePostView is handled in the view
    }
}
