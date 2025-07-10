package entities;
import java.util.*;

public class Match {
    private final UUID matchId = UUID.randomUUID();
    private final User user;
    private final int compatibilityScore;
    private final List<String> sharedArtists;

    public Match(User user, int score, List<String> sharedArtists) {
        this.user = user;
        this.compatibilityScore = score;
        this.sharedArtists = sharedArtists;
    }

    public int getCompatibilityScore() {
        return compatibilityScore;
    }

    public User getUser() {
        return user;
    }
}
