package thumbnailcreator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ThumbNailCreator {

    //string to hold output destination
    private final String DESTINATION;

    public ThumbNailCreator(String DEST) {
        this.DESTINATION = DEST;
    }

    public void start(String filePath) {
        //file object to hold directory or image file to process
        File file = new File(filePath);
        //if directory pull out files and recursively call start method
        if (file.isDirectory()) {
            File[] listOfFiles = file.listFiles();
            for (File filee : listOfFiles) {
                start(filee.getAbsolutePath());
            }//else if file make thumb nail
        } else if (file.isFile()) {
            makeThumbNail(filePath);
        }
    }

    private void makeThumbNail(String filePath) {
        try {//create file object from file path string
            File file = new File(filePath);
            //create buffered image object with file
            BufferedImage img = ImageIO.read(file);
            if (img != null) {
                //create image object from scaled buffer image
                Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                //create new buffered image to write scaled image to
                BufferedImage newImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
                //create graphics object from new buffered image
                Graphics2D graph = newImg.createGraphics();
                //use graphic object to write to new buffered image
                graph.drawImage(scaledImg, 0, 0, null);
                //write new buffered image to file
                ImageIO.write(newImg, returnFileType(filePath), new File(createPathName(filePath)));
            }
        } catch (IOException ex) {
            Logger.getLogger(ThumbNailCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String returnFileType(String filePath) {
        //create file object
        File file = new File(filePath);
        //get file object name and file format
        String[] array = file.getName().split("\\.");
        //return file format string
        return array[1];
    }

    private String createPathName(String filePath) {
        //create file object
        File file = new File(filePath);
        //create output file object
        File output = new File(DESTINATION);
        //build string with pathname to new output file
        String returnString = output.getAbsolutePath() + File.separator + "New" + file.getName();
        //return string
        return returnString;
    }
}
