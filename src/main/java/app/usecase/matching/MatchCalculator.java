package app.usecase.matching;

import app.entities.User;

/** Interface for calculating compatibility between two users based on their preferences. */
public interface MatchCalculator {
    /**
     * Calculates a compatibility score between two users. This score is based on shared interests,
     * favorite artists, genres, etc.
     *
     * @param userOne the first user
     * @param userTwo the second user
     * @return an integer compatibility score
     */
    int calculateCompatibilityScore(User userOne, User userTwo);

    /**
     * Determines whether two users are compatible.
     *
     * @param userOne the first user
     * @param userTwo the second user
     * @return true if the users are compatible, false otherwise
     */
    boolean isCompatible(User userOne, User userTwo);
}
