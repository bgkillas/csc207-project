package app.interface_adapter.presenter;

import app.usecase.handle_friend_request.HandleFriendRequestOutputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestOutputData;

/** Presenter for handling the output of friend requests made within the Matching Room view. */
public class MatchingRoomPresenter implements HandleFriendRequestOutputBoundary {
    /**
     * Handles successful friend request actions.
     *
     * @param outputData the output data containing success message and requester info
     */
    @Override
    public void presentFriendRequestSuccess(HandleFriendRequestOutputData outputData) {
        if (outputData.isSuccess()) {
            // TODO: Update the MatchingRoom view model or UI here
            // System.out.println("Friend request succeeded: " + outputData.getMessage());
        }
    }

    /**
     * Handles failed friend request actions.
     *
     * @param errorMessage the error message to be displayed
     */
    @Override
    public void presentFriendRequestFailure(String errorMessage) {
        // TODO: Update the MatchingRoom view model or UI here
        System.out.println("Friend request failed: " + errorMessage);
    }
}
