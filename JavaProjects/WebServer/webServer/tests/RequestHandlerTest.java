package webServer.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import webServer.RequestHandler;

/**
 * Test case to test the Request Handler class.
 * 
 * @author Jonathan Keyuk
 *
 */
class RequestHandlerTest {

	/* Fields to set up test enviroment */
	private static ServerSocket server;
	private static Socket serverToClient;
	private static Socket clientToServer;
	private static BufferedInputStream clientInputStream;

	/**
	 * Initialize test enviroment.
	 */
	@BeforeEach
	void setUp() {
		try {
			server = new ServerSocket(80);
			clientToServer = new Socket("127.0.0.1", 80);
			serverToClient = server.accept();
			clientInputStream = new BufferedInputStream(clientToServer.getInputStream());
			assertTrue(new File("TestData").exists());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tear down test enviromment.
	 */
	@AfterEach
	void tearDown() {
		try {
			clientToServer.close();
			serverToClient.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test the run method with valid Http requests to several files.
	 * 
	 * @param filePath
	 *            - Path to requested resource.
	 * @throws IOException
	 */
	@ParameterizedTest
	@ValueSource(strings = { "file1.txt", "file2.gif", "test.html", "test.css" })
	void testRun(String filePath) {
		// Generate expected result
		ByteArrayOutputStream expectedBytes = getExpectedResponse("TestData/" + filePath);
		// Send valid request to server
		ArrayList<String> request = new ArrayList<>();
		request.add("GET " + filePath + " HTTP/1.1");
		sendRequest(request, clientToServer);
		// Call method in test
		RequestHandler handler = new RequestHandler(new File("TestData"), serverToClient);
		handler.run();
		// Assert result matches expected
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertArrayEquals(expectedBytes.toByteArray(), result.toByteArray());
	}

	/**
	 * Test the run method with invalid Http methods
	 * 
	 * @param method
	 *            - invalid method
	 */
	@ParameterizedTest
	@ValueSource(strings = { "POST", "PUT", "FSDF" })
	void testRunInvalidMethods(String method) {
		// Send valid request with wrong method
		ArrayList<String> request = new ArrayList<>();
		request.add(method + " / HTTP/1.1");
		sendRequest(request, clientToServer);
		// Call method in test
		RequestHandler handler = new RequestHandler(new File("TestData"), serverToClient);
		handler.run();
		// Assert result matches expected
		String result = getBytesFromStream(clientInputStream).toString();
		String expected = "HTTP/1.1 405 Method Not Allowed\r\n\r\n<h1>Method Not Allowed</h1>";
		assertEquals(expected, result);
	}

	/**
	 * Test the run method with file which does not exist.
	 */
	@Test
	void testRunFileNotFound() {
		// Send valid request with invalid file
		ArrayList<String> request = new ArrayList<>();
		request.add("GET /FakeFile.css HTTP/1.1");
		sendRequest(request, clientToServer);
		// Call method in test
		RequestHandler handler = new RequestHandler(new File("TestData"), serverToClient);
		handler.run();
		// Assert result matches expected
		String result = getBytesFromStream(clientInputStream).toString();
		String expected = "HTTP/1.1 404 Not Found\r\n\r\n<h1>File Not Found</h1>";
		assertEquals(expected, result);
	}

	/**
	 * Test run method with malformed Http requests.
	 * 
	 * @param requestLine
	 *            - malformed Http request
	 */
	@ParameterizedTest
	@ValueSource(strings = { "", "zxczcxzxc", "''" })
	void testRunWithMalformedRequest(String requestLine) {
		// Send malformed request to server
		ArrayList<String> request = new ArrayList<>();
		request.add(requestLine);
		sendRequest(request, clientToServer);
		// Call method in test
		RequestHandler handler = new RequestHandler(new File("TestData"), serverToClient);
		handler.run();
		// Assert no bytes sent
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertEquals(result.size(), 0);
	}

	/**
	 * Test getRequestHead method with randomly generated request.
	 */
	@RepeatedTest(10)
	void testGetRequestHead() {
		// Generate and send expected request
		ArrayList<String> expected = new ArrayList<>();
		for (int j = 0; j < 10 + 1; j++) {
			expected.add(UUID.randomUUID().toString());
		}
		sendRequest(expected, clientToServer);
		// Get result and compare to expected
		ArrayList<String> result = RequestHandler.getRequestHead(serverToClient);
		assertArrayEquals(expected.toArray(), result.toArray());
	}

	/**
	 * Test getRequestHead method with null socket.
	 */
	@Test
	void testGetRequestHeadNullSocket() {
		ArrayList<String> result = RequestHandler.getRequestHead(null);
		assertEquals(0, result.size());
	}

	/**
	 * Test parseRequestLine with valid request line.
	 */
	@Test
	void testParseRequestLine() {
		String request = "GET /test.html HTTP/1.1";
		String[] result = RequestHandler.parseRequestLine(request);
		assertEquals("GET", result[0]);
		assertEquals("/test.html", result[1]);
		assertEquals("HTTP/1.1", result[2]);
	}

	/**
	 * Test parseRequestLine with malformed requests
	 */
	@ParameterizedTest
	@ValueSource(strings = { "", "zxczcxzxc", "A A" })
	void testParseRequestLineMalformedLine(String request) {
		String[] result = RequestHandler.parseRequestLine(request);
		assertNull(result);
	}

	/**
	 * Test parseRequestLine with null request
	 */
	@Test
	void testParseRequestLineNullLine() {
		String[] result = RequestHandler.parseRequestLine(null);
		assertNull(result);
	}

	/**
	 * Test doGet method with valid files and valid socket.
	 * 
	 * @param fileName
	 *            - file to send.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "TestData/file1.txt", "TestData/file2.gif", "TestData/test.html",
			"TestData/test.css" })
	void testDoGet(String fileName) {
		ByteArrayOutputStream expectedBytes = getExpectedResponse(fileName);
		RequestHandler.doGet(new File(fileName), serverToClient);
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertArrayEquals(expectedBytes.toByteArray(), result.toByteArray());
	}

	/**
	 * Test doGet method with null file
	 */
	@Test
	void testDoGetNullFile() {
		RequestHandler.doGet(null, serverToClient);
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertEquals(0, result.size());
	}

	/**
	 * Test doGet method with file that does not exist
	 */
	@Test
	void testDoGetInvalidFile() {
		RequestHandler.doGet(new File("FakeFile"), serverToClient);
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertEquals(0, result.size());
	}

	/**
	 * Test doGet method with null socket
	 */
	@Test
	void testDoGetNullSocket() {
		RequestHandler.doGet(new File("TestData/index.html"), null);
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertEquals(0, result.size());
	}

	/**
	 * Test sendStringOverSocket with random strings.
	 */
	@RepeatedTest(10)
	void testSendStringOverSocket() {
		String expected = UUID.randomUUID().toString();
		RequestHandler.sendStringOverSocket(expected, serverToClient);
		String result = getBytesFromStream(clientInputStream).toString();
		assertEquals(expected, result);
	}

	/**
	 * Test sendStringOverSocket with null string.
	 */
	@Test
	void testSendStringOverSocketNullString() {
		RequestHandler.sendStringOverSocket(null, serverToClient);
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertEquals(0, result.size());
	}

	/**
	 * Test sendStringOverSocket with null socket.
	 */
	@Test
	void testSendStringOverSocketNullSocket() {
		RequestHandler.sendStringOverSocket("dfadfa", null);
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertEquals(0, result.size());
	}

	/**
	 * Test sendFileOverSocket with valid files and one invalid file.
	 * 
	 * @param filepath
	 *            - path of files to send.
	 * @throws IOException
	 */
	@ParameterizedTest
	@ValueSource(strings = { "TestData/file1.txt", "TestData/file2.gif", "TestData/test.html",
			"TestData/test.css", "FakeFile" })
	void testSendFileOverSocket(String filepath) throws IOException {
		// Generate expected result
		File file = new File(filepath);
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		if (file.exists()) Files.copy(file.toPath(), expected);
		// Call method in test
		RequestHandler.sendFileOverSocket(file, serverToClient);
		// Assert result equals expected.
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertArrayEquals(expected.toByteArray(), result.toByteArray());
	}

	/**
	 * Test sendFileOverSocket with null socket.
	 */
	@Test
	void testSendFileOverSocketNullSocket() {
		RequestHandler.sendFileOverSocket(new File("TestData/index.html"), null);
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertEquals(0, result.size());
	}

	/**
	 * Test sendFileOverSocket with null file.
	 */
	@Test
	void testSendFileOverSocketNullFile() {
		RequestHandler.sendFileOverSocket(null, serverToClient);
		ByteArrayOutputStream result = getBytesFromStream(clientInputStream);
		assertEquals(0, result.size());
	}

	/**
	 * Test getMimeType of several files.
	 * 
	 * @param filePath
	 *            - path of files to test.
	 * @param excpected
	 *            - expected result
	 */
	@ParameterizedTest
	@CsvSource({ "TestData/file1.txt, text/plain", "TestData/file2.gif, image/gif",
			"TestData/test.html, text/html", "TestData/test.css, text/css", "FakeFile," })
	void testGetMimeType(String filePath, String excpected) {
		File file = new File(filePath);
		assertEquals(excpected, RequestHandler.getMimeType(file));
	}

	/**
	 * Helper method to send list of strings over a given socket with an ending
	 * blank line.
	 * 
	 * @param requestLines
	 *            - lines to send
	 * @param sock
	 *            - socket to send over.
	 */
	private void sendRequest(ArrayList<String> requestLines, Socket sock) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			for (String string : requestLines) {
				writer.write(string + "\r\n");
			}
			writer.write("\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method to return ByteArrayOutputStream with bytes read from a
	 * given input stream.
	 * 
	 * @param in
	 *            - stream to read from
	 * @return - bytes read from stream.
	 */
	private ByteArrayOutputStream getBytesFromStream(BufferedInputStream in) {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			while (in.available() > 0) {
				result.write(in.read());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Helper method to return the bytes from a expected server response from a
	 * given requested file.
	 * 
	 * @param fileName
	 *            - file to generate expected bytes.
	 * @return - expected bytes.
	 */
	private ByteArrayOutputStream getExpectedResponse(String fileName) {
		File testF = new File(fileName);
		ByteArrayOutputStream expectedBytes = new ByteArrayOutputStream();
		String expectedHead = "HTTP/1.1 200 OK\r\n" + "Content-Type: "
				+ RequestHandler.getMimeType(testF) + ";\r\n" + "Content-Length: " + testF.length()
				+ ";\r\n\r\n";
		try {
			expectedBytes.write(expectedHead.getBytes());
			Files.copy(testF.toPath(), expectedBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return expectedBytes;
	}
}
