package interface_adapter.controller;

import app.createPost.CreatePostInteractor;

public class PostFeedViewController {
    // This controller handles the request to update or initialize a user's profile
    private final CreatePostInteractor interactor;

    public PostFeedViewController(CreatePostInteractor interactor) {
        this.interactor = interactor;
    }

    public void createNewPost() {
        // This method is called when the user submits their profile data
        // It forwards the data to the interactor
        this.interactor.setup();
    }
}
