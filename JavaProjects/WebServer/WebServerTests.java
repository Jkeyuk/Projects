package WebServer;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServerTests {

	private static final ExecutorService EXEC = Executors.newFixedThreadPool(100);
	private static final String dir = ".//src//WebServer//TestData//";
	private static WebServer server;
	private static BufferedWriter writer;
	private static DataInputStream clientInput;
	private static Socket client;

	public static void main(String[] args) {
		setup();
		try {
			if (fileNotFoundTests() && methodNotAllowedTest()) {
				System.out.println("PASSED TESTS");
			} else {
				System.out.println("Failed Tests");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("TEST FAILED by exeption");
		}
		System.exit(0);
	}

	/**
	 * Send get requests with invalid resources make sure appropriate response
	 * is sent back.
	 * 
	 * @return true if test passes
	 */
	private static boolean fileNotFoundTests() throws Exception {
		sendHTTPRequest("GET", "/vhfghfg");
		Thread.sleep(500);
		String r = "";
		byte[] buff = new byte[1024];
		while (clientInput.available() > 0) {
			clientInput.read(buff);
			r += new String(buff, StandardCharsets.UTF_8).trim();
		}
		if (!r.equals("HTTP/1.1 404 Not Found\r\n\r\n<h1>File not found</h1>")) {
			return false;
		}
		return true;
	}

	/**
	 * Send HTTP request with bad method, make sure appropriate response is sent
	 * back.
	 * 
	 * @return true if test passes
	 * @throws Exception
	 */
	private static boolean methodNotAllowedTest() throws Exception {
		sendHTTPRequest("DFADF", "/index.html");
		Thread.sleep(500);
		String r = "";
		byte[] buff = new byte[1024];
		while (clientInput.available() > 0) {
			clientInput.read(buff);
			r += new String(buff, StandardCharsets.UTF_8).trim();
		}
		if (!r.equals("HTTP/1.1 405 Method Not Allowed\r\n\r\n"
				+ "<h1>Method Not Allowed</h1>")) {
			return false;
		}
		return true;
	}

	/**
	 * helper method to setup the server for testing.
	 */
	private static void setup() {
		Runnable start = () -> {
			server = new WebServer(dir, 80);
			server.start();
		};
		EXEC.execute(start);
	}

	/**
	 * helper method to send HTTP requests to server.
	 */
	private static void sendHTTPRequest(String method, String res) {
		try {
			client = new Socket("127.0.0.1", 80);
			clientInput = new DataInputStream(client.getInputStream());
			writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			writer.write(method + " " + res + "HTTP/1.1\r\n\r\n");
			writer.flush();
		} catch (IOException e) {
			System.out.println("request failed");
			e.printStackTrace();
		}
	}
}
