package interface_adapter.presentor;

import entities.MatchFilter;
import usecase.teamStory.SetupMatchFilterOutputBoundary;

import javax.swing.*;

public class SetupMatchFilterPresenter implements SetupMatchFilterOutputBoundary {
    /**
     * This class is the presenter for the Match Filter setup feature It implements the
     * SetupMatchFilterOutputBoundary interface and displays a success message in the GUI when a
     * user's match preferences are saved
     */
    @Override
    public void prepareSuccessView(MatchFilter filter) {
        JOptionPane.showMessageDialog(
                null,
                "Match filter set!\nAge: "
                        + filter.getMinAge()
                        + "-"
                        + filter.getMaxAge()
                        + "\nGender: "
                        + filter.getPreferredGender()
                        + "\nLocation: "
                        + filter.getPreferredLocation());
    }
}
