package app.frameworks_and_drivers.view;

/**
 * Interface for displaying the result of adding a comment. This view interface is used by the
 * presenter to communicate success or failure messages to the user interface.
 */
public interface AddCommentViewInterface {
    /**
     * Renders a message to the user indicating the result of the comment operation.
     *
     * @param message the message to display (e.g., success or error)
     * @param isSuccess true if the comment was added successfully; false otherwise
     */
    void render(String message, boolean isSuccess);
}
