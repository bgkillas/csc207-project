package usecase.team_story;

import entities.User;

import java.util.List;

public interface MatchService {
    List<User> findMatches(User currentUser, List<User> allUsers);
}
