package app.interface_adapter.controller;

import app.frameworks_and_drivers.data_access.PostDataAccessInterface;

/**
 * Controller for handling actions related to opening a post.
 */
public class OpenPostController {
    private final PostDataAccessInterface postDataAccessObject;

    /**
     * Constructs an OpenPostController with the given PostDataAccessInterface.
     *
     * @param postDataAccessObject The data access object for posts
     */
    public OpenPostController(PostDataAccessInterface postDataAccessObject) {
        this.postDataAccessObject = postDataAccessObject;
    }

    /**
     * Default constructor. Creates a controller with no data access object.
     */
    public OpenPostController() {
        this.postDataAccessObject = null;
    }
}
