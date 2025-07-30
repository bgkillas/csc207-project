package app.usecase.add_friend_list;

public interface AddFriendListOutputBoundary {
    void prepareSuccessView(AddFriendListOutputData outputData);
    void prepareFailView(String errorMessage);
}
