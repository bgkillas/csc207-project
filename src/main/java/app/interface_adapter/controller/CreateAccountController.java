package app.interface_adapter.controller;

import app.frameworks_and_drivers.external.spotify.SpotifyInterface;
import app.usecase.create_account.CreateAccountInputBoundary;

/**
 * Controller for handling account creation logic. This class receives input from the UI and
 * delegates the task of creating a new account to the interactor.
 */
public class CreateAccountController {
    private final CreateAccountInputBoundary interactor;

    /**
     * Constructs a new {CreateAccountController} with the given interactor.
     *
     * @param interactor the input boundary that handles the create account use case
     */
    public CreateAccountController(CreateAccountInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Initiates account creation using the provided Spotify interface.
     *
     * @param spotify an interface to Spotify authentication and data
     */
    public void createAccount(SpotifyInterface spotify) {
        interactor.create(spotify);
    }
}
