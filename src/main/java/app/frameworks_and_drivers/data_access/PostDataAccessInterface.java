package app.frameworks_and_drivers.data_access;

import app.entities.Post;
import app.entities.User;
import java.util.List;

public interface PostDataAccessInterface {
    List<Post> getPostsByUser(User user);

    void savePost(User user, Post post);
}