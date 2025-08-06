package app.usecase.add_friend_list;

import app.entities.User;

/**
 * Input boundary for the Add Friend use case.
 * This interface defines the method that the controller calls to
 * add a new friend to a user's friend list.
 */
public interface AddFriendListInputBoundary {

    /**
     * Adds a friend to this user's friend list.
     *
     * @param currentUser the user
     * @param newFriend the user to be added as a friend
     */
    void addFriend(User currentUser, User newFriend);
}
