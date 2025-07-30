package app.usecase.login;

/** Interface for login management. */
public interface LoginManager {
    /**
     * Determines weather or not there exists a user registered with this username.
     *
     * @param name the name of the user.
     * @return if a user exists
     */
    boolean hasLogin(String name);

    /**
     * Checks if the username matches with the password in the database, assumes HasLogin(name) is
     * true.
     *
     * @param name the name of the user
     * @param password the password of the user
     * @return if password matches database
     */
    boolean tryLogin(String name, String password);

    /**
     * Registers a user with a given password, assumes HasLogin(name) is false.
     *
     * @param name the name of the user
     * @param password the password of the user
     */
    void registerLogin(String name, String password);
}
