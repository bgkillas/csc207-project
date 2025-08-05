package app.interface_adapter.controller;

import app.entities.User;
import app.entities.UserSession;
import app.usecase.match_interaction.MatchInteractionInputBoundary;

public class MatchInteractionController {
    private final MatchInteractionInputBoundary interactor;

    public MatchInteractionController(MatchInteractionInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void connect(UserSession session, User matchedUser) {
        interactor.connect(session, matchedUser);
    }

    public void skip(UserSession session, User matchedUser) {
        interactor.skip(session, matchedUser);
    }
}

