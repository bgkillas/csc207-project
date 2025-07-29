package usecase.add_comment;

import entities.Post;
import entities.UserSession;

public interface AddCommentInputBoundary {
    void addComment(UserSession userSession, Post post, String comment);
}
