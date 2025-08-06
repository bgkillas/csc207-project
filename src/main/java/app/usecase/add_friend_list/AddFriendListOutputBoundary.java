package app.usecase.add_friend_list;

/**
 * Output boundary for the Add Friend use case. This interface defines how the interactor
 * communicates the result of the add friend operation to the presenter.
 */
public interface AddFriendListOutputBoundary {
    /**
     * Prepares the view for a successful friend addition.
     *
     * @param outputData the data representing the success result
     */
    void prepareSuccessView(AddFriendListOutputData outputData);

    /**
     * Prepares the view for a failed friend addition attempt.
     *
     * @param errorMessage a message explaining why the operation failed
     */
    void prepareFailView(String errorMessage);
}
