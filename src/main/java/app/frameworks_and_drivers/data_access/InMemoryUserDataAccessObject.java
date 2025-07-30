package app.frameworks_and_drivers.data_access;

import app.entities.User;
import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDataAccessObject implements UserDataAccessInterface {
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users); // return a copy just in case of accidental mutation
    }

}
