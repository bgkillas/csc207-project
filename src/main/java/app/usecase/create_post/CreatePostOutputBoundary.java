package app.usecase.create_post;

public interface CreatePostOutputBoundary {
    void present(CreatePostOutputData outputData);
    void presentFailure(String errorMessage);
}
