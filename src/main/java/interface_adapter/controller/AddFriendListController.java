package interface_adapter.controller;

import entities.User;
import usecase.team_story.add_friend_list.AddFriendListInputBoundary;

/**
 * Controller for adding a friend to the user's friend list.
 */
public class AddFriendListController {
    private final AddFriendListInputBoundary interactor;

    public AddFriendListController(AddFriendListInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Calls the interactor to add a friend.
     *
     * @param currentUser the current user
     * @param newFriend the user to be added as friend
     */
    public void addFriend(User currentUser, User newFriend) {
        interactor.addFriend(currentUser, newFriend);
    }
}