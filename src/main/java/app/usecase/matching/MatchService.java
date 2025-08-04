package app.usecase.matching;

import app.entities.User;
import java.util.List;

public interface MatchService {
    List<User> findMatches(User currentUser, List<User> allUsers);
}
