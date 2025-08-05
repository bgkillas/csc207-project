package app.interface_adapter.presenter;

import app.usecase.match_interaction.MatchInteractionOutputBoundary;
import app.usecase.match_interaction.MatchInteractionOutputData;

import javax.swing.*;

public class MatchInteractionPresenter implements MatchInteractionOutputBoundary {

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

        JOptionPane.showMessageDialog(null, outputData.getMessage(), title, JOptionPane.INFORMATION_MESSAGE);
    }
}
