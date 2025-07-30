package app.interface_adapter.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestViewModel {
    private final List<String> incomingRequests; // e.g., usernames or display names
    private int currentIndex = 0;

    public FriendRequestViewModel() {
        this.incomingRequests = new ArrayList<>();
    }

    public void setIncomingRequests(List<String> requests) {
        incomingRequests.clear();
        incomingRequests.addAll(requests);
        currentIndex = 0;
    }

    public String getCurrentRequest() {
        if (incomingRequests.isEmpty() || currentIndex >= incomingRequests.size()) {
            return null;
        }
        return incomingRequests.get(currentIndex);
    }

    public void removeCurrentRequest() {
        if (!incomingRequests.isEmpty() && currentIndex < incomingRequests.size()) {
            incomingRequests.remove(currentIndex);
            // do not increment index because list shrinks
        }
    }

    public boolean hasRequests() {
        return !incomingRequests.isEmpty();
    }

    public List<String> getAllRequests() {
        return new ArrayList<>(incomingRequests); // return a copy to protect internal list
    }
}
