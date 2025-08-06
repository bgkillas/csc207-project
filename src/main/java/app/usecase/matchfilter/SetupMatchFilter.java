package app.usecase.matchfilter;

import app.entities.User;

/**
 * Interface for setting match filter preferences.
 * Defines a method that updates a user's match filter based on provided preferences.
 */
public interface SetupMatchFilter {
    /**
     * Sets the match filter preferences for a given user.
     *
     * @param user the user to set filter for
     * @param minAge minimum preferred age
     * @param maxAge maximum preferred age
     * @param preferredGender gender preference ("Any", "Male", "Female")
     * @param preferredLocation location preference ("Any", "Toronto", etc.)
     */
    void setFilter(
            User user, int minAge, int maxAge, String preferredGender, String preferredLocation);
}
