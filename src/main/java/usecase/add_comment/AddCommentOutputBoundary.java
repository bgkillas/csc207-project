package usecase.add_comment;

import usecase.add_comment.AddCommentOutputData;

public interface AddCommentOutputBoundary {
    void presentAddCommentSuccess(AddCommentOutputData outputData);
    void presentAddCommentFailure(String errorMessage);
}

