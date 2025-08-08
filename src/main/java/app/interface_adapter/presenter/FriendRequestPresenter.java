package app.interface_adapter.presenter;

import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.handle_friend_request.HandleFriendRequestOutputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestOutputData;
import javax.swing.*;

/**
 * Presenter for the Handle Friend Request use case. This presenter updates the
 * FriendRequestViewModel when a friend request is successfully processed and handles UI feedback
 * for both success and failure cases.
 */
public class FriendRequestPresenter implements HandleFriendRequestOutputBoundary {
    private final FriendRequestViewModel viewModel;

    /**
     * Constructs a FriendRequestPresenter with the given view model.
     *
     * @param viewModel the view model managing the list of friend requests
     */
    public FriendRequestPresenter(FriendRequestViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Handles the view logic for a successfully processed friend request. Removes the current
     * request from the view model and logs the result.
     *
     * @param outputData the output data containing requester's username and status message
     */
    @Override
    public void presentFriendRequestSuccess(HandleFriendRequestOutputData outputData) {
        // System.out.println("Friend request handled from: " + outputData.getRequesterUsername());
        // System.out.println("Message: " + outputData.getMessage());

        viewModel.removeCurrentRequest();
    }

    /**
     * Handles the view logic for a failed attempt to process a friend request. Displays an error
     * dialog to inform the user.
     *
     * @param errorMessage the error message to display
     */
    @Override
    public void presentFriendRequestFailure(String errorMessage) {
        // System.out.println("Error handling friend request: " + errorMessage);
        JOptionPane.showMessageDialog(
                null, errorMessage, "Friend Request Error", JOptionPane.ERROR_MESSAGE);
    }
}
