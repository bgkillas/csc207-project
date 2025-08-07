package app.interface_adapter.presenter;

import app.usecase.add_friend_list.AddFriendListOutputBoundary;
import app.usecase.add_friend_list.AddFriendListOutputData;

/**
 * Presenter for the Add Friend use case. This class implements the output boundary and formats the
 * output for display.
 */
public class AddFriendListPresenter implements AddFriendListOutputBoundary {

    @Override
    public void prepareSuccessView(AddFriendListOutputData outputData) {
        // TODO: Replace this with logic to update UI or ViewModel
        System.out.println("Friend added successfully: " + outputData.getMessage());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // TODO: Replace with error handling logic for UI
        System.out.println("Failed to add friend: " + errorMessage);
    }
}
