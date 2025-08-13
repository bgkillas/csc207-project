package app.usecase.matching;

import app.entities.User;
import java.util.List;

/**
 * Request model for the FindMatches use case, carrying the current user and
 * the list of all users to consider for matching.
 */
public class FindMatchesRequestModel {
    private final User currentUser;
    private final List<User> allUsers;

    /**
     * Construct a request model.
     *
     * @param currentUser the user for whom to find matches
     * @param allUsers the candidate users to consider
     */
    public FindMatchesRequestModel(User currentUser, List<User> allUsers) {
        this.currentUser = currentUser;
        this.allUsers = allUsers;
    }

    /**
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @return all users to consider for matching
     */
    public List<User> getAllUsers() {
        return allUsers;
    }
}
