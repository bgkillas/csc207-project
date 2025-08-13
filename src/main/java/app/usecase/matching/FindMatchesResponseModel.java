package app.usecase.matching;

import app.entities.User;
import java.util.List;

/** TODO. */
public class FindMatchesResponseModel {
    private final List<User> matches;

    /** TODO. */
    public FindMatchesResponseModel(List<User> matches) {
        this.matches = matches;
    }

    public List<User> getMatches() {
        return matches;
    }
}
