package app.addComment;

import entities.Comment;
import entities.Post;
import entities.UserSession;
import usecase.AddComment;

import java.time.LocalDateTime;

public class AddCommentImpl implements AddComment {

    @Override
    public void addComment(UserSession userSession, Post post, String comment) {
        Comment newComment = new Comment(comment, userSession.getUser().getName(), LocalDateTime.now());
        post.getComments().add(newComment);

    }
}
