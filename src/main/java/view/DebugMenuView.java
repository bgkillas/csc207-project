package view;

import account.login.CreateAccountController;
import account.login.LoginManager;
import account.login.LoginManagerMemory;
import account.login.SetupMatchFilterController;
import account.login.SetupUserProfileController;
import app.individual_story.CreatePostInteractor;
import entities.User;
import entities.UserSession;
import interface_adapter.controller.CreatePostController;
import interface_adapter.controller.OpenPostController;
import interface_adapter.controller.PostFeedController;
import interface_adapter.presentor.CreateAccountPresenter;
import interface_adapter.presentor.SetupMatchFilterPresenter;
import interface_adapter.presentor.SetupUserProfilePresenter;
import usecase.team_story.*;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.function.Function;
import entities.Post;
import data_access.PostDataAccessInterface;
import data_access.InMemoryPostDataAccessObject;

/** DebugMenuView functions as a debugging panel It displays buttons to launch all UI views */
public class DebugMenuView {

    /**
     * Creates the debug panel with a vertical list of buttons, one for each view Clicking a button
     * opens the corresponding view in a new JFrame
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

        // Controllers for views that require them
        SetupUserProfileOutputBoundary profilePresenter = new SetupUserProfilePresenter();
        SetupUserProfileController profileController =
                new SetupUserProfileController(
                        new SetupUserProfileInteractor(profilePresenter, session));

        JFrame frame = new JFrame();
        SetupMatchFilterOutputBoundary matchFilterPresenter =
                new SetupMatchFilterPresenter(frame, session);

        SetupMatchFilterController matchFilterController =
                new SetupMatchFilterController(
                        new SetupMatchFilterInteractor(matchFilterPresenter, session));

        CreateAccountOutputBoundary createPresenter =
                new CreateAccountPresenter(null, profileController);
        CreateAccountController createController =
                new CreateAccountController(
                        new CreateAccountInteractor(createPresenter, session, loginManager));

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
                tempFrame -> new BlockListView(dummyUser, session, tempFrame).create());
        addButtonWithFrame(
                panel,
                "BuddyListView",
                tempFrame -> new BuddyListView(dummyUser, session, tempFrame).create());
        addButtonWithFrame(
                panel,
                "ConnectRequestView",
                tempFrame -> new ConnectRequestView(tempFrame, dummyUser, session));

        // NEEDS TO BE FIXED (actionListeners don't work unless view
        // is accessed through PostFeedView
        addButton(
                panel,
                "CreatePostView",
                () -> {
                    PostDataAccessInterface postDAO = new InMemoryPostDataAccessObject();
                    CreatePostInteractor interactor = new CreatePostInteractor(postDAO);
                    CreatePostController controller = new CreatePostController(interactor);
                    return new CreatePostView(dummyUser, session, frame, postDAO)
                            .create(controller);
                });

        addButton(panel, "LoginView", () -> LoginView.create(loginManager, createController));
        addButton(
                panel,
                "MatchFilterSetupView",
                () -> MatchFilterSetupView.create(matchFilterController));
        addButtonWithFrame(
                panel,
                "MatchingRoomView",
                tempFrame ->
                        new MatchingRoomView(
                                tempFrame,
                                dummyUser,
                                Collections.singletonList(dummyUser),
                                session));
        addButton(
                panel,
                "OpenPostView",
                () -> new OpenPostView(dummyUser, session, frame).create(openPostController));
        addButtonWithFrame(
                panel,
                "PostFeedView",
                tempFrame -> {
                    User currentUser = session.getUser(); // 确保 User 不为 null
                    return new PostFeedView(currentUser, session, tempFrame)
                            .create(postFeedViewController);
                });
        addButton(panel, "ProfileSetupView", () -> ProfileSetupView.create(profileController, session.getUser()));
        addButtonWithFrame(
                panel, "ProfileView", tempFrame -> new ProfileView(session.getUser(), tempFrame, session));

        return panel;
    }

    /**
     * Adds a button to the debug menu that opens the given view in a new window
     *
     * @param panel The debug panel to add the button to
     * @param name The name of the view
     * @param viewSupplier A method reference that returns a JComponent
     */
    private static void addButton(JPanel panel, String name, Supplier<JComponent> viewSupplier) {
        JButton button = new JButton("Open " + name);
        button.addActionListener(
                e -> {
                    JFrame frame = new JFrame(name);
                    frame.setContentPane(viewSupplier.get());
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.pack(); // automatically size frame to fit contents
                    frame.setVisible(true);
                });
        panel.add(button);
    }

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

    // Minimal functional interface used to pass component constructors or static create methods
    @FunctionalInterface
    private interface Supplier<T> {
        T get();
    }
}
