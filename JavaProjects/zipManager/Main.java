package zipManager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Entry Point for the Zip Manager application
 * 
 * @author jon
 *
 */
public class Main {

	/**
	 * Main method for the application
	 * 
	 * @param args
	 *            - command line args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			properUsageMessage();
		} else {
			File file = new File(args[0]);
			File ouputFolder = new File(args[1]);
			if (file.isDirectory() || file.isFile()) {
				zipOrUnZip(file, ouputFolder);
			} else {
				System.out.println("\nCannot Find File\n");
			}
		}
	}

	/**
	 * Zips a given zip file, or Unzips if given file is not a zip file, reads
	 * file signature to check if zip file, output generated in given output
	 * folder.
	 * 
	 * @param file
	 *            - file to zip or unzip
	 * @param ouputFolder
	 *            - output folder
	 */
	private static void zipOrUnZip(File file, File ouputFolder) {
		if (file.isDirectory()) {
			zip(file, ouputFolder);
		} else {
			try {
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				long n = raf.readInt();
				raf.close();
				if (n == 0x504B0304 || n == 0x504B0506 || n == 0x504B0708)
					UnZipper.unzip(file, ouputFolder.getAbsolutePath());
				else
					zip(file, ouputFolder);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("\nCould not read file");
			}
		}
	}

	/**
	 * Zips file to given output folder.
	 * 
	 * @param file
	 *            - file to zip
	 * @param ouputFolder
	 *            - output location
	 */
	private static void zip(File file, File ouputFolder) {
		ZipMaker zipper = new ZipMaker();
		zipper.startZipping(file, ouputFolder.getAbsolutePath());
		zipper.closeStream();
	}

	/**
	 * Message with instructions for proper usage.
	 */
	private static void properUsageMessage() {
		System.out.println("\nProper usage: zipManager.Main [ File Path ] [ Output Path ]");
		System.out.println("If file path leads to a zip file, the file is unzipped.");
		System.out.println("Regular files or folders are zipped");
		System.out.println("Outputs files to output path\n");
	}
}
