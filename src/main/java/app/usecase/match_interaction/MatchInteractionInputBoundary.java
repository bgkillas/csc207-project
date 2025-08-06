package app.usecase.match_interaction;

import app.entities.User;
import app.entities.UserSession;

/**
 * Input boundary interface for handling match interaction use cases. Defines the contract for user
 * actions on matches such as connecting or skipping.
 */
public interface MatchInteractionInputBoundary {
    /**
     * Handles the logic when a user chooses to connect with a matched user.
     *
     * @param userSession the session of the current user
     * @param matchedUser the user to connect with
     */
    void connect(UserSession userSession, User matchedUser);

    /**
     * Handles the logic when a user chooses to skip a matched user.
     *
     * @param userSession the session of the current user
     * @param matchedUser the user to skip
     */
    void skip(UserSession userSession, User matchedUser);
}
