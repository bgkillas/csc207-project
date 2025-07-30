package app.usecase.add_comment;

import app.entities.Post;
import app.entities.UserSession;

/**
 * Input boundary for the Add Comment use case.
 * <p>
 * Defines the method that controllers or UI layers call to add a comment
 * to a post on behalf of the current user.
 */
public interface AddCommentInputBoundary {
    /**
     * Adds a comment to the specified post by the current user.
     *
     * @param userSession The current user's session.
     * @param post        The post to which the comment will be added.
     * @param comment The text content of the comment.
     */
    void addComment(UserSession userSession, Post post, String comment);
}
