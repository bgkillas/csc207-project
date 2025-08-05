package app.entities;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Post {
    private UUID id;
    private String title;
    private String text;
    private Image image;
    private LocalDateTime timestamp;
    private User author;
    private List<Comment> comments;

    public Post(
            String title,
            String text,
            Image image,
            LocalDateTime timestamp,
            User author,
            List<Comment> comments) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.text = text;
        this.image = image;
        this.timestamp = timestamp;
        this.author = author;
        this.comments = comments;
    }

    public Post() {
        this.id = null;
        this.title = "Untitled";
        this.text = "";
        this.image = null;
        this.timestamp = LocalDateTime.now();
        this.author = null;
        this.comments = new ArrayList<Comment>();
    }

    public UUID getId() {return this.id;}

    public void setId(UUID id) {this.id = id;}

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getTitle() {
        return this.title;
    }

    public User getAuthor() {
        return author;
    }

    public String getText() {
        return this.text;
    }

    public Image getImage() {
        return this.image;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }
}
