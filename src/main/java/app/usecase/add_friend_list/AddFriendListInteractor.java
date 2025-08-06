package app.usecase.add_friend_list;

import app.entities.User;

/**
 * Interactor for the Add Friend use case.
 * This class implements the logic for adding a new friend
 * to a user's friend list. It performs validation and communicates the result
 * to the output boundary (presenter).
 */
public class AddFriendListInteractor implements AddFriendListInputBoundary {
    private final AddFriendListOutputBoundary presenter;

    /**
     * Creates Interactor to add user on friend list.
     *
     * @param presenter - presenter for adding into friend list
     */
    public AddFriendListInteractor(AddFriendListOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void addFriend(User currentUser, User newFriend) {
        if (newFriend == null || newFriend.equals(currentUser)) {
            presenter.prepareFailView("Cannot add null or self as friend");
            return;
        }

        currentUser.addFriend(newFriend);
        newFriend.addFriend(currentUser);

        presenter.prepareSuccessView(
                new app.usecase.add_friend_list.AddFriendListOutputData(
                        newFriend.getName() + " has been added as a friend."));
    }
}
