package app.interface_adapter.controller;

import app.entities.Post;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import java.io.File;

public class OpenPostController {
    private final PostDataAccessInterface postDAO;

    public OpenPostController(PostDataAccessInterface postDAO) {
        this.postDAO = postDAO;
    }

    public OpenPostController() {
        this.postDAO = null;
    }

    public Post getPost(String postId) {
        // This would typically get the post by ID from the data access layer
        // For now, we'll return null and handle this in the view
        return null;
    }

    public void postNewPost(String title, String content, File image) {}
}
