import java.util.ArrayList;
import java.util.Collections;

public class ImageTest {

    public static void main(String[] args)
    {
        Image img = new Image("sudoku.png");
        img.houghTransform();
        img.save("out", "png");
    }
}
