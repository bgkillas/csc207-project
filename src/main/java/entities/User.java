package entities;
import java.util.*;

public class User {
    private final String name;
    private final int age;
    private final String gender;
    private final String location;
    private final String bio;
    private final List<String> favGenres;
    private final List<String> favArtists;
    private final List<String> favSongs;
    private MatchFilter matchFilter;
    private final List<User> friendList = new ArrayList<>();

    public User(String name, int age, String gender, String location, String bio,
                List<String> favGenres, List<String> favArtists, List<String> favSongs) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.bio = bio;
        this.favGenres = favGenres;
        this.favArtists = favArtists;
        this.favSongs = favSongs;
    }

    public void setMatchFilter(MatchFilter filter) {
        this.matchFilter = filter;
    }

    public MatchFilter getMatchFilter() {
        return this.matchFilter;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void addFriend(User other) {
        friendList.add(other);
    }

    public List<String> getFavArtists() {
        return favArtists;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
