package app.teamStory;

import entities.MatchFilter;
import entities.User;
import usecase.teamStory.SetupMatchFilter;

/** Implementation of the SetupMatchFilter use case. */
public class SetupMatchFilterImpl implements SetupMatchFilter {

    @Override
    public void setFilter(
            User user, int minAge, int maxAge, String preferredGender, String preferredLocation) {
        MatchFilter filter = new MatchFilter(minAge, maxAge, preferredGender, preferredLocation);
        user.setMatchFilter(filter);
    }
}
