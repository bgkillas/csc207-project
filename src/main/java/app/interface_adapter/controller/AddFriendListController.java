package app.interface_adapter.controller;

import app.entities.User;
import app.usecase.add_friend_list.AddFriendListInputBoundary;

/**
 * Controller for adding a friend to the user's friend list. This class receives input from the user
 * interface and delegates the friend addition logic to the interactor.
 */
public class AddFriendListController {
    private final AddFriendListInputBoundary interactor;

    /**
     * Constructs a new {AddFriendListController} with the given interactor.
     *
     * @param interactor the input boundary that handles the add friend use case
     */
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
