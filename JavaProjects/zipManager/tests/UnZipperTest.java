package zipManager.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import zipManager.UnZipper;

/**
 * Test class to test the unzipping feature of the UnZipper class.
 * 
 * @author jonke
 *
 */
class UnZipperTest {

	/* Test files and expected results */
	private static File directoryZip;
	private static File expectedDirectory;
	private static File singleFileZip;
	private static File expectedSingleFile;

	/**
	 * Initialize test files and expected results.
	 */
	@BeforeAll
	static void setUp() {
		directoryZip = new File("test/resources/testDir.zip");
		assertTrue(directoryZip.exists());
		expectedDirectory = new File("test/resources/testDir");
		assertTrue(expectedDirectory.exists());
		singleFileZip = new File("test/resources/singleFile.zip");
		assertTrue(singleFileZip.exists());
		expectedSingleFile = new File("test/resources/singleFile.bmp");
		assertTrue(expectedSingleFile.exists());
	}

	/**
	 * Test unziping of a nested directory.
	 */
	@Test
	void testUnzipDirectory() {
		UnzipTest(directoryZip, expectedDirectory);
	}

	/**
	 * Test unzipping a single file
	 */
	@Test
	void testUnzipSingleFile() {
		UnzipTest(singleFileZip, expectedSingleFile);
	}

	/**
	 * Unzip test unzips a given zip file and compares it with an expected
	 * result.
	 * 
	 * @param zipFile
	 *            - zip file to unzip
	 * @param ExpectedOutput
	 *            - expected output to compare
	 */
	private static void UnzipTest(File zipFile, File ExpectedOutput) {
		// Unzip zip file
		UnZipper.unzip(zipFile, "unzipOuput/");
		// Assert output created
		File output = new File("unzipOuput/" + ExpectedOutput.getName());
		assertTrue(output.exists());
		// Assert output matches expected
		assertFileOrDirEqual(output, ExpectedOutput);
	}

	/**
	 * Recursively assert two directories or files are equal. Directories are
	 * equal if they have the same name, number of files, and each file has the
	 * same name and bytes.
	 * 
	 * @param output
	 *            - File or dir to compare.
	 * @param expected
	 *            - File or dir to compare.
	 */
	private static void assertFileOrDirEqual(File output, File expected) {
		// same name
		assertEquals(output.getName(), expected.getName());
		// same length
		assertEquals(output.length(), expected.length());
		if (output.isDirectory()) {
			assertTrue(expected.isDirectory());
			// same files
			assertArrayEquals(output.list(), expected.list());
			for (File f : output.listFiles()) {
				// expected file exists
				File expectedFile = new File(expected + File.separator + f.getName());
				assertTrue(expectedFile.exists());
				// both files equal
				assertFileOrDirEqual(f, expectedFile);
			}
		} else {
			assertTrue(expected.isFile());
			// files have same bytes
			assertFileEqual(output, expected);
		}
	}

	/**
	 * Assert two files have equal bytes.
	 * 
	 * @param output
	 *            - first file to compare
	 * @param expected
	 *            - second file to compare
	 */
	private static void assertFileEqual(File output, File expected) {
		try {
			BufferedInputStream outPutStream = new BufferedInputStream(new FileInputStream(output));
			BufferedInputStream exStream = new BufferedInputStream(new FileInputStream(expected));
			int data;
			while ((data = outPutStream.read()) != -1) {
				assertEquals(data, exStream.read());
			}
			assertEquals(-1, exStream.read());
			outPutStream.close();
			exStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Failed stream with files: " + output.getName() + " " + expected.getName());
		}
	}

	/**
	 * Delete generated files after tests
	 */
	@AfterAll
	static void tearDown() {
		deleteDir(new File("unzipOuput/"));
	}

	/**
	 * Recursivley delete directory
	 * 
	 * @param file
	 *            -dir to delete
	 */
	private static void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteDir(f);
			}
		}
		file.delete();
	}
}
