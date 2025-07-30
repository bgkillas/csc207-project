package app.usecase.matchfilter;

import app.entities.MatchFilter;

/**
 * This interface defines the method that the interactor calls to pass the created or updated
 * MatchFilter object to the presenter after successful setup
 */
public interface SetupMatchFilterOutputBoundary {
    /**
     * Called when the match filter has been successfully set up, passing the filter to the
     * presenter for display
     *
     * @param filter the created or updated MatchFilter
     */
    void prepareSuccessView(MatchFilter filter);
}
