package ThumbNailMaker;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class creates thumb nail images of a given directory or image file.
 * 
 * @author jonathan
 *
 */
public class ThumbNailCreator {

	/**
	 * recursively walks a file object to create thumb nails of all images in
	 * the given file.
	 * 
	 * @param imageOrFolder
	 *            - file or folder to recursively walk and create thumb nails.
	 */
	public void start(File imageOrFolder) {
		if (imageOrFolder.isDirectory()) {
			File[] listOfFiles = imageOrFolder.listFiles();
			for (File file : listOfFiles) {
				start(file);
			}
		} else if (imageOrFolder.isFile()) {
			makeThumbNail(imageOrFolder);
		}
	}

	/**
	 * Makes a thumb nail image of a given image file.
	 * 
	 * @param imageFile
	 *            - image file to make thumb nail for.
	 */
	void makeThumbNail(File imageFile) {
		try {
			BufferedImage img = ImageIO.read(imageFile);
			if (img != null) {
				Image scaledImg = img.getScaledInstance(100, 100,
						Image.SCALE_SMOOTH);
				BufferedImage newImg = new BufferedImage(100, 100,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D graph = newImg.createGraphics();
				graph.drawImage(scaledImg, 0, 0, null);
				ImageIO.write(newImg, imageFile.getName().split("\\.")[1],
						new File(imageFile.getParentFile().getAbsolutePath()
								+ File.separator + "Thumb-" + imageFile.getName()));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
