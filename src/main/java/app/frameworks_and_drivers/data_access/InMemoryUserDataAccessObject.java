package app.frameworks_and_drivers.data_access;

import app.entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of UserDataAccessInterface.
 * Stores user data in a simple list structure.
 */
public class InMemoryUserDataAccessObject implements UserDataAccessInterface {
    private final List<User> users = new ArrayList<>();

    /**
     * Adds a user to the in-memory list.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Retrieves a copy of the list of all users.
     *
     * @return a new {@link List} containing all stored users
     */
    public List<User> getUsers() {
        // return a copy just in case of accidental mutation
        return new ArrayList<>(users);
    }
}
