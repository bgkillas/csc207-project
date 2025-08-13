package app.usecase.create_post;

/**
 * Output boundary for the Create Post use case.
 */
public interface CreatePostOutputBoundary {
     /**
      * Present successful post creation data.
      *
      * @param outputData DTO containing id and timestamp
      */
    void present(CreatePostOutputData outputData);
     /**
      * Present a failure message when post creation fails.
      *
      * @param errorMessage the error message
      */
    void presentFailure(String errorMessage);
}
