package app.interface_adapter.presenter;

import app.usecase.add_comment.AddCommentOutputBoundary;
import app.usecase.add_comment.AddCommentOutputData;

public class AddCommentPresenter implements AddCommentOutputBoundary {

    @Override
    public void presentAddCommentSuccess(AddCommentOutputData outputData) {
        // TODO: Replace with actual ViewModel or UI update logic
        System.out.println("Author: " + outputData.getAuthor());
        System.out.println("Comment: " + outputData.getText());
        System.out.println("Date: " + outputData.getDate());
        System.out.println("Message: " + outputData.getMessage());
    }

    @Override
    public void presentAddCommentFailure(String errorMessage) {
        // TODO: Replace with actual error display logic
        System.err.println("Failed to add comment: " + errorMessage);
    }
}
