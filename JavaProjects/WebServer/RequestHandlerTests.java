package WebServer;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestHandlerTests {

	private static final ExecutorService EXEC = Executors.newFixedThreadPool(100);
	private static final Random rn = new Random();
	private static final String dir = ".//src//WebServer//TestData//";
	private static RequestHandler handler;
	private static BufferedWriter writer;
	private static DataInputStream clientInput;
	private static Socket client;
	private static Socket server;

	public static void main(String[] args) throws Throwable {
		setup();
		if (getHttpRequestTest() && parseHttpRequestTest() && getMimeTypeTest()
				&& sendHtmlTest() && sendFileTest()) {
			System.out.println("Passed");
		} else {
			System.out.println("Failed");
		}
		System.exit(0);
	}

	/**
	 * Send a group of random strings representing a HTTP request to the server
	 * 100 times, and test if getHttpRequest is receiving them.
	 * 
	 * @return True if test passes.
	 * @throws Throwable
	 */
	private static boolean getHttpRequestTest() throws Throwable {
		for (int i = 0; i < 100; i++) {
			ArrayList<String> randomTest = new ArrayList<>();
			for (int j = 0; j < rn.nextInt(10) + 1; j++) {
				randomTest.add(UUID.randomUUID().toString());
			}
			for (String string : randomTest) {
				writer.write(string + "\r\n");
			}
			writer.write("\r\n");
			writer.flush();
			// method to test
			ArrayList<String> result = handler.getHttpRequest();
			if (!result.equals(randomTest)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Give 3 different HTTP request lines to the parseHttpRequest method, and
	 * check if the lines are parsed correctly.
	 * 
	 * @return true if test passes
	 */
	private static boolean parseHttpRequestTest() {
		ArrayList<String> test1 = new ArrayList<>();
		test1.add("GET /index.html HTTP/1.1");
		ArrayList<String> test2 = new ArrayList<>();
		test2.add("GET / HTTP/1.1");
		ArrayList<String> test3 = new ArrayList<>();
		test3.add("POST /home/jon/car/cat.html HTTP/1.1");
		String[] result1 = handler.parseHttpRequest(test1);
		String[] result2 = handler.parseHttpRequest(test2);
		String[] result3 = handler.parseHttpRequest(test3);
		if (!result1[0].equals("GET")) {
			return false;
		}
		if (!result1[1].equals("/index.html")) {
			return false;
		}
		if (!result2[0].equals("GET")) {
			return false;
		}
		if (!result2[1].equals("/")) {
			return false;
		}
		if (!result3[0].equals("POST")) {
			return false;
		}
		if (!result3[1].equals("/home/jon/car/cat.html")) {
			return false;
		}
		return true;
	}

	/**
	 * call get mime type and check it is returning the correct mime type.
	 * 
	 * @return true if test passes
	 */
	private static boolean getMimeTypeTest() {
		File testFile1 = new File(dir + "file1.txt");
		File testFile2 = new File(dir + "file2.gif");
		File testFile3 = new File(dir + "test.html");
		File testFile4 = new File(dir + "test.css");
		if (!handler.getMimeType(testFile1).equals("text/plain")) {
			return false;
		}
		if (!handler.getMimeType(testFile2).equals("image/gif")) {
			return false;
		}
		if (!handler.getMimeType(testFile3).equals("text/html")) {
			return false;
		}
		if (!handler.getMimeType(testFile4).equals("text/css")) {
			return false;
		}
		return true;
	}

	/**
	 * Call the sendHTML method with random inputs 100 times and test if it is
	 * sending the appropriate response.
	 * 
	 * @return true if test passes
	 * @throws Throwable
	 */
	private static boolean sendHtmlTest() throws Throwable {
		for (int i = 0; i < 100; i++) {
			int code = rn.nextInt(500) + 1;
			String status = UUID.randomUUID().toString();
			String body = UUID.randomUUID().toString();
			String expected = "HTTP/1.1 " + code + " " + status + "\r\n\r\n" + body;
			handler.sendHTML(code, status, body);
			String result = "";
			byte[] buff = new byte[1024];
			while (clientInput.available() > 0) {
				clientInput.read(buff);
				result += new String(buff, StandardCharsets.UTF_8).trim();
			}
			if (!result.equals(expected)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Call the sendFile method with each file and check if the correct headers
	 * and file are sent to the client.
	 * 
	 * @return true if test passes
	 */
	private static boolean sendFileTest() throws Throwable {
		for (File f : new File(dir).listFiles()) {
			// create expected results
			File testF = f;
			String expectedHeaders = "HTTP/1.1 200 OK\r\n" + "Content-Type:"
					+ handler.getMimeType(testF) + ";\r\n" + "Content-Length: "
					+ testF.length() + ";\r\n\r\n";
			ByteArrayOutputStream expectedBytes = new ByteArrayOutputStream();
			expectedBytes.write(expectedHeaders.getBytes());
			Files.copy(testF.toPath(), expectedBytes);
			// Get Results
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			handler.sendFile(200, "OK", testF);
			byte[] buff = new byte[1024];
			while (clientInput.available() > 0) {
				int r = clientInput.read(buff);
				result.write(buff, 0, r);
			}
			// Test Results
			if (!Arrays.equals(expectedBytes.toByteArray(), result.toByteArray())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Helper method to start a server to test functions of Request Handler
	 * class.
	 * 
	 * @throws Throwable
	 */
	private static void setup() throws Throwable {
		Callable<Socket> serverStart = () -> {
			try {
				ServerSocket server = new ServerSocket(80);
				Socket s = server.accept();
				return s;
			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
		};
		Future<Socket> future = EXEC.submit(serverStart);
		client = new Socket("127.0.0.1", 80);
		server = future.get();
		writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		clientInput = new DataInputStream(client.getInputStream());
		handler = new RequestHandler(new File(dir), server);
	}
}
