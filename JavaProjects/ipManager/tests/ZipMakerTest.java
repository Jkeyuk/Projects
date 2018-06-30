package zipManager.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import zipManager.ZipMaker;

/**
 * This class tests the zipping function of the zip maker class.
 * 
 * @author jonathan
 *
 */
class ZipMakerTest {

	/* Test files and expected results */
	private static File testDir;
	private static File expectedDirectoryOutput;
	private static File singleTestFile;
	private static File expectedSingleFileOutput;

	/**
	 * Initialize Files to zip and expected results.
	 */
	@BeforeAll
	static void setUp() {
		testDir = new File("test/resources/testDir");
		assertTrue(testDir.exists());
		expectedDirectoryOutput = new File("test/resources/testDir.zip");
		assertTrue(expectedDirectoryOutput.exists());
		singleTestFile = new File("test/resources/singleFile.bmp");
		assertTrue(singleTestFile.exists());
		expectedSingleFileOutput = new File("test/resources/singleFile.zip");
		assertTrue(expectedSingleFileOutput.exists());
	}

	/**
	 * Test zipping a single file and comparing the output with an expected
	 * result.
	 */
	@Test
	void testZipSingleFile() {
		zipTest(expectedSingleFileOutput, singleTestFile);
	}

	/**
	 * Test zipping a set of nested directories with files. Compare resulting
	 * zip with an expected output.
	 */
	@Test
	void testZipDirectory() {
		zipTest(expectedDirectoryOutput, testDir);
	}

	/**
	 * Zips a given file and asserts the output zip is the same as a given
	 * expected result.
	 * 
	 * @param expectedZip
	 *            - Excpected zip file used for comparison.
	 * @param fileToZip
	 *            - File to zip.
	 */
	private static void zipTest(File expectedZip, File fileToZip) {
		// Zip given test file
		ZipMaker zipper = new ZipMaker("zipOutput");
		zipper.startZipping(fileToZip);
		zipper.closeStream();
		// Assert destination directory and zip file were created.
		assertTrue(new File("zipOutput/" + expectedZip.getName()).exists());
		// Compare resulting zip with the expected zip.
		try {
			ZipFile resultZip = new ZipFile("zipOutput/" + expectedZip.getName());
			ZipFile expected = new ZipFile(expectedZip);
			assertZipFilesEqual(resultZip, expected);
			resultZip.close();
			expected.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("error in zipTest");
		}
	}

	/**
	 * Asserts two zip files are equal. Two zip files are equal if they have the
	 * same numer of files, and each file has a corresponding pair with the same
	 * relative path and bytes
	 * 
	 * @param zip1
	 *            - Zip to compare.
	 * @param zip2
	 *            - Zip to compare.
	 * @throws IOException
	 */
	private static void assertZipFilesEqual(ZipFile zip1, ZipFile zip2) throws IOException {
		// map entries to their relative paths.
		HashMap<String, ZipEntry> zip1Entries = getEntries(zip1);
		HashMap<String, ZipEntry> zip2Entries = getEntries(zip2);
		// assert entries from each zip match
		assertEquals(zip2Entries.size(), zip1Entries.size());
		assertTrue(zip1Entries.keySet().containsAll(zip2Entries.keySet()));
		// compare corresponding zip entries from each zip for equality
		for (Map.Entry<String, ZipEntry> ent : zip2Entries.entrySet()) {
			InputStream zip2Stream = zip2.getInputStream(ent.getValue());
			InputStream zip1Stream = zip1.getInputStream(zip1Entries.get(ent.getKey()));
			int data;
			while ((data = zip2Stream.read()) != -1) {
				assertEquals(data, zip1Stream.read());
			}
			assertEquals(-1, zip1Stream.read());
			zip1Stream.close();
			zip2Stream.close();
		}
	}

	/**
	 * Returns a map of zip entries mapped by their relative paths inside the
	 * given zip file.
	 * 
	 * @param zFile
	 *            - zip file to open.
	 * @return - map of zip entries from a given zip file.
	 */
	private static HashMap<String, ZipEntry> getEntries(ZipFile zFile) {
		HashMap<String, ZipEntry> entries = new HashMap<>();
		Enumeration<? extends ZipEntry> zipEntries = zFile.entries();
		while (zipEntries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) zipEntries.nextElement();
			if (!zipEntry.isDirectory())
				entries.put(Paths.get(zipEntry.getName()).toString(), zipEntry);
		}
		return entries;
	}

	/**
	 * remove all files associated with the test cases.
	 */
	@AfterAll
	static void tearDown() {
		new File("zipOutput/testDir.zip").delete();
		new File("zipOutput/singleFile.zip").delete();
		new File("zipOutput").delete();
	}
}
