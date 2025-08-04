package app.usecase.match_interaction;

import app.entities.User;
import app.entities.UserSession;

public interface MatchInteractionInputBoundary {
    void connect(UserSession userSession, User matchedUser);

    void skip(UserSession userSession, User matchedUser);
}
