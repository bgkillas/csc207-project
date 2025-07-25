package account.login;

import usecase.team_story.SetupMatchFilterInputBoundary;

public class SetupMatchFilterController {
    private final SetupMatchFilterInputBoundary interactor;

    public SetupMatchFilterController(SetupMatchFilterInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void setupFilter(
            int minAge, int maxAge, String preferredGender, String preferredLocation) {
        interactor.setupFilter(minAge, maxAge, preferredGender, preferredLocation);
    }
}
