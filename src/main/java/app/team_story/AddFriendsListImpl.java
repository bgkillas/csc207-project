package app.team_story;

import entities.User;
import usecase.team_story.AddFriendsList;

public class AddFriendsListImpl implements AddFriendsList {

    /**
     * Adds a friend to this user's friend list.
     *
     * @param currentUser the user
     * @param newFriend the user to be added as a friend
     */
    @Override
    public void addFriend(User currentUser, User newFriend) {
        if (newFriend == null || newFriend.equals(currentUser)) {
            throw new IllegalArgumentException("Cannot add null or self as friend");
        }

        currentUser.addFriend(newFriend);
        newFriend.addFriend(currentUser);
    }
}
