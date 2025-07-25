package usecase.team_story;

import entities.User;

public interface AddFriendsList {

    /**
     * Adds a friend to this user's friend list.
     *
     * @param currentUser the user
     * @param newFriend the user to be added as a friend
     */
    void addFriend(User currentUser, User newFriend);
}
