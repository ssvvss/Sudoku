
public class ImageTest {

    public static void main(String[] args) {
        Image img = new Image("sudoku.jpg");
//        img.localThresholding();
//        img.save("out", "bmp");
        img.houghTransform().save("out", "png");
    }
}
