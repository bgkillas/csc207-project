package app.usecase.matching;

/**
 * Input boundary for the FindMatches use case.
 */
public interface FindMatchesInputBoundary {
    /**
     * Execute the use case given an input request model.
     *
     * @param requestModel input data containing current user and candidates
     */
    void findMatches(FindMatchesRequestModel requestModel);
}
