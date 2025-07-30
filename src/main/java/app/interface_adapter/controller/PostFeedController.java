package app.interface_adapter.controller;

import app.usecase.create_post.CreatePostInteractor;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;

public class PostFeedController {
    // This controller handles the request to view the post feed and start creating a new post.
    private final CreatePostInteractor interactor;

    public PostFeedController(CreatePostInteractor interactor) {
        this.interactor = interactor;
    }

    public PostFeedController() {
        this.interactor = new CreatePostInteractor(new InMemoryPostDataAccessObject());
    }

    public void createNewPost() {
        // This method is called when the user clicks the "new" button
        // Direct navigation to CreatePostView is handled in the view
    }
}
