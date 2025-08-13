package app.usecase.matching;

import app.entities.User;
import java.util.List;

/** TODO. */
public class FindMatchesRequestModel {
    private final User currentUser;
    private final List<User> allUsers;

    /** TODO. */
    public FindMatchesRequestModel(User currentUser, List<User> allUsers) {
        this.currentUser = currentUser;
        this.allUsers = allUsers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }
}
