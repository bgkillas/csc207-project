package app.usecase.add_friend_list;

/**
 * Output data model for the Add Friend use case. This class holds the result message to be
 * displayed when a friend is successfully added.
 */
public class AddFriendListOutputData {
    private final String message;

    /**
     * Constructs a new {AddFriendListOutputData} with the given message.
     *
     * @param message the message to be shown to the user
     */
    public AddFriendListOutputData(String message) {
        this.message = message;
    }

    /**
     * Returns the message associated with the result of the add friend operation.
     *
     * @return the result message
     */
    public String getMessage() {
        return message;
    }
}
