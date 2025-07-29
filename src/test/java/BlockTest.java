import static org.junit.Assert.assertEquals;

import entities.User;
import org.junit.Test;
import java.util.Collections;

public class BlockTest {
    @Test
    public void testBlock() {
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
        assertEquals(dummyUser.getBlockList().size(), 0);
        dummyUser.addBlock(user1);
        assertEquals(dummyUser.getBlockList().size(), 1);
        dummyUser.addBlock(user1);
        assertEquals(dummyUser.getBlockList().size(), 1);
        assertEquals(dummyUser.getBlockList().get(0), user1);
        dummyUser.removeBlock(user1);
        assertEquals(dummyUser.getBlockList().size(), 0);
        dummyUser.removeBlock(user1);
        assertEquals(dummyUser.getBlockList().size(), 0);
    }
}
