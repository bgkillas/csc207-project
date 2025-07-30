package interface_adapter.presentor;


import usecase.team_story.handle_friend_request.HandleFriendRequestOutputBoundary;
import usecase.team_story.handle_friend_request.HandleFriendRequestOutputData;

public class FriendRequestPresenter implements HandleFriendRequestOutputBoundary {

    @Override
    public void presentFriendRequestSuccess(HandleFriendRequestOutputData outputData) {
        // This logic is specific to the FriendRequestView.
        System.out.println("Handled incoming friend request from: " + outputData.getRequesterUsername());
        System.out.println("Message: " + outputData.getMessage());

        // Here you would typically update the FriendRequestViewModel,
        // e.g., remove the handled request from the list, show confirmation, etc.
    }

    @Override
    public void presentFriendRequestFailure(String errorMessage) {
        // Display error in FriendRequestView
        System.out.println("Error handling friend request: " + errorMessage);

        // Optionally, update viewmodel or trigger error UI display
    }
}
