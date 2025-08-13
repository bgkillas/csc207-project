package app.usecase.matching;

import app.entities.User;
import java.util.List;

public class FindMatchesResponseModel {
    private final List<User> matches;

    public FindMatchesResponseModel(List<User> matches) {
        this.matches = matches;
    }

    public List<User> getMatches() {
        return matches;
    }
}
