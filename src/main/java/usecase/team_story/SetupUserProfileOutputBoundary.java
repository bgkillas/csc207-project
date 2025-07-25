package usecase.team_story;

import entities.User;

/**
 * This interface defines the method used by the interactor to pass the updated user data back to
 * the presenter after a successful profile setup
 */
public interface SetupUserProfileOutputBoundary {
    void prepareSuccessView(User user);
}
