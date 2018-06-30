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
	private String destination;
	private StringBuilder path;

	/**
	 * Constructs zip maker with path to output folder.
	 * 
	 * @param destination
	 *            - path to output folder.
	 */
	public ZipMaker(String destination) {
		this.destination = destination;
		this.path = new StringBuilder();
	}

	/**
	 * Recursively zips files and directories.
	 * 
	 * @param fileToZip
	 *            - file or directory to zip
	 * 
	 */
	public void startZipping(File fileToZip) {
		if (this.zipStream == null) {
			new File(this.destination).mkdirs();
			this.zipStream = creatZipStream(destination + File.separator + fileToZip.getName());
		}
		if (fileToZip.isDirectory()) {
			File[] fileList = fileToZip.listFiles();
			path.append(fileToZip.getName() + File.separator);
			for (File file : fileList) {
				startZipping(file);
			}
		} else {
			zip(fileToZip, this.zipStream, this.path.toString());
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
	 * write the bytes from a given file to a given zipStream.
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
