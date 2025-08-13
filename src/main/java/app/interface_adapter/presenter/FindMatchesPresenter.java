package app.interface_adapter.presenter;

import app.usecase.matching.FindMatchesOutputBoundary;
import app.usecase.matching.FindMatchesResponseModel;

/** TODO. */
public class FindMatchesPresenter implements FindMatchesOutputBoundary {
    @Override
    public void present(FindMatchesResponseModel responseModel) {
        // No UI model here; minimal placeholder to preserve behavior without new logic
        System.out.println("Found matches: " + responseModel.getMatches().size());
    }
}
