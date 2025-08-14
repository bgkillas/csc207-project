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

    private MatchDataAccessInterface mockDAO;
    private HandleFriendRequestInputBoundary mockRequestSender;
    private AddFriendListInputBoundary mockFriendAdder;
    private MatchInteractionOutputBoundary mockPresenter;

    private List<MatchInteractionOutputData> outputHistory;

    @BeforeEach
    void setUp() {
        outputHistory = new ArrayList<>();

        mockDAO =
                new MatchDataAccessInterface() {
                    private final Map<User, List<User>> outgoing = new HashMap<>();
                    private final Map<User, List<User>> incoming = new HashMap<>();

                    @Override
                    public List<User> getOutgoingFriendRequest(User user) {
                        return outgoing.computeIfAbsent(user, k -> new ArrayList<>());
                    }

                    @Override
                    public List<User> getIncomingFriendRequest(User user) {
                        return incoming.computeIfAbsent(user, k -> new ArrayList<>());
                    }

                    @Override
                    public void addOutgoingFriendRequest(User from, User to) {
                        getOutgoingFriendRequest(from).add(to);
                    }

                    @Override
                    public void addIncomingFriendRequest(User to, User from) {
                        getIncomingFriendRequest(to).add(from);
                    }

                    @Override
                    public void addMatch(User user, app.entities.Match match) {
                        /* not used */
                    }

                    @Override
                    public List<app.entities.Match> getMatches(User user) {
                        return List.of();
                    }
                };

        mockRequestSender =
                new HandleFriendRequestInputBoundary() {
                    @Override
                    public void sendFriendRequest(UserSession userSession, User toUser) {}

                    @Override
                    public void acceptFriendRequest(UserSession userSession, User fromUser) {}

                    @Override
                    public void declineFriendRequest(UserSession userSession, User fromUser) {}
                };

        mockFriendAdder =
                (u1, u2) -> {
                    u1.addFriend(u2);
                    u2.addFriend(u1);
                };

        mockPresenter = outputHistory::add;
    }

    private User makeUser(String name) {
        return new User(
                name,
                20,
                "Toronto",
                "Female",
                "I love music!",
                List.of("Taylor Swift"),
                List.of("Pop"),
                new ArrayList<>());
    }

    @Test
    void mutualConnect_viaDAO_cleansRequests_andBecomesFriends() {
        User alice = makeUser("Alice");
        User bob = makeUser("Bob");

        UserSession session = new UserSession();
        session.setUser(alice);

        mockDAO.getOutgoingFriendRequest(bob).add(alice);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData out = outputHistory.get(0);
        assertTrue(out.isSuccess());
        assertTrue(out.isMutual());
        assertEquals("Bob", out.getMatchedUserName());
        assertEquals("You are now friends with Bob", out.getMessage());

        assertTrue(alice.getFriendList().contains(bob));
        assertTrue(bob.getFriendList().contains(alice));

        assertFalse(mockDAO.getOutgoingFriendRequest(bob).contains(alice));
        assertFalse(mockDAO.getIncomingFriendRequest(alice).contains(bob));
    }

    @Test
    void mutualConnect_viaSessionIncoming_cleansRequests_andBecomesFriends() {
        User alice = makeUser("Alice");
        User bob = makeUser("Bob");

        UserSession session = new UserSession();
        session.setUser(alice);

        session.getIncomingMatches().add(bob);

        mockDAO.getIncomingFriendRequest(alice).add(bob);
        mockDAO.getOutgoingFriendRequest(bob).add(alice);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData out = outputHistory.get(0);
        assertTrue(out.isSuccess());
        assertTrue(out.isMutual());
        assertEquals("Bob", out.getMatchedUserName());

        assertTrue(alice.getFriendList().contains(bob));
        assertTrue(bob.getFriendList().contains(alice));
        assertFalse(session.getIncomingMatches().contains(bob));
        assertFalse(session.getOutgoingMatches().contains(bob));
    }

    @Test
    void singleConnect_sendsRequest_andAddsToSessionOutgoing_noDuplicates() {
        User alice = makeUser("Alice");
        User bob = makeUser("Bob");
        UserSession session = new UserSession();
        session.setUser(alice);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);
        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData first = outputHistory.get(0);
        assertTrue(first.isSuccess());
        assertFalse(first.isMutual());
        assertEquals(List.of(bob), session.getOutgoingMatches());
        assertEquals(List.of(alice), mockDAO.getIncomingFriendRequest(bob));
        assertEquals(List.of(bob), mockDAO.getOutgoingFriendRequest(alice));

        interactor.connect(session, bob);
        assertEquals(2, outputHistory.size());
        MatchInteractionOutputData second = outputHistory.get(1);
        assertTrue(second.isSuccess());
        assertFalse(second.isMutual());
        assertTrue(second.getMessage().toLowerCase().contains("already sent"));
        assertEquals(1, session.getOutgoingMatches().size()); // 未重复添加
    }

    @Test
    void alreadyOutgoing_viaDAO_branch() {
        User alice = makeUser("Alice");
        User bob = makeUser("Bob");
        UserSession session = new UserSession();
        session.setUser(alice);

        mockDAO.addOutgoingFriendRequest(alice, bob);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData out = outputHistory.get(0);
        assertTrue(out.isSuccess());
        assertFalse(out.isMutual());
        assertTrue(out.getMessage().toLowerCase().contains("already sent"));
    }

    @Test
    void alreadyOutgoing_viaSessionOutgoingMatches_branch() {
        User alice = makeUser("Alice");
        User bob = makeUser("Bob");
        UserSession session = new UserSession();
        session.setUser(alice);

        session.getOutgoingMatches().add(bob);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData out = outputHistory.get(0);
        assertTrue(out.isSuccess());
        assertFalse(out.isMutual());
        assertTrue(out.getMessage().toLowerCase().contains("already sent"));
    }

    @Test
    void connect_withNullCurrentUser_guard() {
        User bob = makeUser("Bob");
        UserSession session = new UserSession();

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData out = outputHistory.get(0);
        assertFalse(out.isSuccess());
        assertFalse(out.isMutual());
        assertEquals("Bob", out.getMatchedUserName());
        assertEquals("Cannot connect right now.", out.getMessage());
    }

    @Test
    void connect_withNullMatchedUser_guard() {
        User alice = makeUser("Alice");
        UserSession session = new UserSession();
        session.setUser(alice);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, null);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData out = outputHistory.get(0);
        assertFalse(out.isSuccess());
        assertFalse(out.isMutual());
        assertEquals("", out.getMatchedUserName());
        assertEquals("Cannot connect right now.", out.getMessage());
    }

    @Test
    void selfConnect_isRejected() {
        User alice = makeUser("Alice");
        UserSession session = new UserSession();
        session.setUser(alice);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, alice);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData out = outputHistory.get(0);
        assertFalse(out.isSuccess());
        assertFalse(out.isMutual());
        assertEquals("Alice", out.getMatchedUserName());
        assertEquals("You can't connect with yourself.", out.getMessage());
    }

    @Test
    void alreadyFriends_shortCircuits_withMessage() {
        User alice = makeUser("Alice");
        User bob = makeUser("Bob");
        alice.addFriend(bob);

        UserSession session = new UserSession();
        session.setUser(alice);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.connect(session, bob);

        assertEquals(1, outputHistory.size());
        MatchInteractionOutputData out = outputHistory.get(0);
        assertTrue(out.isSuccess());
        assertTrue(out.isMutual());
        assertEquals("Bob", out.getMatchedUserName());
        assertTrue(out.getMessage().toLowerCase().contains("already friends"));
    }

    @Test
    void skip_removesFromIncomingAndOutgoing_noPresenterCall() {
        User alice = makeUser("Alice");
        User bob = makeUser("Bob");

        UserSession session = new UserSession();
        session.setUser(alice);
        session.getIncomingMatches().add(bob);
        session.getOutgoingMatches().add(bob);

        MatchInteractionInteractor interactor =
                new MatchInteractionInteractor(
                        mockDAO, mockRequestSender, mockFriendAdder, mockPresenter);

        interactor.skip(session, bob);

        assertFalse(session.getIncomingMatches().contains(bob));
        assertFalse(session.getOutgoingMatches().contains(bob));
        assertTrue(outputHistory.isEmpty());
    }
}
