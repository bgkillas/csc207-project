package app.usecase.user_profile_setup;

import app.entities.User;

/**
 * This interface defines the method used by the interactor to pass the updated user data back to
 * the presenter after a successful profile setup.
 */
public interface SetupUserProfileOutputBoundary {
    /**
     * Prepares the view to reflect a successful profile setup.
     *
     * @param user the User whose profile has been successfully updated.
     */
    void prepareSuccessView(User user);
}
