package usecase.team_story;

import entities.MatchFilter;
import entities.User;
import entities.UserSession;

/**
 * This class implements the application logic for setting up a user's match filter It takes the
 * match preferences as input, creates a MatchFilter object, attaches it to the currently logged-in
 * user (from UserSession), and calls the presenter
 */
public class SetupMatchFilterInteractor implements SetupMatchFilterInputBoundary {
    private final SetupMatchFilterOutputBoundary presenter;
    private final UserSession session;

    public SetupMatchFilterInteractor(
            SetupMatchFilterOutputBoundary presenter, UserSession session) {
        this.presenter = presenter;
        this.session = session;
    }

    /**
     * Sets up the match filter for the current user using the given preferences
     *
     * @param minAge Minimum preferred age for matches
     * @param maxAge Maximum preferred age for matches
     * @param preferredGender Preferred gender for matches
     * @param preferredLocation Preferred location for matches
     */
    @Override
    public void setupFilter(
            int minAge, int maxAge, String preferredGender, String preferredLocation) {
        MatchFilter filter = new MatchFilter(minAge, maxAge, preferredGender, preferredLocation);
        User user = session.getUser();
        user.setMatchFilter(filter);
        presenter.prepareSuccessView(filter);
    }
}
