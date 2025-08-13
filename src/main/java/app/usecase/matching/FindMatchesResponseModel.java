package app.usecase.matching;

import app.entities.User;
import java.util.List;

/**
 * Response model for the FindMatches use case, containing the matched users.
 */
public class FindMatchesResponseModel {
    private final List<User> matches;

    /**
     * Construct a response model.
     *
     * @param matches the list of matched users
     */
    public FindMatchesResponseModel(List<User> matches) {
        this.matches = matches;
    }

    /**
     * @return the list of matched users
     */
    public List<User> getMatches() {
        return matches;
    }
}
