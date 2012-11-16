import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private BufferedImage img;

    public Image(int width, int height)
    {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public Image(String name)
    {
        try {
            img = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
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
    
    private int RGB(int r, int g, int b)
    {
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }
    
    private int intensity(int rgb)
    {
        int b = rgb & 255;
        int g = (rgb >> 8) & 255;
        int r = (rgb >> 16) & 255;
        return (77 * r + 150 * g + 28 * b) / 255;
    }

    public void grayscale()
    {
        for (int i = 0; i < img.getWidth(); i++)
            for (int j = 0; j < img.getHeight(); j++) {
                int g = intensity(img.getRGB(i, j));
                img.setRGB(i, j, RGB(g, g, g));
            }
    }

    public void globalThresholding()
    {
        for (int i = 0; i < img.getWidth(); i++)
            for (int j = 0; j < img.getHeight(); j++) {
                if (127 <= intensity(img.getRGB(i, j)))
                    img.setRGB(i, j, RGB(255, 255, 255));
                else
                    img.setRGB(i, j, RGB(0, 0, 0));
            }
    }

    public void localThresholding()
    {
        int n = img.getWidth(), m = img.getHeight(), radius = 5;
        int[][] table = new int[n][m];
        int[][] sum = new int[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                table[i][j] = intensity(img.getRGB(i, j));

        sum[0][0] = table[0][0];
        for (int d = 1; d <= m + n - 2; d++)
            for (int i = 0; i <= d; i++)
                if (i < n && d - i < m) {
                    sum[i][d - i] += table[i][d - i];
                    if (i > 0)
                        sum[i][d - i] += sum[i - 1][d - i];
                    if (d - i > 0)
                        sum[i][d - i] += sum[i][d - i - 1];
                    if (i > 0 && d - i > 0)
                        sum[i][d - i] -= sum[i - 1][d - i - 1];
                }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int x1 = i - radius, y1 = j - radius;
                int xn = i + radius, yn = j + radius;

                if (x1 < 0) x1 = 0;
                if (y1 < 0) y1 = 0;
                if (xn >= n) xn = n - 1;
                if (yn >= m) yn = m - 1;

                int A, B, C, D;

                A = sum[xn][yn];

                if (x1 == 0) B = 0;
                else B = sum[x1 - 1][yn];

                if (y1 == 0) C = 0;
                else C = sum[xn][y1 - 1];

                if (x1 == 0 || y1 == 0) D = 0;
                else D = sum[x1 - 1][y1 - 1];

                if (A + D - B - C <= table[i][j] * (xn - x1 + 1) * (yn - y1 + 1))
                    img.setRGB(i, j, RGB(0, 0, 0));
                else
                    img.setRGB(i, j, RGB(255, 255, 255));
            }
        }
    }

    public Image houghTransform()
    {
        int n = img.getHeight(), m = img.getWidth();
        Image result = new Image(180, (int)Math.sqrt(n * n + m * m));

        return result;
    }
}
