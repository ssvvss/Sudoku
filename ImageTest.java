
public class ImageTest {

    public static void main(String[] args) {
        Image img = new Image("Penguins.jpg");
        img.binarize();
        img.save("penguins", "png");
    }
}
