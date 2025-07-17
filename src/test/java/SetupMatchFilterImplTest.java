import app.teamStory.SetupMatchFilterImpl;

import entities.MatchFilter;
import entities.User;
import org.junit.jupiter.api.Test;
import usecase.teamStory.SetupMatchFilter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SetupMatchFilterImplTest {

    @Test
    public void testSetFilter() {
        // Arrange
        List<String> genres = new ArrayList<>();
        genres.add("Pop");

        List<String> artists = new ArrayList<>();
        artists.add("Lorde");

        List<String> songs = new ArrayList<>();
        songs.add("Solar Power");

        User user = new User("Stan", 22, "Male", "Toronto", "Hello!", genres, artists, songs);

        SetupMatchFilter setupFilter = new SetupMatchFilterImpl();

        // Act
        setupFilter.setFilter(user, 20, 30, "Female", "Toronto");

        // Assert
        MatchFilter filter = user.getMatchFilter();
        assertTrue(
                filter.isValid(
                        new User("Emily", 25, "Female", "Toronto", "", genres, artists, songs)));
        assertFalse(
                filter.isValid(
                        new User("John", 35, "Male", "Toronto", "", genres, artists, songs)));
        assertFalse(
                filter.isValid(
                        new User("Kate", 25, "Female", "Vancouver", "", genres, artists, songs)));
    }
}
