package app.interface_adapter.presenter;

import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.handle_friend_request.HandleFriendRequestOutputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestOutputData;
import javax.swing.*;

public class FriendRequestPresenter implements HandleFriendRequestOutputBoundary {
    private final FriendRequestViewModel viewModel;

    public FriendRequestPresenter(FriendRequestViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentFriendRequestSuccess(HandleFriendRequestOutputData outputData) {
        System.out.println("Friend request handled from: " + outputData.getRequesterUsername());
        System.out.println("Message: " + outputData.getMessage());

        viewModel.removeCurrentRequest();
    }

    @Override
    public void presentFriendRequestFailure(String errorMessage) {
        System.out.println("Error handling friend request: " + errorMessage);
        JOptionPane.showMessageDialog(
                null, errorMessage, "Friend Request Error", JOptionPane.ERROR_MESSAGE);
    }
}
