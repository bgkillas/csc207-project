package app.usecase.add_comment;


import java.time.LocalDateTime;

public class AddCommentOutputData {
    private final String text;
    private final String author;
    private final LocalDateTime date;
    private final String message;

    public AddCommentOutputData(String text, String author, LocalDateTime date, String message) {
        this.text = text;
        this.author = author;
        this.date = date;
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}