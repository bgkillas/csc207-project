package app.interface_adapter.controller;

import app.frameworks_and_drivers.data_access.PostDataAccessInterface;

public class OpenPostController {
    private final PostDataAccessInterface postDataAccessObject;

    public OpenPostController(PostDataAccessInterface postDataAccessObject) {
        this.postDataAccessObject = postDataAccessObject;
    }

    public OpenPostController() {
        this.postDataAccessObject = null;
    }
}
