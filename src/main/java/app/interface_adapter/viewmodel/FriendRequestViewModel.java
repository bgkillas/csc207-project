package app.interface_adapter.viewmodel;

import app.entities.User;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestViewModel {
    private final List<User> incomingRequests;
    private int currentIndex = 0;

    public FriendRequestViewModel() {
        this.incomingRequests = new ArrayList<>();
    }

    public void setIncomingRequests(List<User> requests) {
        incomingRequests.clear();
        incomingRequests.addAll(requests);
        currentIndex = 0;
    }

    public User getCurrentUser() {
        if (incomingRequests.isEmpty() || currentIndex >= incomingRequests.size()) {
            return null;
        }
        return incomingRequests.get(currentIndex);
    }

    public void removeCurrentRequest() {
        if (!incomingRequests.isEmpty() && currentIndex < incomingRequests.size()) {
            incomingRequests.remove(currentIndex);
        }
    }

    public boolean hasRequests() {
        return !incomingRequests.isEmpty();
    }

    public List<User> getAllRequests() {
        return new ArrayList<>(incomingRequests);
    }
}
