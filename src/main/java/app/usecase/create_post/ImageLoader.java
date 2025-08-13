package app.usecase.create_post;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

/** Port for loading an Image from a File. Implementations may use any image library. */
public interface ImageLoader {
    Image load(File file) throws IOException;
}
