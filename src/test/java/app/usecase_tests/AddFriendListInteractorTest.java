package app.usecase_tests;

import app.interface_adapter.presenter.AddFriendListPresenter;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.entities.MatchFilter;
import app.entities.User;
import app.usecase.add_friend_list.AddFriendListOutputBoundary;
import app.usecase.add_friend_list.AddFriendListOutputData;
import app.usecase.handle_friend_request.HandleFriendRequestOutputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestOutputData;
import org.junit.Test;
import app.usecase.add_friend_list.AddFriendListInputBoundary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddFriendListInteractorTest {
    @Test
    public void testAddFriends() {
        List<String> emptyList = new ArrayList<String>();
        MatchFilter generalMF = new MatchFilter(0, 100, null, null);
        User user0 =
                new User(
                        "Stan",
                        20,
                        "male",
                        "Toronto",
                        "I luv lord",
                        emptyList,
                        emptyList,
                        emptyList);
        User user1 =
                new User(
                        "user01",
                        18,
                        "female",
                        "North York",
                        "Let's go out!",
                        emptyList,
                        emptyList,
                        emptyList);
        // Senario: user1 has been matched with user0 in her matching room and clicks "connect"
        // button
        // which sends match request to user0. User0 finds this in his Match Request tab and also
        // clicks "connect"
        AddFriendListPresenter presenter = new AddFriendListPresenter();
        AddFriendListInputBoundary addFriendListInputBoundary =
                new AddFriendListInteractor(presenter);

        addFriendListInputBoundary.addFriend(user0, user1);

        assertTrue(user0.getFriendList().contains(user1)); // user0 has user1 added as friend
        assertTrue(user1.getFriendList().contains(user0)); // user0 has user1 added as friend
    }

    @Test
    public void testAddFriendsNull() {
        AtomicReference<String> error = new AtomicReference<String>();

        List<String> emptyList = new ArrayList<String>();
        User user0 =
                new User(
                        "Stan",
                        20,
                        "male",
                        "Toronto",
                        "I luv lord",
                        emptyList,
                        emptyList,
                        emptyList);

        AddFriendListOutputBoundary dummyPresenter =
                new AddFriendListOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            AddFriendListOutputData outputData) {
                        System.out.println("Success: " + outputData.getMessage());
                    }

                    @Override
                    public void prepareFailView(String errorMessage) {
                        error.set(errorMessage);
                    }
                };

        AddFriendListInputBoundary interactor = new AddFriendListInteractor(dummyPresenter);

        interactor.addFriend(user0, null);
        assertEquals("Cannot add null or self as friend", error.get());
    }
}
