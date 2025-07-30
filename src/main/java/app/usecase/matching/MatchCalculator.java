package app.usecase.matching;

import app.entities.User;

public interface MatchCalculator {
    int calculateCompatibilityScore(User userOne, User userTwo);

    boolean isCompatible(User userOne, User userTwo);
}
