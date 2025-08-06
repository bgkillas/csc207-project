package app.frameworks_and_drivers.view;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.InMemoryMatchDataAccessObject;
import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.frameworks_and_drivers.data_access.PostDataAccessInterface;
import app.interface_adapter.controller.*;
import app.interface_adapter.presenter.*;
import app.interface_adapter.viewmodel.FriendRequestViewModel;
import app.usecase.add_friend_list.AddFriendListInputBoundary;
import app.usecase.add_friend_list.AddFriendListInteractor;
import app.usecase.add_friend_list.AddFriendListOutputBoundary;
import app.usecase.create_post.CreatePostInteractor;
import app.usecase.handle_friend_request.HandleFriendRequestInteractor;
import app.usecase.login.LoginManager;
import app.usecase.login.LoginManagerMemory;
import app.usecase.match_interaction.MatchInteractionInteractor;
import app.usecase.matchfilter.SetupMatchFilterInteractor;
import app.usecase.matchfilter.SetupMatchFilterOutputBoundary;
import app.usecase.user_profile_setup.SetupUserProfileInteractor;
import app.usecase.user_profile_setup.SetupUserProfileOutputBoundary;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.function.Function;
import javax.swing.*;

/** DebugMenuView functions as a debugging panel It displays buttons to launch all UI views. */
public class DebugMenuView {

    /**
     * Creates the debug panel with a vertical list of buttons, one for each view Clicking a button
     * opens the corresponding view in a new JFrame.
     */
    public static JPanel create(UserSession session) throws NoSuchAlgorithmException {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1)); // vertical list of buttons

        // Shared dummy user and login manager
        LoginManager loginManager = new LoginManagerMemory();
        User dummyUser =
                new User(
                        "debugUser",
                        25,
                        "Other",
                        "DebugLand",
                        "Debugging",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        User user1 =
                new User(
                        "thing1",
                        20,
                        "Other",
                        "DebugLand",
                        "Debugging",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        User user2 =
                new User(
                        "thing3",
                        20,
                        "Other",
                        "DebugLand",
                        "Debugging",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        User user3 =
                new User(
                        "thing2",
                        20,
                        "Other",
                        "DebugLand",
                        "Debugging",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        User user4 =
                new User(
                        "thing2",
                        20,
                        "Other",
                        "DebugLand",
                        "Debugging",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        user1.addFriend(dummyUser);
        user2.addFriend(dummyUser);
        user3.addFriend(dummyUser);
        user2.addFriend(user1);
        user1.addFriend(user2);
        user1.addFriend(user4);
        dummyUser.addFriend(user1);
        dummyUser.addFriend(user2);
        dummyUser.addFriend(user3);
        dummyUser.addBlock(user4);
        session.setUser(dummyUser);
        session.getIncomingMatches().add(user1);
        session.getIncomingMatches().add(user2);
        session.getIncomingMatches().add(user3);

        // Controllers for views that require them NEWWW
        /*        SetupUserProfileOutputBoundary profilePresenter = new SetupUserProfilePresenter();
        SetupUserProfileController profileController =
                new SetupUserProfileController(
                        new SetupUserProfileInteractor(profilePresenter, session));*/

        /*
                // Profile Setup: create frame and inject it into presenter
                JFrame profileFrame = new JFrame("Set Up Profile");
                SetupUserProfileOutputBoundary profilePresenter =
                    new SetupUserProfilePresenter(profileFrame);
                SetupUserProfileController profileController =
                        new SetupUserProfileController(
                                new SetupUserProfileInteractor(profilePresenter, session));
        */

        JFrame frame = new JFrame();
        PostDataAccessInterface postDataAccessObject = new InMemoryPostDataAccessObject();
        SetupMatchFilterOutputBoundary matchFilterPresenter =
                new SetupMatchFilterPresenter(frame, session, postDataAccessObject);

        SetupMatchFilterController matchFilterController =
                new SetupMatchFilterController(
                        new SetupMatchFilterInteractor(matchFilterPresenter, session));

        /*CreateAccountOutputBoundary createPresenter =
                new CreateAccountPresenter(null, profileController);
        CreateAccountController createController =
                new CreateAccountController(
                        new CreateAccountInteractor(createPresenter, session, loginManager));*/

        // Controller for post feed view
        CreatePostInteractor createPostInteractor = new CreatePostInteractor();
        PostFeedController postFeedViewController = new PostFeedController(createPostInteractor);

        // Controller for create post view
        CreatePostController createPostViewController = new CreatePostController();

        // Controller for create post view
        OpenPostController openPostController = new OpenPostController();

        // Add buttons to open each view
        addButtonWithFrame(
                panel,
                "BlockListView",
                tempFrame ->
                        new BlockListView(dummyUser, session, tempFrame, postDataAccessObject)
                                .create());
        addButtonWithFrame(
                panel,
                "BuddyListView",
                tempFrame ->
                        new BuddyListView(dummyUser, session, tempFrame, postDataAccessObject)
                                .create());
        addButtonWithFrame(
                panel,
                "ConnectRequestView",
                tempFrame -> {
                    FriendRequestViewModel viewModel = new FriendRequestViewModel();
                    viewModel.setIncomingRequests(session.getIncomingMatches());
                    FriendRequestPresenter presenter = new FriendRequestPresenter(viewModel);
                    AddFriendListOutputBoundary addFriendPresenter = new AddFriendListPresenter();
                    AddFriendListInputBoundary addFriendInteractor =
                            new AddFriendListInteractor(addFriendPresenter);

                    HandleFriendRequestInteractor interactor =
                            new HandleFriendRequestInteractor(
                                    // or your actual DataAccessObject
                                    new InMemoryMatchDataAccessObject(),
                                    addFriendInteractor,
                                    presenter);
                    FriendRequestController controller = new FriendRequestController(interactor);

                    return new ConnectRequestView(
                            tempFrame,
                            dummyUser,
                            session,
                            controller,
                            viewModel,
                            postDataAccessObject);
                });

        // NEEDS TO BE FIXED (actionListeners don't work unless view
        // is accessed through PostFeedView
        addButtonWithFrame(
                panel,
                "CreatePostView",
                tempFrame -> {
                    CreatePostInteractor interactor =
                            new CreatePostInteractor(postDataAccessObject);
                    CreatePostController controller = new CreatePostController(interactor);
                    return new CreatePostView(dummyUser, session, tempFrame, postDataAccessObject)
                            .create(controller);
                });

        /*    addButton(panel, "LoginView", () -> LoginView.create(loginManager, createController));
         */
        addButtonWithFrame(
                panel,
                "MatchFilterSetupView",
                tempFrame -> MatchFilterSetupView.create(matchFilterController, frame));

        addButtonWithFrame(
                panel,
                "MatchingRoomView",
                tempFrame -> {
                    MatchInteractionPresenter matchPresenter = new MatchInteractionPresenter();

                    InMemoryMatchDataAccessObject matchDataAccessObject =
                            new InMemoryMatchDataAccessObject();

                    AddFriendListPresenter addFriendPresenter = new AddFriendListPresenter();
                    AddFriendListInteractor addFriendInteractor =
                            new AddFriendListInteractor(addFriendPresenter);

                    FriendRequestViewModel requestViewModel = new FriendRequestViewModel();
                    FriendRequestPresenter requestPresenter =
                            new FriendRequestPresenter(requestViewModel);
                    HandleFriendRequestInteractor friendRequestInteractor =
                            new HandleFriendRequestInteractor(
                                    matchDataAccessObject, addFriendInteractor, requestPresenter);

                    MatchInteractionInteractor interactor =
                            new MatchInteractionInteractor(
                                    matchDataAccessObject,
                                    friendRequestInteractor,
                                    addFriendInteractor,
                                    matchPresenter);

                    MatchInteractionController controller =
                            new MatchInteractionController(interactor);

                    return new MatchingRoomView(
                            tempFrame,
                            dummyUser,
                            Collections.singletonList(dummyUser),
                            session,
                            controller,
                            postDataAccessObject);
                });

        addButtonWithFrame(
                panel,
                "PostFeedView",
                tempFrame -> {
                    User currentUser = session.getUser();
                    return new PostFeedView(currentUser, session, tempFrame)
                            .create(postFeedViewController);
                });
        /*        addButton( NEWWW
        panel,
        "ProfileSetupView",
        () -> ProfileSetupView.create(profileController, session.getUser()));*/

        addButtonWithFrame(
                panel,
                "ProfileSetupView",
                tempFrame -> {
                    // Recreate the presenter and controller using this new tempFrame
                    SetupUserProfileOutputBoundary tempPresenter =
                            new SetupUserProfilePresenter(tempFrame);
                    SetupUserProfileController tempController =
                            new SetupUserProfileController(
                                    new SetupUserProfileInteractor(tempPresenter, session));
                    return ProfileSetupView.create(tempController, session.getUser());
                });

        addButtonWithFrame(
                panel,
                "ProfileView",
                tempFrame ->
                        new ProfileView(
                                session.getUser(), tempFrame, session, postDataAccessObject));

        return panel;
    }

    /**
     * Adds a button to the debug menu that opens the given view in a new window.
     *
     * @param panel The debug panel to add the button to
     * @param name The name of the view
     * @param viewSupplier A method reference that returns a JComponent
     */
    private static void addButtonWithFrame(
            JPanel panel, String name, Function<JFrame, JComponent> viewSupplier) {
        JButton button = new JButton("Open " + name);
        button.addActionListener(
                e -> {
                    JFrame frame = new JFrame(name);
                    frame.setContentPane(viewSupplier.apply(frame));
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.pack(); // automatically size frame to fit contents
                    frame.setVisible(true);
                });
        panel.add(button);
    }
}
