package data_access;

import entities.Post;
import entities.User;
import java.util.List;

public interface PostDataAccessInterface {
    List<Post> getPostsByUser(User user);
    void savePost(User user, Post post);
}
