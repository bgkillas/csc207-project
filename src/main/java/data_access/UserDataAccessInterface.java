package data_access;

import entities.User;

import java.util.List;

public interface UserDataAccessInterface {
    List<User> getUsers();

    void addUser(User user);
}
