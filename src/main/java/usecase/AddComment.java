package usecase;

import entities.Post;
import entities.UserSession;

public interface AddComment {
    void addComment(UserSession userSession, Post post, String comment);
}
