package usecases;

import entities.MatchFilter;
import entities.User;

public class SetupMatchFilter {
    public void applyFilter(User user, int minAge, int maxAge, String preferredGender, String preferredLocation) {
        MatchFilter filter = new MatchFilter(minAge, maxAge, preferredGender, preferredLocation);
        user.setMatchFilter(filter);
    }
}

