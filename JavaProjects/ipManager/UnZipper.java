package zipManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This class allows users to un-zip up files and folders.
 * 
 * @author Jonathan
 *
 */
public class UnZipper {

	private final String destination;

	public UnZipper(String destination) {
		this.destination = destination;
	}

	/**
	 * unzips a zip file at a given path to the destinatiion folder.
	 * 
	 * @param zipFilePath
	 *            - path to file to unzip.
	 */
	public void unzip(String zipFilePath) {
		makeOutputFolder();
		try {
			File zipFile = new File(zipFilePath);
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry zEntery;
			while ((zEntery = zipInputStream.getNextEntry()) != null) {
				File file = new File(destination + File.separator
						+ zipFile.getName().split("\\.")[0] + File.separator + zEntery.getName());
				file.getParentFile().mkdirs();
				writeStreamToFile(file, zipInputStream);
				zipInputStream.closeEntry();
			}
			zipInputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * makes destination folder if it does not exist.
	 */
	private void makeOutputFolder() {
		File file = new File(destination);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * writes bytes from a given ZipInputStream to a given file.
	 * 
	 * @param file
	 *            - file to write bytes to
	 * @param zipInputStream
	 *            - stream to read bytes from.
	 */
	void writeStreamToFile(File file, ZipInputStream zipInputStream) {
		try {
			FileOutputStream fileOutput = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int x;
			while ((x = zipInputStream.read(buffer)) > 0) {
				fileOutput.write(buffer, 0, x);
			}
			fileOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
