package app.usecase.matching;

import app.entities.User;
import java.util.List;

/**
 * Interface for services that compute and return a list of matched users based on the current
 * user's preferences and compatibility logic.
 */
public interface MatchService {
    /**
     * Finds and returns a list of matched users for the given current user from a provided list of
     * all users in the system.
     *
     * @param currentUser the user for whom matches are being found
     * @param allUsers the list of all potential users to match with
     * @return a list of users that are compatible matches
     */
    List<User> findMatches(User currentUser, List<User> allUsers);
}
