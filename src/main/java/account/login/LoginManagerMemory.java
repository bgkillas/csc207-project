package account.login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** an in memory implementation of LoginManager. */
public class LoginManagerMemory implements LoginManager {
    private final Map<String, byte[]> map = new HashMap<>();
    private final MessageDigest digest = MessageDigest.getInstance("SHA-256");

    /** creates a LoginManager class. */
    public LoginManagerMemory() throws NoSuchAlgorithmException {}

    @Override
    public boolean hasLogin(String name) {
        return map.containsKey(name);
    }

    @Override
    public boolean tryLogin(String name, String password) {
        return Arrays.equals(map.get(name), getHash(password));
    }

    @Override
    public void registerLogin(String name, String password) {
        map.put(name, getHash(password));
    }

    private byte[] getHash(String password) {
        return digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}
