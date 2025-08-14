package app.usecase_tests;

import app.frameworks_and_drivers.data_access.InMemoryPostDataAccessObject;
import app.entities.Post;
import app.entities.User;
import app.usecase.create_post.CreatePostOutputBoundary;
import app.usecase.create_post.CreatePostOutputData;
import app.usecase.create_post.ImageLoader;
import org.junit.Test;
import app.usecase.create_post.CreatePostInteractor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePostInteractorTest {

    @Test
    public void testCreatePostWithoutImage() {
        InMemoryPostDataAccessObject DataAccessObject = new InMemoryPostDataAccessObject();
        CreatePostInteractor interactor = new CreatePostInteractor(DataAccessObject);
        User testUser =
                new User(
                        "Jess Jane",
                        19,
                        "Female",
                        "Toronto",
                        "Bio",
                        List.of(),
                        List.of(),
                        List.of());

        interactor.createPost("Hello", "This is my first post!", null, testUser);

        List<Post> posts = DataAccessObject.getPostsByUser(testUser);
        assertEquals(1, posts.size());

        Post post = posts.get(0);
        assertEquals("Hello", post.getTitle());
        assertEquals("This is my first post!", post.getText());
        assertEquals(testUser, post.getAuthor());
        assertNull(post.getImage());
    }

    @Test
    public void testCreatePostWithInvalidImage() {
        InMemoryPostDataAccessObject DataAccessObject = new InMemoryPostDataAccessObject();
        CreatePostInteractor interactor = new CreatePostInteractor(DataAccessObject);
        User testUser =
                new User(
                        "John Smith", 19, "Male", "Africa", "Bio", List.of(), List.of(), List.of());

        File fakeImage = new File("does-not-exist.jpg"); // file should be null

        interactor.createPost("Image Post", "Trying to upload image", fakeImage, testUser);

        List<Post> posts = DataAccessObject.getPostsByUser(testUser);
        assertEquals(1, posts.size());

        Post post = posts.get(0);
        assertEquals("Image Post", post.getTitle());
        assertEquals("Trying to upload image", post.getText());
        assertEquals(testUser, post.getAuthor());
        assertNull(post.getImage()); // Because file doesn't exist
    }

    @Test
    public void testCreatePostWithEmptyTitleAndContent() {
        InMemoryPostDataAccessObject dataAccessObject = new InMemoryPostDataAccessObject();
        CreatePostInteractor interactor = new CreatePostInteractor(dataAccessObject);
        User testUser = new User("Sam", 22, "Other", "NYC", "Bio", List.of(), List.of(), List.of());

        interactor.createPost("", "", null, testUser);

        List<Post> posts = dataAccessObject.getPostsByUser(testUser);
        assertEquals(1, posts.size());

        Post post = posts.get(0);
        assertEquals("", post.getTitle());
        assertEquals("", post.getText());
    }

    @Test
    public void testCreatePostWithValidImage_UsesImageIO() throws Exception {
        InMemoryPostDataAccessObject dao = new InMemoryPostDataAccessObject();
        CreatePostInteractor interactor = new CreatePostInteractor(dao);
        User user = new User("Alice", 20, "Female", "NYC", "Bio",
                List.of(), List.of(), List.of());

        File imgFile = File.createTempFile("post-img-io", ".png");
        imgFile.deleteOnExit();
        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(bi, "png", imgFile);

        interactor.createPost("With Image", "Body", imgFile, user);

        List<Post> posts = dao.getPostsByUser(user);
        assertEquals(1, posts.size());
        assertNotNull(posts.get(0).getImage()); // should be set by ImageIO.read(...)
    }

    @Test
    public void testCreatePostWithCustomImageLoaderAndPresenter() throws Exception {
        InMemoryPostDataAccessObject dao = new InMemoryPostDataAccessObject();

        // Stub presenter to verify it was called
        abstract class TestPresenter implements CreatePostOutputBoundary {
            boolean called = false;
            CreatePostOutputData data;
            @Override
            public void present(CreatePostOutputData outputData) {
                called = true;
                data = outputData;
            }
        }
        TestPresenter presenter = new TestPresenter() {
            @Override
            public void presentFailure(String errorMessage) {

            }
        };

        ImageLoader loader = new ImageLoader() {
            @Override
            public Image load(File f) {
                return new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
            }
        };

        CreatePostInteractor interactor = new CreatePostInteractor(dao, presenter, loader);
        User user = new User("Bob", 30, "Male", "Toronto", "Bio",
                List.of(), List.of(), List.of());

        File imgFile = File.createTempFile("post-img-loader", ".png");
        imgFile.deleteOnExit();
        BufferedImage bi = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(bi, "png", imgFile);

        interactor.createPost("Loader Image", "Text", imgFile, user);

        List<Post> posts = dao.getPostsByUser(user);
        assertEquals(1, posts.size());
        assertNotNull(posts.get(0).getImage()); // set via ImageLoader
        assertTrue(presenter.called);
        assertNotNull(presenter.data);
        assertNotNull(presenter.data.getPostId());
        assertNotNull(presenter.data.getTimestamp());
    }

    @Test
    public void testDefaultConstructorSmoke() {
        CreatePostInteractor interactor = new CreatePostInteractor(); // uses in-memory DAO internally
        User user = new User("Dana", 40, "Other", "MTL", "Bio",
                List.of(), List.of(), List.of());
        // We can't inspect internal DAO here, but invoking the method covers constructor & method lines.
        interactor.createPost("T", "C", null, user);
    }

    @Test
    public void testCreatePost_ImageLoaderThrows_TriggersCatchAndNoImage() throws Exception {
        InMemoryPostDataAccessObject dao = new InMemoryPostDataAccessObject();

        // Presenter optional, but include it to also exercise the presenter path again
        CreatePostOutputBoundary presenter = new CreatePostOutputBoundary() {
            @Override public void present(CreatePostOutputData outputData) {
                assertNotNull(outputData.getPostId());
                assertNotNull(outputData.getTimestamp());
            }
            @Override public void presentFailure(String errorMessage) { /* not used */ }
        };

        // Force the try{} to throw
        ImageLoader throwingLoader = f -> { throw new RuntimeException("boom"); };

        CreatePostInteractor interactor = new CreatePostInteractor(dao, presenter, throwingLoader);

        // Make sure image != null and image.exists() == true
        File imgFile = File.createTempFile("post-img-throw", ".png");
        imgFile.deleteOnExit();
        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(bi, "png", imgFile);

        // Should swallow the exception and still save the post with a null image
        User u = new User("Catch", 23, "Other", "City", "Bio", List.of(), List.of(), List.of());
        interactor.createPost("t", "c", imgFile, u);

        List<Post> posts = dao.getPostsByUser(u);
        assertEquals(1, posts.size());
        assertNull(posts.get(0).getImage()); // because loader failed in catch
    }

}
