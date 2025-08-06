package app.interface_adapter.controller;

import app.usecase.matchfilter.SetupMatchFilterInputBoundary;

/**
 * Controller for handling user input related to match filter setup. Receives filter preferences
 * from the UI layer and forwards them to the SetupMatchFilterInputBoundary interactor.
 */
public class SetupMatchFilterController {
    private final SetupMatchFilterInputBoundary interactor;

    /**
     * Constructs the controller with the given interactor.
     *
     * @param interactor the use case interactor that handles match filter setup
     */
    public SetupMatchFilterController(SetupMatchFilterInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Called when the user submits their match filter preferences. Forwards the preferences to the
     * interactor.
     *
     * @param minAge the minimum preferred age for matches
     * @param maxAge the maximum preferred age for matches
     * @param preferredGender the preferred gender for matches
     * @param preferredLocation the preferred location for matches
     */
    public void setupFilter(
            int minAge, int maxAge, String preferredGender, String preferredLocation) {
        interactor.setupFilter(minAge, maxAge, preferredGender, preferredLocation);
    }
}
