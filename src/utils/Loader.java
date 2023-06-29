package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Loader {
    public static BufferedImage loadBufferedImage(String path) {
        BufferedImage image = null;
        InputStream is = Loader.class.getResourceAsStream(path);
        try {
            image = ImageIO.read(is);
            is.close();
        } catch (IOException|NullPointerException e) {
            e.printStackTrace();
        }
        return image;
    }
}
