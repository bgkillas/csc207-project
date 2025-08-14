package app.entities;

/** Represents a filter used to determine if a user matches given preferences. */
public class MatchFilter {

    private final int minAge;
    private final int maxAge;
    private final String preferredGender;
    private final String preferredLocation;

    private final UserSpec spec;

    /** Represents a filter used to determine if a user matches given preferences. */
    public MatchFilter(int minAge, int maxAge, String preferredGender, String preferredLocation) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.preferredGender = normalize(preferredGender);
        this.preferredLocation = normalize(preferredLocation);

        UserSpec age = new AgeRangeSpec(minAge, maxAge);
        UserSpec gender = new GenderSpec(this.preferredGender);
        UserSpec location = new LocationSpec(this.preferredLocation);
        this.spec = age.and(gender).and(location);
    }

    /**
     * Checks if the given user satisfies the match filter criteria.
     *
     * @param user the user to check
     * @return true if the user matches the filter, false otherwise
     */
    public boolean isValid(User user) {
        return spec.isSatisfiedBy(user);
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

    private static String normalize(String s) {
        return (s == null || s.isBlank() || s.equalsIgnoreCase("Any")) ? "N/A" : s;
    }

    // Specification， not exposed to outside.
    private interface UserSpec {
        boolean isSatisfiedBy(User u);

        default UserSpec and(UserSpec other) {
            return x -> this.isSatisfiedBy(x) && other.isSatisfiedBy(x);
        }

        /*default UserSpec or(UserSpec other) {
            return x -> this.isSatisfiedBy(x) || other.isSatisfiedBy(x);
        }

        default UserSpec not() {
            return x -> !this.isSatisfiedBy(x);
        }*/
    }

    private static final class AgeRangeSpec implements UserSpec {
        private final int min;
        private final int max;

        AgeRangeSpec(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean isSatisfiedBy(User u) {
            int age = u.getAge();
            return age >= min && age <= max;
        }
    }

    private static final class GenderSpec implements UserSpec {
        private final String pref;

        GenderSpec(String pref) {
            this.pref = pref;
        }

        @Override
        public boolean isSatisfiedBy(User u) {
            return "N/A".equals(pref) || u.getGender().equalsIgnoreCase(pref);
        }
    }

    private static final class LocationSpec implements UserSpec {
        private final String pref;

        LocationSpec(String pref) {
            this.pref = pref;
        }

        @Override
        public boolean isSatisfiedBy(User u) {
            return "N/A".equals(pref) || u.getLocation().equalsIgnoreCase(pref);
        }
    }
}
