package app.interface_adapter.controller;

import app.entities.User;
import app.entities.UserSession;
import app.usecase.match_interaction.MatchInteractionInputBoundary;

/**
 * Controller class for handling user interactions with matched users,
 * such as choosing to connect or skip a match.
 */
public class MatchInteractionController {
    private final MatchInteractionInputBoundary interactor;

    /**
     * Constructs the controller with the specified interactor.
     *
     * @param interactor the input boundary for match interactions
     */
    public MatchInteractionController(MatchInteractionInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Handles the event when the user chooses to connect with a matched user.
     *
     * @param session the current user session
     * @param matchedUser the matched user to connect with
     */
    public void connect(UserSession session, User matchedUser) {
        interactor.connect(session, matchedUser);
    }

    /**
     * Handles the event when the user chooses to skip a matched user.
     *
     * @param session the current user session
     * @param matchedUser the matched user to skip
     */
    public void skip(UserSession session, User matchedUser) {
        interactor.skip(session, matchedUser);
    }
}

