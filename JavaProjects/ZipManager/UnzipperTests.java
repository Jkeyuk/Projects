package ZipManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipperTests {

	public static void main(String[] args) {
		// -------------------unit tests-------------------------------
		// writeStreamToFileTests("C:\\Users\\Public\\JavascriptApps\\test.zip",
		// "C:\\Users\\Public\\JavascriptApps");
		// -----------------full class test -------------------------------
		// UnZipper un = new UnZipper("C:\\Users\\Public\\JavascriptApps");
		// un.unzip("C:\\Users\\Public\\JavascriptApps\\test.zip");
	}

	public static void writeStreamToFileTests(String zipFilePath, String destination) {
		try {
			File zipFile = new File(zipFilePath);
			UnZipper un = new UnZipper("");
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
			ZipEntry zEntery;
			while ((zEntery = zipInputStream.getNextEntry()) != null) {
				File file = new File(destination + File.separator + zipFile.getName().split("\\.")[0] + File.separator
						+ zEntery.getName());
				File newFolder = new File(file.getParent());
				newFolder.mkdirs();
				un.writeStreamToFile(file, zipInputStream);
				zipInputStream.closeEntry();
			}
			zipInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
