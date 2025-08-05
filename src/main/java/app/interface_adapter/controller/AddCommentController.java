package app.interface_adapter.controller;

import app.entities.Post;
import app.entities.UserSession;
import app.usecase.add_comment.AddCommentInputBoundary;

public class AddCommentController {
    private final AddCommentInputBoundary interactor;

    public AddCommentController(AddCommentInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void addComment(UserSession session, Post post, String comment) {
        this.interactor.addComment(session, post, comment);
    }
}
