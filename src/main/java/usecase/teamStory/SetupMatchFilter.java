package usecase.teamStory;

import entities.User;

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
    void setFilter(User user, int minAge, int maxAge, String preferredGender, String preferredLocation);
}
