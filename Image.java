import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private BufferedImage img;
    
    public Image(String name)
    {
        try {
            img = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private int RGB(int r, int g, int b)
    {
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }

    public void binarize() {
        int threshold = RGB(127, 127, 127);
        for (int i = 0; i < img.getWidth(); i++)
            for (int j = 0; j < img.getHeight(); j++) {
                if (img.getRGB(i, j) > threshold)
                    img.setRGB(i, j, RGB(255, 255, 255));
                else
                    img.setRGB(i, j, RGB(0, 0, 0));
            }
    }

    public void save(String name, String extension) {
        try {
            File outputFile = new File(name + "." + extension);
            ImageIO.write(img, extension, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
