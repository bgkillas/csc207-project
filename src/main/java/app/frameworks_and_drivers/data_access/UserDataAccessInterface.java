package app.frameworks_and_drivers.data_access;

import app.entities.User;
import java.util.List;

public interface UserDataAccessInterface {
    List<User> getUsers();

    void addUser(User user);
}
