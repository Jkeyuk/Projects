package FileTransferProgram.Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import FileTransferProgram.Connection;
import FileTransferProgram.FTServer;

public class FTServerTests {

	private static FTServer server;
	private static Connection testConnection;

	public static void main(String[] args) throws Throwable {
		/*
		 * Setup
		 */
		server = new FTServer(".//src//FileTransferProgram//UnitTests//TestData");
		server.startServer(8080);
		testConnection = new Connection(new Socket("127.0.0.1", 8080));
		/*
		 * tests
		 */
		if (showFilesCommand() && deleteCommand() && downloadCommand()
				&& uploadCommand()) {
			System.out.println("All tests passed.");
		} else {
			System.out.println("Fail");
		}
		System.exit(0);
	}

	private static boolean showFilesCommand() throws Throwable {
		ArrayList<String> expected1 = new ArrayList<>();
		ArrayList<String> results1 = new ArrayList<>();
		expected1.add("OK");
		expected1.add("----------------------------------------");
		expected1.add("Files:");
		expected1.add("file1.txt");
		expected1.add("file2.gif");
		testConnection.sendMessage("!showFiles");
		testConnection.receiveMessages(x -> results1.add(x));
		if (!expected1.equals(results1)) {
			return false;
		} else {
			return true;
		}
	}

	private static boolean deleteCommand() throws Throwable {
		File test = new File(
				".//src//FileTransferProgram//UnitTests//TestData//testDelete.txt");
		test.createNewFile();
		Thread.sleep(1000);
		if (test.exists()) {
			testConnection.sendMessage("!delete testDelete.txt");
			Thread.sleep(1000);
			if (test.exists()) {
				return false;
			} else {
				return true;
			}
		} else {
			System.out.println("delete test did not create file to test");
			return false;
		}
	}

	private static boolean downloadCommand() throws Throwable {
		File test = new File(
				".//src//FileTransferProgram//UnitTests//TestData//downloadTest.gif");
		File expected = new File(
				".//src//FileTransferProgram//UnitTests//TestData//file2.gif");
		testConnection.sendMessage("!download file2.gif");
		if (!testConnection.getMessage().equals("OK")) {
			return false;
		}
		testConnection.recieveFile(test);
		if (!filesEqual(test, expected)) {
			return false;
		}
		test.delete();
		return true;
	}

	private static boolean uploadCommand() throws Throwable {
		File test = new File(
				".//src//FileTransferProgram//UnitTests//TestData//uploadTest.gif");
		File expected = new File(
				".//src//FileTransferProgram//UnitTests//TestData//file2.gif");
		testConnection.sendMessage("!upload " + test.getName());
		testConnection.sendFile(expected);
		Thread.sleep(2000);
		if (!filesEqual(test, expected)) {
			return false;
		}
		test.delete();
		return true;
	}

	/**
	 * Helper method that returns true if the content of two files are equal.
	 * 
	 * @throws IOException
	 */
	private static boolean filesEqual(File file1, File file2) throws IOException {
		FileInputStream fis1 = new FileInputStream(file1);
		FileInputStream fis2 = new FileInputStream(file2);
		byte[] buff1 = new byte[1024];
		byte[] buff2 = new byte[1024];
		while (fis1.read(buff1) > 0) {
			fis2.read(buff2);
			if (!Arrays.equals(buff1, buff2)) {
				fis1.close();
				fis2.close();
				return false;
			}
		}
		fis1.close();
		fis2.close();
		return true;
	}
}
