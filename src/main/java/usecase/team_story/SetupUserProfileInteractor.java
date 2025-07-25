package usecase.team_story;

import entities.User;
import entities.UserSession;

public class SetupUserProfileInteractor implements SetupUserProfileInputBoundary {
    private final SetupUserProfileOutputBoundary presenter;
    private final UserSession session;

    /**
     * Constructor to initialize the interactor with a presenter and session
     *
     * @param presenter the output boundary that will handle UI updates
     * @param session the user session holding the current user
     */
    public SetupUserProfileInteractor(
            SetupUserProfileOutputBoundary presenter, UserSession session) {
        this.presenter = presenter;
        this.session = session;
    }

    /**
     * Updates the user's profile with the given information.
     *
     * @param bio new bio for the user
     * @param age new age for the user
     * @param gender new gender for the user
     * @param location new location for the user
     */
    @Override
    public void setup(String bio, int age, String gender, String location) {
        User user = session.getUser(); // get the currently logged-in user
        user.setBio(bio);
        user.setAge(age);
        user.setGender(gender);
        user.setLocation(location);
        presenter.prepareSuccessView(user);
    }
}
