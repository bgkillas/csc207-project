package app.interface_adapter.presenter;

import app.usecase.create_post.CreatePostOutputBoundary;
import app.usecase.create_post.CreatePostOutputData;

public class CreatePostPresenter implements CreatePostOutputBoundary {
    @Override
    public void present(CreatePostOutputData outputData) {
        // Example: log to console or update a view model
        System.out.println("Post created successfully! ID: " + outputData.getPostId()
                + ", Time: " + outputData.getTimestamp());
    }

    @Override
    public void presentFailure(String errorMessage) {
        // Example: show error message or update a failure view model
        System.err.println("Post creation failed: " + errorMessage);
    }
}
