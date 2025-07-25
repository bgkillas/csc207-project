package interface_adapter.controller;

import app.individual_story.CreatePostInteractor;

public class PostFeedController {
    // This controller handles the request to view the post feed and start creating a new post.
    private final CreatePostInteractor interactor;

    public PostFeedController(CreatePostInteractor interactor) {
        this.interactor = interactor;
    }

    public void createNewPost() {
        // This method is called when the user clicks the "new" button
        this.interactor.setup();
    }
}
