package app.usecase.add_comment;

public interface AddCommentOutputBoundary {
    void presentAddCommentSuccess(AddCommentOutputData outputData);
    void presentAddCommentFailure(String errorMessage);
}

