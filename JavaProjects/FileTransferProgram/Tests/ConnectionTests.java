package FileTransferProgram.Tests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import FileTransferProgram.Connection;

/**
 * Unit tests for the Connection Class.
 * 
 * @author jonathan
 *
 */
public class ConnectionTests {

	private static final ExecutorService EXEC = Executors.newFixedThreadPool(100);
	private static ServerSocket server;
	private static Connection testConnection;

	public static void main(String[] args) throws Throwable {
		if (getMessageTests() && recieveMessagesTest() && sendMessageTest()
				&& recieveFileTest() && sendFileTests()) {
			System.out.println("All Tests Passed");
		} else {
			System.out.println("Fail");
		}
		EXEC.shutdown();
	}

	private static boolean getMessageTests() throws Throwable {
		DataOutputStream out = new DataOutputStream(startServer().getOutputStream());
		/*
		 * Specific Tests
		 */
		String test1 = "";
		String test2 = "   ";
		String test3 = "oneWord";
		String test4 = "   spaces   between words    and after   ";
		out.writeUTF(test1);
		out.flush();
		if (!testConnection.getMessage().equals(test1)) {
			return false;
		}
		out.writeUTF(test2);
		out.flush();
		if (!testConnection.getMessage().equals(test2)) {
			return false;
		}
		out.writeUTF(test3);
		out.flush();
		if (!testConnection.getMessage().equals(test3)) {
			return false;
		}
		out.writeUTF(test4);
		out.flush();
		if (!testConnection.getMessage().equals(test4)) {
			return false;
		}
		/**
		 * 100 Random String Tests
		 */
		for (int i = 0; i < 100; i++) {
			String s = UUID.randomUUID().toString();
			out.writeUTF(s);
			out.flush();
			if (!testConnection.getMessage().equals(s)) {
				return false;
			}
		}
		server.close();
		server = null;
		return true;
	}

	private static boolean recieveMessagesTest() throws Throwable {
		DataOutputStream out = new DataOutputStream(startServer().getOutputStream());
		/*
		 * Specific Test
		 */
		ArrayList<String> test1 = new ArrayList<>();
		out.writeUTF("@END@");
		out.flush();
		testConnection.receiveMessages(x -> test1.add(x));
		if (!test1.isEmpty()) {
			return false;
		}
		ArrayList<String> test2 = new ArrayList<>();
		out.writeUTF("hi");
		out.writeUTF("@END@");
		out.flush();
		testConnection.receiveMessages(x -> test2.add(x));
		if (!test2.get(0).equals("hi") && test2.size() != 1) {
			return false;
		}
		/*
		 * A block of 100 random strings test.
		 */
		ArrayList<String> test3 = new ArrayList<>();
		ArrayList<String> expected3 = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			String s = UUID.randomUUID().toString();
			expected3.add(s);
			out.writeUTF(s);
			out.flush();
		}
		out.writeUTF("@END@");
		out.flush();
		testConnection.receiveMessages(x -> test3.add(x));
		if (!test3.equals(expected3)) {
			return false;
		}
		server.close();
		server = null;
		return true;
	}

	private static boolean sendMessageTest() throws Throwable {
		DataInputStream input = new DataInputStream(startServer().getInputStream());
		/*
		 * Specific Tests
		 */
		String test1 = "";
		String test2 = "   ";
		String test3 = "oneWord";
		String test4 = "   spaces   between words    and after   ";
		testConnection.sendMessage(test1);
		if (!input.readUTF().equals(test1)) {
			return false;
		}
		testConnection.sendMessage(test2);
		if (!input.readUTF().equals(test2)) {
			return false;
		}
		testConnection.sendMessage(test3);
		if (!input.readUTF().equals(test3)) {
			return false;
		}
		testConnection.sendMessage(test4);
		if (!input.readUTF().equals(test4)) {
			return false;
		}
		/**
		 * 100 Random String Tests
		 */
		for (int i = 0; i < 100; i++) {
			String s = UUID.randomUUID().toString();
			testConnection.sendMessage(s);
			if (!input.readUTF().equals(s)) {
				return false;
			}
		}
		server.close();
		server = null;
		return true;
	}

	private static boolean recieveFileTest() throws Throwable {
		// Setup
		DataOutputStream out = new DataOutputStream(startServer().getOutputStream());
		File file1 = new File(
				".//src//FileTransferProgram//UnitTests//TestData//file1.txt");
		File outputTest1 = new File(
				".//src//FileTransferProgram//UnitTests//TestData//outputTest1.txt");
		File file2 = new File(
				".//src//FileTransferProgram//UnitTests//TestData//file2.gif");
		File outputTest2 = new File(
				".//src//FileTransferProgram//UnitTests//TestData//outputTest2.gif");
		/*
		 * Test 1 - single file
		 */
		// Send bytes from file to output stream
		FileInputStream fis1 = new FileInputStream(file1);
		int read;
		byte[] buff = new byte[1024];
		out.writeLong(file1.length());
		while ((read = fis1.read(buff)) > 0) {
			out.write(buff, 0, read);
		}
		out.flush();
		fis1.close();
		// call method to test
		testConnection.recieveFile(outputTest1);
		// Check if file1 and output test1 are the same
		if (!filesEqual(file1, outputTest1)) {
			return false;
		}
		/*
		 * Test 2 - single file
		 */
		// Send bytes from file to output stream
		FileInputStream fis2 = new FileInputStream(file2);
		int read2;
		byte[] buff2 = new byte[1024];
		out.writeLong(file2.length());
		while ((read2 = fis2.read(buff2)) > 0) {
			out.write(buff2, 0, read2);
		}
		out.flush();
		fis2.close();
		// call method to test
		testConnection.recieveFile(outputTest2);
		// check file2 and output test2 are the same
		if (!filesEqual(file2, outputTest2)) {
			return false;
		}
		outputTest1.delete();
		outputTest2.delete();
		server.close();
		server = null;
		return true;
	}

	private static boolean sendFileTests() throws Throwable, Throwable {
		// Setup
		DataInputStream input = new DataInputStream(startServer().getInputStream());
		File file1 = new File(
				".//src//FileTransferProgram//UnitTests//TestData//file1.txt");
		File sentFileTest1 = new File(
				".//src//FileTransferProgram//UnitTests//TestData//sentFileTest1.txt");
		/*
		 * Test 1 - single file
		 */
		// Call method to test
		testConnection.sendFile(file1);
		// Write bytes from input stream to sentFileTest1
		FileOutputStream fos1 = new FileOutputStream(sentFileTest1);
		Thread.sleep(1200);
		int read1;
		byte[] buff1 = new byte[1024];
		long numOfBytes1 = input.readLong();
		while (numOfBytes1 > 0) {
			read1 = input.read(buff1);
			numOfBytes1 -= read1;
			fos1.write(buff1, 0, read1);
		}
		fos1.close();
		// check if file1 has the same contents as sentFile1
		if (!filesEqual(file1, sentFileTest1)) {
			return false;
		}
		sentFileTest1.delete();
		server.close();
		server = null;
		return true;
	}

	/**
	 * helper method to connect the Connection object to a server and returns
	 * the socket connected to the Connection object.
	 **/
	private static Socket startServer() throws Throwable {
		testConnection = null;
		server = null;
		Callable<Socket> serverStart = () -> {
			try {
				server = new ServerSocket(8080);
				Socket s = server.accept();
				return s;
			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
		};
		Future<Socket> future = EXEC.submit(serverStart);
		testConnection = new Connection(new Socket("127.0.0.1", 8080));
		Socket serverToUser = future.get();
		return serverToUser;
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
