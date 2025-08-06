package app.frameworks_and_drivers.data_access;

import app.entities.User;
import java.util.List;

/**
 * Interface for accessing and managing user data in the application.
 * This defines the contract for how user data can be retrieved and stored,
 * regardless of the specific underlying implementation (e.g., in-memory, database).
 */
public interface UserDataAccessInterface {
    /**
     * Retrieves all users stored in the data source.
     *
     * @return a list of User objects.
     */
    List<User> getUsers();

    /**
     * Adds a new user to the data source.
     *
     * @param user the User object to be added.
     */
    void addUser(User user);
}
