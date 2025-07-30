package app.interface_adapter.controller;

import app.usecase.user_profile_setup.SetupUserProfileInputBoundary;

public class SetupUserProfileController {
    // This controller handles the request to update or initialize a user's profile
    private final SetupUserProfileInputBoundary interactor;

    public SetupUserProfileController(SetupUserProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void setupUserProfile(String bio, int age, String gender, String location) {
        // This method is called when the user submits their profile data
        // It forwards the data to the interactor
        interactor.setup(bio, age, gender, location);
    }
}
