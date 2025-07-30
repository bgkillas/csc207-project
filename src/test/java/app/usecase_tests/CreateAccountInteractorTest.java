package app.usecase_tests;

import app.entities.User;
import app.entities.UserSession;
import app.usecase.login.LoginManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import app.usecase.create_account.CreateAccountInteractor;
import app.usecase.create_account.CreateAccountOutputBoundary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the CreateAccountInteractor
 * Checks that a new user is registered using the LoginManager
 * Check that a User object is created and stored in UserSession
 * Checks that the output boundary is called with the created user
 */

class CreateAccountInteractorTest {
    private MockPresenter mockPresenter;
    private UserSession session;
    private MockLoginManager loginManager;
    private CreateAccountInteractor interactor;

    @BeforeEach
    void setUp() {
        // Set up a mock presenter to check output
        mockPresenter = new MockPresenter();
        // New session for each test
        session = new UserSession();
        // Override tryLogin because it's abstract
        loginManager = new MockLoginManager() {
            @Override
            public boolean tryLogin(String name, String password) {
                return false;
            }
        };
        // Instantiate interactor with test elements
        interactor = new CreateAccountInteractor(mockPresenter, session, loginManager);
    }

    // Test for creating a new user who is not yet in the login system
    @Test
    void testCreateAccount_NewUser() {
        String username = "test_spotify_user";

        interactor.create(username);

        // Check user was registered in login manager
        assertTrue(loginManager.wasRegisterCalled);
        assertEquals(username, loginManager.registeredUsername);

        // Check user session has the new user
        User createdUser = session.getUser();
        assertNotNull(createdUser);
        assertEquals(username, createdUser.getName());

        // Check presenter was notified
        assertTrue(mockPresenter.wasCalled);
        assertEquals(username, mockPresenter.user.getName());
    }
}

// Mock implementation of the presenter
class MockPresenter implements CreateAccountOutputBoundary {
    boolean wasCalled = false; // was presenter triggered
    User user = null; // stores the user passed to the presenter

    @Override
    public void prepareSuccessView(User user) {
        this.wasCalled = true;
        this.user = user;
    }
}

// Abstract mock of LoginManager
abstract class MockLoginManager implements LoginManager {
    boolean wasRegisterCalled = false;
    String registeredUsername = null;

    @Override
    public boolean hasLogin(String username) {
        return false; // Treat as a new user for this test
    }

    @Override
    public void registerLogin(String username, String password) {
        wasRegisterCalled = true;
        registeredUsername = username;
    }
}
