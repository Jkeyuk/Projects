package ZipManager;

import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("Please choose from the following options...");
			System.out.println("1. Zip a file or folder.");
			System.out.println("2. Un zip a zip file");
			System.out.println("3. Exit program");
			int choice = ZipManager.In.getInt("Enter Your Choice:", scan);
			if (choice == 1) {
				ZipMaker zipper = new ZipMaker(getDirectory(scan));
				String message = "Enter path of file to zip";
				zipper.startZipping(getFileOrFolder(message, scan), "");
				zipper.closeStream();
				System.out.println("zipping complete");
			} else if (choice == 2) {
				UnZipper un = new UnZipper(getDirectory(scan));
				String message = "Enter path of file to unzip";
				un.unzip(getFileOrFolder(message, scan).getAbsolutePath());
				System.out.println("Unzipping complete");
			} else {
				break;
			}
		}
		scan.close();
		System.exit(0);
	}

	private static String getDirectory(Scanner scan) {
		return ZipManager.In
				.getDirectory("where would you like to save your file to," + 
						      " please enter full pathname.", scan)
				.getAbsolutePath();
	}

	private static File getFileOrFolder(String message, Scanner scan) {
		return new File(
				ZipManager.In.getStringIf(
						message, scan, 
						x -> new File(x).isFile() || new File(x).isDirectory()));
	}
}
