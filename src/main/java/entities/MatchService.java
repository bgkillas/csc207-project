package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for finding a list of compatible matches for a user
 */
public class MatchService {

    /**
     * Finds all users from the provided list who are mutually compatible with the current user
     * @param currentUser the user to find matches for
     * @param allUsers a list of users who are potential matches
     * @return a list of users who are mutually compatible with current user
     */
    public List<User> findMatches(User currentUser, List<User> allUsers) {
        List<User> matches = new ArrayList<>();
        for (User potentialMatch : allUsers) {
            if (!potentialMatch.equals(currentUser)
                    && currentUser.getMatchFilter().isValid(potentialMatch)
                    && potentialMatch.getMatchFilter().isValid(currentUser)
                    && MatchCalculator.isCompatible(currentUser, potentialMatch)) {
                matches.add(potentialMatch);
            }
        }

        return matches;
    }

}
