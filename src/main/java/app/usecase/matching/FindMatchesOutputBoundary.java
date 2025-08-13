package app.usecase.matching;

/**
 * Output boundary for the FindMatches use case. Implementations present the
 * response model to the UI layer or a view model.
 */
public interface FindMatchesOutputBoundary {
    /**
     * Present the matches back to the caller/presenter layer.
     *
     * @param responseModel data transfer object containing the list of matched users
     */
    void present(FindMatchesResponseModel responseModel);
}
