package app.entity_tests;

import app.entities.MatchFilter;
import app.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MatchFilterTest {

    private User alice;
    private User bob;

    @BeforeEach
    void setUp() {
        alice =
                new User(
                        "Alice",
                        22,
                        "female",
                        "Toronto",
                        "alice123",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        bob =
                new User(
                        "Bob",
                        30,
                        "male",
                        "Vancouver",
                        "bob456",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
    }

    @Test
    void testGetters() {
        MatchFilter filter = new MatchFilter(20, 30, "female", "Toronto");
        assertEquals(20, filter.getMinAge());
        assertEquals(30, filter.getMaxAge());
        assertEquals("female", filter.getPreferredGender());
        assertEquals("Toronto", filter.getPreferredLocation());
    }

    @Test
    void testValidMatch_AllCriteriaMatch() {
        MatchFilter filter = new MatchFilter(20, 25, "female", "Toronto");
        assertTrue(filter.isValid(alice));
    }

    @Test
    void testInvalidMatch_AgeTooLow() {
        MatchFilter filter = new MatchFilter(23, 30, "female", "Toronto");
        assertFalse(filter.isValid(alice));
    }

    @Test
    void testInvalidMatch_GenderMismatch() {
        MatchFilter filter = new MatchFilter(20, 25, "male", "Toronto");
        assertFalse(filter.isValid(alice));
    }

    @Test
    void testInvalidMatch_LocationMismatch() {
        MatchFilter filter = new MatchFilter(20, 25, "female", "Vancouver");
        assertFalse(filter.isValid(alice));
    }

    @Test
    void testValidMatch_WithAnyGenderAndLocation() {
        MatchFilter filter = new MatchFilter(20, 25, "N/A", "N/A");
        assertTrue(filter.isValid(alice));
    }

    @Test
    void testValidMatch_OnlyGenderAny() {
        MatchFilter filter = new MatchFilter(20, 25, "N/A", "Toronto");
        assertTrue(filter.isValid(alice));
    }

    @Test
    void testValidMatch_OnlyLocationAny() {
        MatchFilter filter = new MatchFilter(20, 25, "female", "N/A");
        assertTrue(filter.isValid(alice));
    }

    @Test
    void testInvalidMatch_AllMismatch() {
        MatchFilter filter = new MatchFilter(18, 21, "male", "Vancouver");
        assertFalse(filter.isValid(alice));
    }
}
