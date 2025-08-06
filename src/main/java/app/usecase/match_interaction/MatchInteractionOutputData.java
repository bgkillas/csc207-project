package app.usecase.match_interaction;

/**
 * Output data object representing the result of a match interaction.
 * Used to pass information from the interactor to the presenter.
 */
public class MatchInteractionOutputData {
    private final boolean success;
    private final boolean isMutual;
    private final String matchedUserName;
    private final String message;

    /**
     * Constructs an output data object for match interaction.
     *
     * @param success whether the interaction (connect or skip) was successful
     * @param isMutual whether the connection was mutual (i.e., both users sent friend requests)
     * @param matchedUserName the name of the matched user involved in the interaction
     * @param message a message describing the result of the interaction
     */
    public MatchInteractionOutputData(
            boolean success, boolean isMutual, String matchedUserName, String message) {
        this.success = success;
        this.isMutual = isMutual;
        this.matchedUserName = matchedUserName;
        this.message = message;
    }

    /**
     * Returns whether the interaction was successful.
     *
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns whether the connection was mutual.
     *
     * @return true if the match was mutual, false otherwise
     */
    public boolean isMutual() {
        return isMutual;
    }

    /**
     * Returns the name of the matched user.
     *
     * @return matched user's name
     */
    public String getMatchedUserName() {
        return matchedUserName;
    }

    /**
     * Returns the message associated with the result of the interaction.
     *
     * @return result message
     */
    public String getMessage() {
        return message;
    }
}
