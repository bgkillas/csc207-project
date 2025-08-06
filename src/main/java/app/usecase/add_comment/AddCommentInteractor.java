package app.usecase.add_comment;

import app.entities.Comment;
import app.entities.Post;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import java.time.LocalDateTime;

/**
 * Use case interactor for adding a comment to a post.
 *
 * <p>This class handles the logic for creating a new Comment entity based on the current user's
 * session and a comment string, appending it to the given Post, and updating the post through the
 * data access layer. The outcome is passed to the presenter through the output boundary.
 */
public class AddCommentInteractor implements AddCommentInputBoundary {
    private final PostDataAccessInterface postDataAccessObject;
    private final AddCommentOutputBoundary presenter;

    /**
     * Creates the AddComment use case interactor.
     *
     * @param postDataAccessObject The data access object used to persist post updates.
     * @param presenter The presenter that will handle success or failure output.
     */
    public AddCommentInteractor(
            PostDataAccessInterface postDataAccessObject, AddCommentOutputBoundary presenter) {
        this.postDataAccessObject = postDataAccessObject;
        this.presenter = presenter;
    }

    /**
     * Adds a new comment to the specified post.
     *
     * @param userSession The current session containing the user adding the comment.
     * @param post The post to which the comment will be added.
     * @param commentText The text content of the comment.
     */
    @Override
    public void addComment(UserSession userSession, Post post, String commentText) {
        if (commentText == null || commentText.trim().isEmpty()) {
            presenter.presentAddCommentFailure("Comment cannot be empty.");
            return;
        }

        Comment newComment = new Comment(commentText, userSession.getUser().getName(),
                LocalDateTime.now());
        // UserSession update
        post.getComments().add(newComment);

        AddCommentOutputData outputData =
                new AddCommentOutputData(
                        newComment.getText(),
                        newComment.getAuthor(),
                        newComment.getDate(),
                        "Comment added successfully.");
        presenter.presentAddCommentSuccess(outputData);
    }
}
