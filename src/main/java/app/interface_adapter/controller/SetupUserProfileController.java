package app.interface_adapter.controller;

import app.usecase.user_profile_setup.SetupUserProfileInputBoundary;

/**
 * Controller for handling user profile setup requests. Receives input from the UI and delegates the
 * data to the use case interactor that handles profile updates.
 */
public class SetupUserProfileController {
    // This controller handles the request to update or initialize a user's profile
    private final SetupUserProfileInputBoundary interactor;

    /**
     * Constructs the controller with the given interactor.
     *
     * @param interactor the use case input boundary responsible for handling profile setup
     */
    public SetupUserProfileController(SetupUserProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Called when the user submits their profile setup form. Forwards the provided user information
     * to the interactor.
     *
     * @param bio the user's bio
     * @param age the user's age
     * @param gender the user's gender
     * @param location the user's location
     */
    public void setupUserProfile(String bio, int age, String gender, String location) {
        interactor.setup(bio, age, gender, location);
    }
}
