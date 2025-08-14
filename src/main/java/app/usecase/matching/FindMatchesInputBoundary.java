package app.usecase.matching;

import app.entities.User;
import java.util.List;

/** TODO. */
public interface FindMatchesInputBoundary {
    /** TODO. */
    void findMatches(FindMatchesRequestModel requestModel);

    /** TODO. */
    List<User> getMatches(FindMatchesRequestModel requestModel);
}
