package SandBox;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageGreyScaler {

    public static void main(String[] args) throws IOException {
        BufferedImage img = ImageIO.read(new File(
                "C:\\Users\\Public\\JavascriptApps\\Temp\\Netbeans\\test.jpg"));

        int width = img.getWidth();
        int height = img.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color col = new Color(img.getRGB(j, i));
                int red = (int) (col.getRed() * 0.299);
                int green = (int) (col.getGreen() * 0.587);
                int blue = (int) (col.getBlue() * 0.114);
                Color newC = new Color(
                        red + green + blue, red + green + blue, red + green + blue);
                img.setRGB(j, i, newC.getRGB());
            }
        }

        File outFile = new File("C:\\Users\\Public\\JavascriptApps\\Temp\\Netbeans\\test1.jpg");

        ImageIO.write(img, "jpg", outFile);
    }
}
