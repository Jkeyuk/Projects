package zipManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class allows users to zip up files and folders.
 * 
 * @author Jonathan
 *
 */
public class ZipMaker {

	private ZipOutputStream zipStream;
	private StringBuilder pathInZip;

	/**
	 * Constructs zip maker.
	 */
	public ZipMaker() {
		this.pathInZip = new StringBuilder();
	}

	/**
	 * Recursively zips files and directories to the given destination.
	 * 
	 * @param fileToZip
	 *            - file to zip
	 * @param destination
	 *            - output folder
	 */
	public void startZipping(File fileToZip, String destination) {
		if (this.zipStream == null) {
			new File(destination).mkdirs();
			this.zipStream = creatZipStream(destination + File.separator + fileToZip.getName());
		}
		if (fileToZip.isDirectory()) {
			File[] fileList = fileToZip.listFiles();
			pathInZip.append(fileToZip.getName() + File.separator);
			for (File file : fileList) {
				startZipping(file, destination);
			}
		} else {
			zip(fileToZip, this.zipStream, this.pathInZip.toString());
		}
	}

	/**
	 * Returns a ZipOutputStream to the given path.
	 * 
	 * @param pathName
	 *            - path of the zip file.
	 * @return - zip output stream to given path.
	 */
	private static ZipOutputStream creatZipStream(String pathName) {
		try {
			String zipFileName;
			int extensionIndex = pathName.lastIndexOf(".");
			if (extensionIndex >= 0) {
				zipFileName = pathName.substring(0, extensionIndex) + ".zip";
			} else {
				zipFileName = pathName + ".zip";
			}
			return new ZipOutputStream(new FileOutputStream(new File(zipFileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Zips a given file to a given zipStream in the given path.
	 * 
	 * @param fileToZip
	 *            - file to zip.
	 * @param zipStream
	 *            - stream to write data to.
	 * @param dirPath
	 *            - path to file inside the zip file.
	 */
	private static void zip(File fileToZip, ZipOutputStream zipStream, String dirPath) {
		try {
			ZipEntry zipEntry = new ZipEntry(dirPath + fileToZip.getName());
			zipStream.putNextEntry(zipEntry);
			writeFileToZipStream(fileToZip, zipStream);
			zipStream.closeEntry();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Writes the bytes from a given file to a given zip input stream.
	 * 
	 * @param file
	 *            - file to write to Zip stream
	 * @param zipStream
	 *            - stream to write data to
	 */
	private static void writeFileToZipStream(File file, ZipOutputStream zipStream) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buf)) > 0) {
				zipStream.write(buf, 0, bytesRead);
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes zipStream and sets it to null.
	 */
	public void closeStream() {
		try {
			zipStream.close();
			this.zipStream = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
