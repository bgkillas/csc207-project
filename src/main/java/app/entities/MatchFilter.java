package app.entities;

/** Represents a filter used to determine if a user matches given preferences. */
public class MatchFilter {

    private final int minAge;
    private final int maxAge;
    private final String preferredGender;
    private final String preferredLocation;

    /**
     * Constructs a MatchFilter with the given age, gender, and location preferences.
     *
     * @param minAge the minimum age
     * @param maxAge the maximum age
     * @param preferredGender the preferred gender ("Any" for no preference)
     * @param preferredLocation the preferred location ("Any" for no preference)
     */
    public MatchFilter(int minAge, int maxAge, String preferredGender, String preferredLocation) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.preferredGender = preferredGender;
        this.preferredLocation = preferredLocation;
    }

    /**
     * Checks if the given user satisfies the match filter criteria.
     *
     * @param user the user to check
     * @return true if the user matches the filter, false otherwise
     */
    public boolean isValid(User user) {
        return user.getAge() >= minAge
                && user.getAge() <= maxAge
                && ("Any".equals(preferredGender)
                        || user.getGender().equalsIgnoreCase(preferredGender))
                && ("Any".equals(preferredLocation)
                        || user.getLocation().equalsIgnoreCase(preferredLocation));
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getPreferredGender() {
        return preferredGender;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }
}
