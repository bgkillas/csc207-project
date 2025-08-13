package app.usecase.matching;

import app.entities.User;
import java.util.List;

public class FindMatchesRequestModel {
    private final User currentUser;
    private final List<User> allUsers;

    public FindMatchesRequestModel(User currentUser, List<User> allUsers) {
        this.currentUser = currentUser;
        this.allUsers = allUsers;
    }

    public User getCurrentUser() { return currentUser; }
    public List<User> getAllUsers() { return allUsers; }
}
