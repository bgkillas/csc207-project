package app.interface_adapter.presenter;

import app.usecase.match_interaction.MatchInteractionOutputBoundary;
import app.usecase.match_interaction.MatchInteractionOutputData;
import javax.swing.*;

/**
 * Presenter for handling the output of match interactions.
 * Displays a dialog box to the user with the result of the interaction.
 */
public class MatchInteractionPresenter implements MatchInteractionOutputBoundary {

    /**
     * Displays a message to the user based on the result of a match interaction.
     *
     * @param outputData The data containing the result of the interaction.
     */
    @Override
    public void presentMatchInteractionResult(MatchInteractionOutputData outputData) {
        String title;
        if (!outputData.isSuccess()) {
            title = "Error";
        } else if (outputData.isMutual()) {
            title = "It's a Match!";
        } else {
            title = "Friend Request Sent";
        }

        JOptionPane.showMessageDialog(null, outputData.getMessage(), title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
