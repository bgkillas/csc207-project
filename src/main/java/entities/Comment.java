package entities;

import java.time.LocalDateTime;

public class Comment {
    private String text;
    private String author;
    private LocalDateTime date;

    public Comment(String text, String author, LocalDateTime date) {
        this.text = text;
        this.author = author;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
