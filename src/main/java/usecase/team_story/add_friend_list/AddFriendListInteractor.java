package usecase.team_story.add_friend_list;

import entities.User;

public class AddFriendListInteractor implements AddFriendListInputBoundary {
    private final AddFriendListOutputBoundary presenter;

    /**
     * Creates Interactor to add user on friend list
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

        presenter.prepareSuccessView(new AddFriendListOutputData(
                newFriend.getName() + " has been added as a friend."));
    }
}