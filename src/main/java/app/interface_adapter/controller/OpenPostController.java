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

}
