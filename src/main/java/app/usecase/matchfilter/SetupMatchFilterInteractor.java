package app.usecase.matchfilter;

import app.entities.MatchFilter;
import app.entities.User;
import app.entities.UserSession;

/**
 * This class implements the application logic for setting up a user's match filter It takes the
 * match preferences as input, creates a MatchFilter object, attaches it to the currently logged-in
 * user (from UserSession), and calls the presenter.
 */
public class SetupMatchFilterInteractor implements SetupMatchFilterInputBoundary {
    private final SetupMatchFilterOutputBoundary presenter;
    private final UserSession session;

    /**
     * Constructs a SetupMatchFilterInteractor with the given presenter and session.
     *
     * @param presenter the output boundary that handles presenting the success view
     * @param session   the current user session containing the logged-in user
     */
    public SetupMatchFilterInteractor(
            SetupMatchFilterOutputBoundary presenter, UserSession session) {
        this.presenter = presenter;
        this.session = session;
    }

    /**
     * Sets up the match filter for the current user using the given preferences.
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
