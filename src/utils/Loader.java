package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Loader {
    public static BufferedImage loadBufferedImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return image;
    }
}
