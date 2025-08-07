package app.entities;

import app.frameworks_and_drivers.data_access.MatchDataAccessInterface;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.frameworks_and_drivers.data_access.UserDataAccessInterface;
import app.frameworks_and_drivers.external.spotify.SpotifyInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the session of a currently logged-in user. Maintains the user's state, including
 * friend requests, matches, posts, all users, and optional Spotify data if connected.
 */
public class UserSession {
    private User user;
    private final List<User> incomingFriendRequest;
    private final List<User> outgoingFriendRequest;
    private final List<Match> matches;
    private List<Post> posts;
    private List<User> allUsers = new ArrayList<>();
    private SpotifyInterface spotify;

    /**
     * Constructs a UserSession for the given user and data access objects.
     *
     * @param user the current user.
     * @param userDataAccessObject user data access object.
     * @param matchDataAccessObject match data access object.
     * @param postDataAccessObject post data access object.
     */
    public UserSession(
            User user,
            UserDataAccessInterface userDataAccessObject,
            MatchDataAccessInterface matchDataAccessObject,
            PostDataAccessInterface postDataAccessObject) {
        this.allUsers = new ArrayList<>();
        this.incomingFriendRequest = new ArrayList<>();
        this.outgoingFriendRequest = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.posts = new ArrayList<>();

        List<User> fromDataAccessObjectIn = matchDataAccessObject.getIncomingFriendRequest(user);
        if (fromDataAccessObjectIn != null) {
            this.incomingFriendRequest.addAll(fromDataAccessObjectIn);
        }

        List<User> fromDataAccessObjectOut = matchDataAccessObject.getOutgoingFriendRequest(user);
        if (fromDataAccessObjectOut != null) {
            this.outgoingFriendRequest.addAll(fromDataAccessObjectOut);
        }

        List<Match> fromMatches = matchDataAccessObject.getMatches(user);
        if (fromMatches != null) {
            this.matches.addAll(fromMatches);
        }

        List<Post> fromPosts = postDataAccessObject.getPostsByUser(user);
        if (fromPosts != null) {
            this.posts.addAll(fromPosts);
        }

        this.allUsers =
                userDataAccessObject.getUsers() != null
                        ? userDataAccessObject.getUsers()
                        : new ArrayList<>();

        this.setUser(user);
    }

    /**
     * Constructs a UserSession for the given user.
     *
     * @param user the current user
     */
    public UserSession(User user) {
        this.incomingFriendRequest = new ArrayList<>();
        this.outgoingFriendRequest = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.setUser(user);
    }

    /**
     * Temporary no-argument constructor to allow creating an empty session Use for demo; for full
     * implementation, use constructor that takes a User.
     */
    public UserSession() {
        this.user = null;
        this.incomingFriendRequest = new ArrayList<>();
        this.outgoingFriendRequest = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    /**
     * Returns the list of all users known to this session.
     *
     * @return the list of all users
     */
    public List<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Adds a user to the list of all users.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        allUsers.add(user);
    }

    /**
     * Initializes the Spotify API and pulls user-level metadata.
     *
     * @param spotify the Spotify interface implementation to initialize
     */
    public void initiateSpotify(SpotifyInterface spotify) {
        this.spotify = spotify;
        spotify.initSpotify();
        spotify.pullUserData();
    }

    /**
     * Returns the current user's Spotify username.
     *
     * @return the Spotify username
     */
    public String getUserName() {
        return spotify.getUserName();
    }

    /**
     * Returns the current user's Spotify ID.
     *
     * @return the Spotify user ID
     */
    public String getUserId() {
        return spotify.getUserId();
    }

    /** Updates the user's top tracks, artists, and genres using Spotify data. */
    public void updateSpotify() {
        if (this.user != null && this.spotify != null) {
            spotify.pullTopArtistsAndGenres();
            this.user.setFavArtists(spotify.getTopArtists());
            this.user.setFavGenres(spotify.getTopGenres());
            spotify.pullTopTracks();
            this.user.setFavSongs(spotify.getTopTracks());
        }
    }

    /**
     * Sets the current user for this session and updates Spotify preferences if available. If the
     * user has no existing friends, two default friends ("Diana" and "Eric") are added with mutual
     * friendship links. It also adds three dummy incoming friend requests from themed users:
     * "Java", "Python", and "C++". (for demo)
     *
     * @param user the user to set as the current session user
     */
    public void setUser(User user) {
        this.user = user;
        this.updateSpotify();
        if (user.getFriendList().isEmpty()) {
            addDefaultFriends();
        }
        addDummyIncomingRequests();
    }

    /**
     * Adds two default friends ("Diana" and "Eric") to the current user if the user has no friends.
     * The default users are music-themed and represent sample friend connections for demonstration
     * or testing purposes. Mutual friendship links are established.
     */
    private void addDefaultFriends() {
        User diana =
                new User(
                        "Diana",
                        21,
                        "female",
                        "Vancouver",
                        "EDM is life!",
                        List.of("EDM", "Pop"),
                        List.of("Zedd", "Avicii"),
                        List.of("Clarity", "Wake Me Up"));
        User eric =
                new User(
                        "Eric",
                        25,
                        "male",
                        "Calgary",
                        "Guitarist and metalhead",
                        List.of("Metal", "Rock"),
                        List.of("Metallica", "Nirvana"),
                        List.of("Enter Sandman", "Smells Like Teen Spirit"));

        this.addUser(diana);
        this.addUser(eric);

        this.user.getFriendList().add(diana);
        this.user.getFriendList().add(eric);

        diana.getFriendList().add(this.user);
        eric.getFriendList().add(this.user);
    }

    /**
     * Adds three dummy incoming friend requests to the current session user. The requests are from
     * themed users: "Java", "Python", and "C++", each with distinct music preferences and bios.
     * These users are also added to the global user list so that they can be referenced throughout
     * the app. Intended for demonstration, UI testing, or placeholder data purposes.
     */
    private void addDummyIncomingRequests() {
        User alice =
                new User(
                        "Java",
                        22,
                        "female",
                        "Toronto",
                        "Indie lover, always looking for concert buddies",
                        List.of("Indie", "Folk"),
                        List.of("Phoebe Bridgers", "Bon Iver"),
                        List.of("Motion Sickness", "Skinny Love"));

        User bob =
                new User(
                        "Python",
                        24,
                        "male",
                        "Toronto",
                        "Hip-hop fan and amateur DJ",
                        List.of("Hip-hop", "Rap"),
                        List.of("Kendrick Lamar", "Drake"),
                        List.of("HUMBLE.", "Hotline Bling"));

        User charlie =
                new User(
                        "C++",
                        27,
                        "non-binary",
                        "Montreal",
                        "Electronic vibes only",
                        List.of("Electronic", "House"),
                        List.of("Deadmau5", "Calvin Harris"),
                        List.of("Ghosts 'n' Stuff", "Summer"));

        // Add to global user list
        this.addUser(alice);
        this.addUser(bob);
        this.addUser(charlie);

        // Add to incoming friend requests
        this.addIncomingMatch(alice);
        this.addIncomingMatch(bob);
        this.addIncomingMatch(charlie);
    }

    /**
     * Returns the current user for this session.
     *
     * @return the current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the list of users who have sent match requests to the current user.
     *
     * @return the list of incoming match users
     */
    public List<User> getIncomingMatches() {
        return incomingFriendRequest;
    }

    /**
     * Returns the list of users to whom the current user has sent match requests.
     *
     * @return the list of outgoing match users
     */
    public List<User> getOutgoingMatches() {
        return outgoingFriendRequest;
    }

    /**
     * Returns the list of matches for the current user.
     *
     * @return the list of matches
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     * Adds a user to the list of incoming match requests.
     *
     * @param other the user who sent a match request
     */
    public void addIncomingMatch(User other) {
        incomingFriendRequest.add(other);
    }

    /**
     * Adds a user to the list of outgoing match requests.
     *
     * @param other the user to whom a match request was sent
     */
    public void addOutgoingMatch(User other) {
        outgoingFriendRequest.add(other);
    }

    /**
     * Adds a match to the list of matches for the current user.
     *
     * @param match the match to add
     */
    public void addMatch(Match match) {
        matches.add(match);
    }

    /**
     * Returns the list of posts created by the current user.
     *
     * @return the list of posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Sets the list of posts for the current user.
     *
     * @param posts the list of posts to assign
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * Adds a single post to the user's list of posts.
     *
     * @param post the post to add
     */
    public void addPost(Post post) {
        posts.add(post);
    }
}
