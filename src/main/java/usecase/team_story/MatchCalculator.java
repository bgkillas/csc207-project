package usecase.team_story;

import entities.User;

public interface MatchCalculator {
    int calculateCompatibilityScore(User userOne, User userTwo);

    boolean isCompatible(User userOne, User userTwo);
}
