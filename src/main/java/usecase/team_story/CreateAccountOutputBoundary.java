package usecase.team_story;

import entities.User;

public interface CreateAccountOutputBoundary {
    /**
     * This method will be called by the interactor once the account has been created It takes a
     * User object and tells the presenter to handle the successful creation
     *
     * @param user the newly created User object
     */
    void prepareSuccessView(User user);
}
