package app.usecase.match_interaction;

public class MatchInteractionOutputData {
    private final boolean success;
    private final boolean isMutual;
    private final String matchedUserName;
    private final String message;

    public MatchInteractionOutputData(
            boolean success,
            boolean isMutual,
            String matchedUserName,
            String message) {
        this.success = success;
        this.isMutual = isMutual;
        this.matchedUserName = matchedUserName;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isMutual() {
        return isMutual;
    }

    public String getMatchedUserName() {
        return matchedUserName;
    }

    public String getMessage() {
        return message;
    }
}
