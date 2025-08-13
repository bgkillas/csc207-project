package app.usecase.create_post;

/** TODO. */
public interface CreatePostOutputBoundary {
    /** TODO. */
    void present(CreatePostOutputData outputData);

    /** TODO. */
    void presentFailure(String errorMessage);
}
