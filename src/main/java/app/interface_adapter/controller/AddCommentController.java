package app.interface_adapter.controller;

import app.entities.Post;
import app.entities.UserSession;
import app.usecase.add_comment.AddCommentInputBoundary;

/**
 * Controller for handling the addition of comments to a post.
 * This class receives input from the user interface then hands off
 * the task of adding a comment to the interactor.
 */
public class AddCommentController {
    private final AddCommentInputBoundary interactor;

    /**
     * Constructs a new {AddCommentController} with the given interactor.
     *
     * @param interactor the input boundary that handles the add comment use case.
     */

    public AddCommentController(AddCommentInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Adds a comment to the specified post for the given user session.
     *
     * @param session the current user session
     * @param post    the post to which the comment is being added
     * @param comment the content of the comment
     */
    public void addComment(UserSession session, Post post, String comment) {
        this.interactor.addComment(session, post, comment);
    }
}
