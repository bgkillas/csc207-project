package app.usecase.add_comment;

import java.time.LocalDateTime;

/**
 * Output data for the Add Comment use case.
 * This class holds the details of a comment that has been added,
 * including its content, author, timestamp, and an optional message
 * for the presenter to display.
 */
public class AddCommentOutputData {
    private final String text;
    private final String author;
    private final LocalDateTime date;
    private final String message;

    /**
     * Constructs a new {AddCommentOutputData} object.
     *
     * @param text    the text content of the comment
     * @param author  the username of the comment's author
     * @param date    the timestamp when the comment was created
     * @param message an optional message (e.g., success confirmation) for the presenter
     */
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
