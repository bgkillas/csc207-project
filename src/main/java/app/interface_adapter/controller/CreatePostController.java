package app.interface_adapter.controller;

import app.usecase.create_post.CreatePostInteractor;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.entities.User;
import java.io.File;

public class CreatePostController {
    // This controller handles the request to create and post a new post.
    private final CreatePostInteractor interactor;

    public CreatePostController() {
        this.interactor = new CreatePostInteractor(new InMemoryPostDataAccessObject());
    }

    public CreatePostController(CreatePostInteractor interactor) {
        this.interactor = interactor;
    }

    public void postNewPost(String title, String content, File image, User author) {
        interactor.createPost(title, content, image, author);
    }
}
