package app.interface_adapter.presenter;

import app.frameworks_and_drivers.view.AddCommentViewInterface;
import app.usecase.add_comment.AddCommentOutputBoundary;
import app.usecase.add_comment.AddCommentOutputData;

/**
 * Presenter for the Add Comment use case. This class implements the output boundary and prepares
 * the view based on whether the comment addition was successful or not.
 */
public class AddCommentPresenter implements AddCommentOutputBoundary {
    private final AddCommentViewInterface view;

    /**
     * Constructs a new {AddCommentPresenter} with the given view.
     *
     * @param view the view interface used to display the result of adding a comment
     */
    public AddCommentPresenter(AddCommentViewInterface view) {
        this.view = view;
    }

    /**
     * Notifies the view that the comment was successfully added.
     *
     * @param outputData the data representing the added comment
     */
    @Override
    public void presentAddCommentSuccess(AddCommentOutputData outputData) {
        view.render("Comment added!", true);
    }

    /**
     * Notifies the view that adding the comment failed.
     *
     * @param errorMessage a message explaining the reason for failure
     */
    @Override
    public void presentAddCommentFailure(String errorMessage) {
        view.render("Failed to add comment: " + errorMessage, false);
    }
}
