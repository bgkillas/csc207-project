package app.usecase.create_account;

import app.entities.User;

/**
 * Output boundary for the Create Account use case.
 * This interface defines how the interactor communicates a successful account
 * creation event to the presenter layer.
 */
public interface CreateAccountOutputBoundary {
    /**
     * This method will be called by the interactor once the account has been created It takes a
     * User object and tells the presenter to handle the successful creation.
     *
     * @param user the newly created User object
     */
    void prepareSuccessView(User user);
}
