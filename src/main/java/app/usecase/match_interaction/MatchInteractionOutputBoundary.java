package app.usecase.match_interaction;

/**
 * Output boundary interface for presenting the result of a match interaction (connect or skip).
 * Implementations of this interface will handle displaying the results to the user.
 */
public interface MatchInteractionOutputBoundary {
    /**
     * Presents the result of a match interaction.
     *
     * @param outputData the output data containing the result and details of the interaction
     */
    void presentMatchInteractionResult(MatchInteractionOutputData outputData);
}
