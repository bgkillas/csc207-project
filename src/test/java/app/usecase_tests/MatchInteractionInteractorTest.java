package app.usecase_tests;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.MatchDataAccessInterface;
import app.usecase.add_friend_list.AddFriendListInputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestInputBoundary;
import app.usecase.match_interaction.MatchInteractionInteractor;
import app.usecase.match_interaction.MatchInteractionOutputBoundary;
import app.usecase.match_interaction.MatchInteractionOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MatchInteractionInteractorTest {

    private MatchDataAccessInterface mockDataAccessObject;
    private HandleFriendRequestInputBoundary mockRequestSender;
    private AddFriendListInputBoundary mockFriendAdder;
    private MatchInteractionOutputBoundary mockPresenter;

    private List<MatchInteractionOutputData> outputHistory;

    @BeforeEach
    void setUp() {
        outputHistory = new ArrayList<>();

        mockDataAccessObject =
                new MatchDataAccessInterface() {
                    private final Map<User, List<User>> outgoing = new HashMap<>();

                    @Override
                    public List<User> getOutgoingFriendRequest(User user) {
                        return outgoing.computeIfAbsent(user, k -> new ArrayList<>());
                    }

                    @Override
                    public List<User> getIncomingFriendRequest(User user) {
                        return outgoing.computeIfAbsent(user, k -> new ArrayList<>());
                    }

                    @Override
                    public void addOutgoingFriendRequest(User from, User to) {}

                    @Override
                    public void addIncomingFriendRequest(User to, User from) {}

                    @Override
                    public void addMatch(User user, app.entities.Match match) {}

                    @Override
                    public List<app.entities.Match> getMatches(User user) {
                        return new ArrayList<>();
                    }
                };

        mockRequestSender =
                new HandleFriendRequestInputBoundary() {
                    @Override
                    public void sendFriendRequest(UserSession userSession, User toUser) {
                        // 不再添加 output，交由 presenter 处理
                    }

                    @Override
                    public void acceptFriendRequest(UserSession userSession, User fromUser) {}

                    @Override
                    public void declineFriendRequest(UserSession userSession, User fromUser) {}
                };

        mockFriendAdder = (user1, user2) -> {};

        mockPresenter = outputHistory::add;
    }

    @Test
    void testMutualConnectResultsInFriendship() {
        User alice =
                new User(
                        "Alice",
                        20,
                        "Toronto",
                        "Female",
                        "I love music!",
                        List.of("Taylor Swift", "Drake"),
                        List.of("Pop", "Hip-Hop"),
                        new ArrayList<>());

        User bob =
                new User(
                        "Bob",
                        21,
                        "Vancouver",
                        "Male",
                        "Hi there!",
                        List.of("Drake", "Ed Sheeran"),
                        List.of("Pop", "Rock"),
                        new ArrayList<>());

        UserSession session = new UserSession();
        session.setUser(alice);

        mockDataAccessObject
                .getOutgoingFriendRequest(bob)
                .add(alice); // simulate Bob already sent request

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDataAccessObject, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData result = outputHistory.get(0);

        assertTrue(result.isSuccess());
        assertTrue(result.isMutual());
        assertEquals("Bob", result.getMatchedUserName());
        assertEquals("You are now friends with Bob", result.getMessage());
    }

    @Test
    void testSingleConnectSendsRequest() {
        User bob =
                new User(
                        "Bob",
                        21,
                        "Vancouver",
                        "Male",
                        "Hi there!",
                        List.of("Drake", "Ed Sheeran"),
                        List.of("Pop", "Rock"),
                        new ArrayList<>());

        User alice =
                new User(
                        "Alice",
                        20,
                        "Toronto",
                        "Female",
                        "I love music!",
                        List.of("Taylor Swift", "Drake"),
                        List.of("Pop", "Hip-Hop"),
                        new ArrayList<>());

        UserSession session = new UserSession();
        session.setUser(alice);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDataAccessObject, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData result = outputHistory.get(0);

        assertTrue(result.isSuccess());
        assertFalse(result.isMutual());
        assertEquals("Bob", result.getMatchedUserName());
        assertEquals("Friend request sent to Bob", result.getMessage());
    }
}
