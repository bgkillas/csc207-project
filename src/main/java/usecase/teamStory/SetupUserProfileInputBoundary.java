package usecase.teamStory;

/**
 * This interface defines the method required to set up or update a user's profile It takes in
 * personal information such as bio, age, gender, and location, which are passed in from the
 * controller (GUI form) The implementing interactor should update the currently logged-in user's
 * profile based on these inputs and then call the output boundary
 */
public interface SetupUserProfileInputBoundary {
    /**
     * Sets up or updates a user's profile with the given attributes
     *
     * @param bio the user's biography
     * @param age the user's age
     * @param gender the user's gender
     * @param location the user's location
     */
    void setup(String bio, int age, String gender, String location);
}
