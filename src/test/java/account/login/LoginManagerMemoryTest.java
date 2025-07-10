package account.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import org.junit.Test;

/** Tests if the login manager memory portion correctly handles logins. */
public class LoginManagerMemoryTest {
    @Test
    public void testLogin() throws NoSuchAlgorithmException {
        final LoginManager loginManager = new LoginManagerMemory();
        assertFalse(loginManager.hasLogin("name"));
        loginManager.registerLogin("name", "pass");
        loginManager.registerLogin("name2", "pass2");
        assertTrue(loginManager.hasLogin("name"));
        assertFalse(loginManager.tryLogin("name", "password"));
        assertTrue(loginManager.tryLogin("name", "pass"));
    }
}
