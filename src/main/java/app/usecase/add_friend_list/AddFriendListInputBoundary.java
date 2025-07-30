package app.usecase.add_friend_list;

import app.entities.User;

public interface AddFriendListInputBoundary {

    /**
     * Adds a friend to this user's friend list.
     *
     * @param currentUser the user
     * @param newFriend the user to be added as a friend
     */
    void addFriend(User currentUser, User newFriend);
}
