package interface_adapter.presentor;


import usecase.team_story.handle_friend_request.HandleFriendRequestOutputBoundary;
import usecase.team_story.handle_friend_request.HandleFriendRequestOutputData;

public class MatchingRoomPresenter implements HandleFriendRequestOutputBoundary {

    @Override
    public void presentFriendRequestSuccess(HandleFriendRequestOutputData outputData) {
        if (outputData.isSuccess()) {
            // TODO: Update the MatchingRoom view model or UI here
            System.out.println("Friend request succeeded: " + outputData.getMessage());
        }
    }

    @Override
    public void presentFriendRequestFailure(String errorMessage) {
        // TODO: Update the MatchingRoom view model or UI here
        System.out.println("Friend request failed: " + errorMessage);
    }
}