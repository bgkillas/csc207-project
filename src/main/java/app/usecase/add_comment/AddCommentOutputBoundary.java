package app.usecase.add_comment;

/**
 * The output boundary interface for the Add Comment use case.
 * This interface defines methods that the presenter must implement to
 * handle adding a comment successfully and not.
 */
public interface AddCommentOutputBoundary {
    /**
     * Prepares the view for a successful comment addition.
     *
     * @param outputData the data to be presented after successfully adding a comment
     */
    void presentAddCommentSuccess(AddCommentOutputData outputData);

    /**
     * Prepares the view for a failed comment addition.
     *
     * @param errorMessage a message describing why the comment addition failed
     */
    void presentAddCommentFailure(String errorMessage);
}
