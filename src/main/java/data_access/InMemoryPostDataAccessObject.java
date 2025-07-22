package data_access;

import entities.Post;
import entities.User;

import java.util.*;

public class InMemoryPostDataAccessObject implements PostDataAccessInterface {
    private final Map<User, List<Post>> userPosts = new HashMap<>();

    @Override
    public List<Post> getPostsByUser(User user) {
        return new ArrayList<>(userPosts.getOrDefault(user, new ArrayList<>()));
    }

    @Override
    public void savePost(User user, Post post) {
        userPosts.computeIfAbsent(user, k -> new ArrayList<>()).add(post);
    }
}
