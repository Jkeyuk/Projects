package ThumbNailMaker;

import java.io.File;
import java.util.Scanner;

/**
 * This program allows users to create 100X100 pixel thumb nails of images in a
 * given directory or file.
 * 
 * @author jonathan
 *
 */
public class Main {

	public static void main(String[] args) {
		new ThumbNailCreator().start(getSource(new Scanner(System.in)));
	}

	private static File getSource(Scanner scan) {
		String path;
		do {
			System.out.println("");
			System.out.println("enter pathname to image file or folder of images:");
			path = scan.nextLine().trim();
		} while (!checkSource(path));
		scan.close();
		return new File(path);
	}

	private static boolean checkSource(String s) {
		if (new File(s).isDirectory()) {
			return true;
		} else {
			return new File(s).isFile();
		}
	}
}
