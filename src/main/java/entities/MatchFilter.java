package entities;

public class MatchFilter {
    private final int minAge;
    private final int maxAge;
    private final String preferredGender;
    private final String preferredLocation;

    public MatchFilter(int minAge, int maxAge, String preferredGender, String preferredLocation) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.preferredGender = preferredGender;
        this.preferredLocation = preferredLocation;
    }

    public boolean isValid(User user) {
        return user.getAge() >= minAge &&
                user.getAge() <= maxAge &&
                (preferredGender.equals("Any") || user.getGender().equalsIgnoreCase(preferredGender)) &&
                (preferredLocation.equals("Any") || user.getLocation().equalsIgnoreCase(preferredLocation));
    }
}
