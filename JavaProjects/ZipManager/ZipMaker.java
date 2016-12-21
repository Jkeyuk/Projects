package ZipManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * This class allows users to zip up files and folders.
 * @author Jonathan 
 *
 */
public class ZipMaker {

	private ZipOutputStream zipStream = null;
	private String destination;
	private File sourceFile;

	public ZipMaker(String destination) {
		this.destination = destination;
	}

	/**
	 * recursively zips files and directories.
	 * 
	 * @param fileToZip
	 *            - file or directory to zip
	 * @param dirPath
	 *            - path to place file inside the zip file.
	 */
	public void startZipping(File fileToZip, String dirPath) {
		if (this.zipStream == null) {
			this.zipStream = creatZipStream(fileToZip.getName());
			this.sourceFile = fileToZip;
		}
		if (fileToZip.isDirectory()) {
			File[] fileList = fileToZip.listFiles();
			for (File file : fileList) {
				dirPath = sourceFile.toURI().relativize(fileToZip.toURI()).getPath();
				startZipping(file, dirPath);
			}
		} else {
			zip(fileToZip, zipStream, dirPath);
		}
	}

	/**
	 * returns a ZipOutputStream which writes to a zip file with a given file
	 * name.
	 * 
	 * @param fileName
	 *            - file name of the zip file to write to.
	 * @return
	 */
	private ZipOutputStream creatZipStream(String fileName) {
		try {
			String zipFileName;
			int extensionIndex = fileName.lastIndexOf(".");
			if (extensionIndex > 0) {
				zipFileName = fileName.substring(0, extensionIndex) + ".zip";
			} else {
				zipFileName = fileName + ".zip";
			}
			return new ZipOutputStream(new FileOutputStream(new File(destination + File.separator + zipFileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Zips a given file to a given zipStream in its proper location specified
	 * by the given directory path.
	 * 
	 * @param fileToZip
	 *            - file to zip.
	 * @param zipStream
	 *            - stream to write data to.
	 * @param dirPath
	 *            - path to file inside the zip file.
	 */
	void zip(File fileToZip, ZipOutputStream zipStream, String dirPath) {
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
	 * writes the bytes from a given file to a given zipStream.
	 * 
	 * @param file
	 *            - file to write to Zip stream
	 * @param zipStream
	 *            - stream to write data to
	 */
	void writeFileToZipStream(File file, ZipOutputStream zipStream) {
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
	void closeStream() {
		try {
			zipStream.close();
			this.zipStream = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
