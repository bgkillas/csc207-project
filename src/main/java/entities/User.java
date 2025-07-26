package entities;

import java.util.ArrayList;
import java.util.List;

/** Represents a user in the system. */
public class User {
    private final String name;
    private int age;
    private String gender;
    private String location; // subjective to change (a dropdown list of options)
    private String bio;
    private List<String> favGenres;
    private List<String> favArtists;
    private List<String> favSongs;
    private MatchFilter matchFilter;
    private final List<User> friendList = new ArrayList<>();
    private final List<User> blockList = new ArrayList<>();
    private java.awt.Image profilePicture;

    /**
     * Constructs a User with the given attributes.
     *
     * @param name the user's name
     * @param age the user's age
     * @param gender the user's gender
     * @param location the user's location
     * @param bio the user's biography
     * @param favGenres the user's favorite genres
     * @param favArtists the user's favorite artists
     * @param favSongs the user's favorite songs
     */
    public User(
            String name,
            int age,
            String gender,
            String location,
            String bio,
            List<String> favGenres,
            List<String> favArtists,
            List<String> favSongs) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.bio = bio;
        this.favGenres = favGenres;
        this.favArtists = favArtists;
        this.favSongs = favSongs;
        this.matchFilter = new MatchFilter(0, 100, "N/A", "N/A");
    }

    /**
     * Sets the match filter for this user.
     *
     * @param filter the match filter
     */
    public void setMatchFilter(MatchFilter filter) {
        this.matchFilter = filter;
    }

    /**
     * Returns the user's match filter.
     *
     * @return the match filter
     */
    public MatchFilter getMatchFilter() {
        return this.matchFilter;
    }

    /**
     * Returns the user's list of friends.
     *
     * @return the friend list
     */
    public List<User> getFriendList() {
        return friendList;
    }

    /**
     * Adds a friend to this user's friend list.
     *
     * @param other the user to be added as a friend
     */
    public void addFriend(User other) {
        if (other == null || other.equals(this)) {
            return;
        }
        if (!friendList.contains(other)) {
            friendList.add(other);
        }
    }

    /**
     * Returns the user's list of blocked users.
     *
     * @return the block list
     */
    public List<User> getBlockList() {
        return blockList;
    }

    /**
     * Adds a user to this user's block list.
     *
     * @param other the user to be added as a blocked user
     */
    public void addBlock(User other) {
        if (other == null || other.equals(this)) {
            return;
        }
        friendList.remove(other);
        if (!blockList.contains(other)) {
            blockList.add(other);
        }
    }

    /**
     * Removes a user to this user's block list.
     *
     * @param other the user to be removed as a blocked user
     */
    public void removeBlock(User other) {
        if (other == null || other.equals(this)) {
            return;
        }
        blockList.remove(other);
    }

    /**
     * checks if a user is in this user's block list.
     *
     * @param other the user to be removed as a blocked user
     * @return if the user is in the block list or not
     */
    public boolean hasBlock(User other) {
        return blockList.contains(other);
    }

    /**
     * Returns the user's favorite artists.
     *
     * @return the favorite artists
     */
    public List<String> getFavArtists() {
        return favArtists;
    }

    /**
     * Returns the user's favorite genres.
     *
     * @return the favorite genres
     */
    public List<String> getFavGenres() {
        return favGenres;
    }

    public List<String> getFavSongs() {
        return favSongs;
    }

    /** updates the user's favorite artists. */
    public void setFavArtists(List<String> ArtistsList) {
        this.favArtists = ArtistsList;
    }

    /** updates the user's favorite genres. */
    public void setFavGenres(List<String> genresList) {
        this.favGenres = genresList;
    }

    /** updates the user's favorite genres. */
    public void setFavSongs(List<String> songsList) {
        this.favSongs = songsList;
    }

    /**
     * Returns the user's gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the user's age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the user's location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the user's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return this.bio;
    }

    public java.awt.Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(java.awt.Image profilePicture) {
        this.profilePicture = profilePicture;
    }

}
