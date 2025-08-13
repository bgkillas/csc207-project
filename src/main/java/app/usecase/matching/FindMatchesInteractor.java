package app.usecase.matching;

import app.entities.Match;
import app.entities.User;
import java.util.ArrayList;
import java.util.List;

/** Use case interactor that computes matches directly (no external service), preserving logic. */
public class FindMatchesInteractor implements FindMatchesInputBoundary {
    private final FindMatchesOutputBoundary presenter;

    public FindMatchesInteractor(FindMatchesOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void findMatches(FindMatchesRequestModel requestModel) {
        User current = requestModel.getCurrentUser();
        List<User> all = requestModel.getAllUsers();

        List<User> matches = new ArrayList<>();
        for (User potentialMatch : all) {
            if (!potentialMatch.equals(current)
                    && current.getMatchFilter().isValid(potentialMatch)
                    && potentialMatch.getMatchFilter().isValid(current)
                    && isCompatible(current, potentialMatch)) {
                matches.add(potentialMatch);
            }
        }

        presenter.present(new FindMatchesResponseModel(matches));
    }

    // Delegate compatibility to the entity's scoring logic to avoid duplication
    private boolean isCompatible(User userOne, User userTwo) {
        return new Match(userOne, userTwo).getCompatibilityScore() > 85;
    }
}
