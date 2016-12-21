package ZipManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipMakerTests {

	public static void main(String[] args) throws IOException {
		/* ----------------uncomment lines to test units----------------- */
		// writeFileToZipStreamTest("C:\\Users\\jonke_000\\Documents\\test\\test.zip",
		// "greedy.c",
		// "C:\\Users\\jonke_000\\Documents\\test\\greedy.c");

		// zipTests("C:\\Users\\jonke_000\\Documents\\test\\test2.zip",
		// "C:\\Users\\jonke_000\\Documents\\test\\greedy.c");

		// -------------- full program testing--------------
		// ZipMaker zipper = new ZipMaker("C:\\Users\\Public\\JavascriptApps");

		// --------------single file test------------------
		// zipper.startZipping(new File(
		// "C:\\Users\\jonke_000\\Documents\\test\\greedy.c"), "");
		// zipper.closeStream();

		// -------------------directory test-----------------
		// zipper.startZipping(new
		// File("C:\\Users\\Public\\JavascriptApps\\test"), "");
		// zipper.closeStream();
	}

	public static void writeFileToZipStreamTest(String dest, String entry, String filePath) throws IOException {
		ZipMaker zipper = new ZipMaker("");
		// file to read
		File file = new File(filePath);
		// output destination
		ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(new File(dest)));
		// entry to add to zip file
		ZipEntry zipEntry = new ZipEntry(entry);
		// put entry in stream
		zipStream.putNextEntry(zipEntry);
		// method to test
		zipper.writeFileToZipStream(file, zipStream);
		// close streams
		zipStream.closeEntry();
		zipStream.close();
	}

	public static void zipTests(String dest, String filePath) throws IOException {
		ZipMaker zipper = new ZipMaker("");
		// output destination
		ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(new File(dest)));
		// file to read
		File file = new File(filePath);
		zipper.zip(file, zipStream, "");
		zipStream.close();
	}
}
