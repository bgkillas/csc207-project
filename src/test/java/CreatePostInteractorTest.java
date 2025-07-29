import data_access.InMemoryPostDataAccessObject;
import entities.Post;
import entities.User;
import org.junit.Test;
import app.individual_story.CreatePostInteractor;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePostInteractorTest {

    @Test
    public void testCreatePostWithoutImage() {
        InMemoryPostDataAccessObject dao = new InMemoryPostDataAccessObject();
        CreatePostInteractor interactor = new CreatePostInteractor(dao);
        User testUser = new User("Jess Jane", 19, "Female", "Toronto", "Bio", List.of(), List.of(), List.of());

        interactor.createPost("Hello", "This is my first post!", null, testUser);

        List<Post> posts = dao.getPostsByUser(testUser);
        assertEquals(1, posts.size());

        Post post = posts.get(0);
        assertEquals("Hello", post.getTitle());
        assertEquals("This is my first post!", post.getText());
        assertEquals(testUser, post.getAuthor());
        assertNull(post.getImage());
    }

    @Test
    public void testCreatePostWithInvalidImage() {
        InMemoryPostDataAccessObject dao = new InMemoryPostDataAccessObject();
        CreatePostInteractor interactor = new CreatePostInteractor(dao);
        User testUser = new User("John Smith", 19, "Male", "Africa", "Bio", List.of(), List.of(), List.of());

        File fakeImage = new File("does-not-exist.jpg");  // file should be null

        interactor.createPost("Image Post", "Trying to upload image", fakeImage, testUser);

        List<Post> posts = dao.getPostsByUser(testUser);
        assertEquals(1, posts.size());

        Post post = posts.get(0);
        assertEquals("Image Post", post.getTitle());
        assertEquals("Trying to upload image", post.getText());
        assertEquals(testUser, post.getAuthor());
        assertNull(post.getImage());  // Because file doesn't exist
    }
}