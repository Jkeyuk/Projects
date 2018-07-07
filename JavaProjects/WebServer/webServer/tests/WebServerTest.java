package webServer.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import webServer.WebServer;

/**
 * Test case to test the WebServer Class.
 * 
 * @author jonathan keyuk
 *
 */
class WebServerTest {

	private static WebServer server;

	/**
	 * Initialize test enviroment
	 */
	@BeforeEach
	void setup() {
		server = new WebServer("TestData", 80);
	}

	/**
	 * Test start method. Tests if server starts and responds to requests.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	void testStart() throws UnknownHostException, IOException, InterruptedException {
		// Call method in test
		server.start();
		// Assert server responding to requests.
		assertTrue(getResponse().available() > 0);
		server.shutDown();
	}

	/**
	 * Test shutDown method. Tests if server shuts down and does not accept
	 * connections.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	void testShutDown() throws UnknownHostException, IOException, InterruptedException {
		// start and shutdown server
		server.start();
		Thread.sleep(500);
		server.shutDown();
		// Assert server is not responding
		assertThrows(ConnectException.class, () -> new Socket("127.0.0.1", 80));
	}

	/**
	 * Helper method to send http request and returns the input stream to the
	 * response.
	 * 
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private BufferedInputStream getResponse() throws IOException, InterruptedException {
		Socket client = new Socket("127.0.0.1", 80);
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(client.getOutputStream()));
		writer.write("GET / HTTP/1.1\r\n\r\n");
		writer.flush();
		Thread.sleep(500);// Wait for server response
		BufferedInputStream clientInput = new BufferedInputStream(client.getInputStream());
		return clientInput;
	}

}
