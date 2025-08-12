package app.entities;

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
    private final List<User> matchesTemp = new ArrayList<>();
    private List<Post> posts;
    private List<User> allUsers = new ArrayList<>();

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
     * Sets the current user for this session and updates Spotify preferences if available. If the
     * user has no existing friends, two default friends ("Diana" and "Eric") are added with mutual
     * friendship links. It also adds three dummy incoming friend requests from themed users:
     * "Java", "Python", and "C++". (for demo)
     *
     * @param user the user to set as the current session user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Adds two default friends ("Diana" and "Eric") to the current user if the user has no friends.
     * The default users are music-themed and represent sample friend connections for demonstration
     * or testing purposes. Mutual friendship links are established.
     */
    public void addDefaultFriends() {
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

        this.user.addFriend(diana);
        this.user.addFriend(eric);

        diana.addFriend(this.user);
        eric.addFriend(this.user);
    }


    /**
     * Set every user in the allUser attribute of this UserSession to have the same favGenres, favArtists, and favSongs
     * so that they are all considered compatible with the given user. This is for the Demo!
     *
     * @param user - logged in user
     */
    public void makeAllUsersCompatible(User user) {
        List<String> favGenres = user.getFavGenres();
        List<String> favArtists = user.getFavArtists();
        List<String> favSongs = user.getFavSongs();

        // for debugging, this allows us to see user's fav lists
        System.out.println("User's fav: " + favGenres + favArtists + favSongs);

        for (User u : allUsers) {
            u.setFavGenres(favGenres);
            u.setFavArtists(favArtists);
            u.setFavSongs(favSongs);
        }
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
     * Returns the list of all matches for the match view.
     *
     * @return the list of incoming match users
     */
    public List<User> getMatchesTemp() {
        return matchesTemp;
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
