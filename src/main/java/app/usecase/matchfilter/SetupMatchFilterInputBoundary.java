package app.usecase.matchfilter;

/**
 * This interface defines the method required to pass user-defined match preferences (age range,
 * gender, and location) from the controller to the interactor.
 */
public interface SetupMatchFilterInputBoundary {
    /**
     * Sets up a match filter with the given user preferences.
     *
     * @param minAge the minimum preferred age of matches
     * @param maxAge the maximum preferred age of matches
     * @param preferredGender the preferred gender of matches
     * @param preferredLocation the preferred location of matches
     */
    void setupFilter(int minAge, int maxAge, String preferredGender, String preferredLocation);
}
