package app.interface_adapter.viewmodel;

import app.entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for managing and accessing a list of incoming friend requests. This class maintains the
 * list of requests and the current index, providing methods to navigate, remove, and retrieve
 * friend requests.
 */
public class FriendRequestViewModel {
    private final List<User> incomingRequests;
    private int currentIndex = 0;

    /** Constructs a new FriendRequestViewModel with an empty request list. */
    public FriendRequestViewModel() {
        this.incomingRequests = new ArrayList<>();
    }

    /**
     * Sets the incoming friend requests and resets the current index.
     *
     * @param requests a list of incoming User requests
     */
    public void setIncomingRequests(List<User> requests) {
        incomingRequests.clear();
        incomingRequests.addAll(requests);
        currentIndex = 0;
    }

    /**
     * Returns the User at the current index in the request list.
     *
     * @return the current user requesting friendship, or null if no requests are available
     */
    public User getCurrentUser() {
        if (incomingRequests.isEmpty() || currentIndex >= incomingRequests.size()) {
            return null;
        }
        return incomingRequests.get(currentIndex);
    }

    /**
     * Removes the user at the current index from the list of incoming requests. Does nothing if the
     * list is empty or the index is out of bounds.
     */
    public void removeCurrentRequest() {
        if (!incomingRequests.isEmpty() && currentIndex < incomingRequests.size()) {
            incomingRequests.remove(currentIndex);
        }
    }

    /**
     * Checks if there are any remaining friend requests.
     *
     * @return true if the request list is not empty, false otherwise
     */
    public boolean hasRequests() {
        return !incomingRequests.isEmpty();
    }

    /**
     * Returns a copy of all incoming friend requests.
     *
     * @return a list of User objects who sent requests
     */
    public List<User> getAllRequests() {
        return new ArrayList<>(incomingRequests);
    }
}
