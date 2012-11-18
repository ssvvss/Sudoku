import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

    public void houghTransform()
    {
        int n = img.getHeight(), m = img.getWidth();
        int diagonal = (int)(Math.sqrt(n * n + m * m) / 2) + 1;
        int[][] dp = new int[180][2 * diagonal + 1];
        double[] sinTable, cosTable;
        int xc = m / 2, yc = n / 2, x, y;
      
        sinTable = new double[180];
        for (int i = 0; i < 180; i++)
            sinTable[i] = Math.sin(i * Math.PI / 180);

        cosTable = new double[180];
        for (int i = 0; i < 180; i++)
            cosTable[i] = Math.cos(i * Math.PI / 180);
        
        for (int u = 0; u < m; u++)
            for (int v = 0; v < n; v++)
                if (img.getRGB(u, v) == RGB(255, 255, 255)) {
                    x = u - xc; y = v - yc;

                    for (int theta = 0; theta < 180; theta++) {
                        int r = (int)(x * cosTable[theta] + y * sinTable[theta]);
                        dp[theta][r + diagonal]++;
                    }
                }

        ArrayList<Triple> arr = new ArrayList<Triple>();
        int ansTheta = 0, ansR = 0, maxP = 0;
        for (int i = 0; i < 180; i++)
            for (int j = 0; j < 2 * diagonal + 1; j++) {
                arr.add(new Triple(dp[i][j], i, j));
                if (dp[i][j] > maxP) {
                    maxP = dp[i][j];
                    ansTheta = i;
                    ansR = j;
                }
            }
        ansR -= diagonal;
        
//        double a = -cosTable[ansTheta] / sinTable[ansTheta];
//        double b = ansR / sinTable[ansTheta];
//
//        for (int u = -xc; u < xc; u++) {
//            int v = (int)(a * u + b);
//
//            if (u + xc >= 0 && u + xc < m && v + yc >= 0 && v + yc < n)
//                img.setRGB(u + xc, v + yc, RGB(255, 0, 0));
//        }
        Collections.sort(arr);
        
        for (int i = 0; i < 300; i++) {
            int theta = arr.get(i).getSecond();
            int r = arr.get(i).getThird() - diagonal;
            double a = -cosTable[theta] / sinTable[theta];
            double b = r / sinTable[theta];

            if (theta < 92 && theta > 88) {
                System.out.println(arr.get(i).getFirst());
                drawLine(a, b);
            }
        }
    }

    private void drawLine(double a, double b)
    {
        int n = img.getHeight(), m = img.getWidth();
        int xc = m / 2, yc = n / 2;

        for (int u = -xc; u < xc; u++) {
            int v = (int)(a * u + b);

            if (u + xc >= 0 && u + xc < m && v + yc >= 0 && v + yc < n)
                img.setRGB(u + xc, v + yc, RGB(255, 0, 0));
        }
    }
}
