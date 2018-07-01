package zipManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This class allows users to unzip zip files.
 * 
 * @author Jonathan
 *
 */
public class UnZipper {

	/**
	 * Unzips a given zip file to the given output path.
	 * 
	 * @param zipFile
	 *            - file to unzip.
	 * @param outFolder
	 *            - path to output folder.
	 */
	public static void unzip(File zipFile, String outFolder) {
		new File(outFolder).mkdirs();
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry zEntery;
			while ((zEntery = zipInputStream.getNextEntry()) != null) {
				if (!zEntery.isDirectory()) {
					File file = new File(outFolder + File.separator + zEntery.getName());
					file.getParentFile().mkdirs();
					writeStreamToFile(file, zipInputStream);
				}
				zipInputStream.closeEntry();
			}
			zipInputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Writes the bytes from a given ZipInputStream to a given file.
	 * 
	 * @param file
	 *            - file to write bytes to
	 * @param zipInputStream
	 *            - stream to read bytes from.
	 */
	private static void writeStreamToFile(File file, ZipInputStream zipInputStream) {
		try {
			FileOutputStream fileOutput = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int x;
			while ((x = zipInputStream.read(buffer)) != -1) {
				fileOutput.write(buffer, 0, x);
			}
			fileOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
