package app.addComment;

import data_access.PostDataAccessInterface;
import entities.Comment;
import entities.Post;
import entities.UserSession;
import usecase.AddComment;

import java.time.LocalDateTime;

public class AddCommentImpl implements AddComment {
    private final PostDataAccessInterface postDAO;

    public AddCommentImpl(PostDataAccessInterface postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public void addComment(UserSession userSession, Post post, String comment) {
        Comment newComment = new Comment(comment, userSession.getUser().getName(), LocalDateTime.now());

        post.getComments().add(newComment);  // update in memory
        postDAO.savePost(post.getAuthor(), post);  // update the DAO

    }
}
