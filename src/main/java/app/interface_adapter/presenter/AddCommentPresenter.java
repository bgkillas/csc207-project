package app.interface_adapter.presenter;

import app.usecase.add_comment.AddCommentOutputBoundary;
import app.usecase.add_comment.AddCommentOutputData;
import app.frameworks_and_drivers.view.AddCommentViewInterface;

public class AddCommentPresenter implements AddCommentOutputBoundary {
    private final AddCommentViewInterface view;

    public AddCommentPresenter(AddCommentViewInterface view) {
        this.view = view;
    }

    @Override
    public void presentAddCommentSuccess(AddCommentOutputData outputData) {
        view.render("Comment added!", true);
    }

    @Override
    public void presentAddCommentFailure(String errorMessage) {
        view.render("Failed to add comment: " + errorMessage, false);
    }
}
