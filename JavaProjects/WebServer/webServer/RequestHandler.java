package webServer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Request handler handles an Http request for a server. Implements runnable as
 * to be run in its own thread.
 * 
 * @author Jonathan Keyuk
 *
 */
public class RequestHandler implements Runnable {

	private final File WORKING_DIRECTORY;
	private final Socket CLIENT;

	/**
	 * Constructs request handler with the servers working directory and socket
	 * connecting to the client.
	 * 
	 * @param WORKING_DIRECTORY
	 *            - working directory of resources from server.
	 * @param CLIENT_SOCKET
	 *            - Connection to the client.
	 */
	public RequestHandler(File WORKING_DIRECTORY, Socket CLIENT_SOCKET) {
		this.WORKING_DIRECTORY = WORKING_DIRECTORY;
		this.CLIENT = CLIENT_SOCKET;
	}

	/**
	 * Handles the Http request from this classes client socket field.
	 */
	@Override
	public void run() {
		ArrayList<String> requestData = getRequestHead(CLIENT);
		if (requestData.isEmpty()) return;
		String[] reqLine = parseRequestLine(requestData.get(0));
		if (reqLine == null) return;
		String reqMethod = reqLine[0], reqResource = reqLine[1];
		if (!reqMethod.equalsIgnoreCase("GET")) {
			String wrongMethod = "HTTP/1.1 405 Method Not Allowed\r\n\r\n<h1>Method Not Allowed</h1>";
			sendStringOverSocket(wrongMethod, CLIENT);
		} else {
			if (reqResource.equals("/")) reqResource = "/index.html";
			File file = new File(WORKING_DIRECTORY + File.separator + reqResource);
			if (file.isFile()) {
				doGet(file, CLIENT);
			} else {
				sendStringOverSocket("HTTP/1.1 404 Not Found\r\n\r\n<h1>File Not Found</h1>",
						CLIENT);
			}
		}
		try {
			CLIENT.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Returns lines from the head of the HTTP request read from the given
	 * socket.
	 * 
	 * @param sock
	 *            - socket to read request from
	 * @return - Lines of the HTTP request head.
	 */
	public static ArrayList<String> getRequestHead(Socket sock) {
		ArrayList<String> requestData = new ArrayList<>();
		if (sock == null) return requestData;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String input;
			while ((input = in.readLine()) != null) {
				if (input.equals("")) break;
				requestData.add(input);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return requestData;
	}

	/**
	 * Returns an array containing the request method, request URI, and HTTP
	 * version from the given request line. Or null if the request line is
	 * malformed.
	 * 
	 * @param requestLine
	 *            - Http request line to parse
	 * @return - array with request method, request URI, and HTTP verision.
	 */
	public static String[] parseRequestLine(String requestLine) {
		if (requestLine == null || requestLine.trim().isEmpty()) return null;
		String[] req = requestLine.trim().split("\\s+");
		if (req.length != 3) return null;
		return req;
	}

	/**
	 * Sends an Http response for a given file over a given socket.
	 * 
	 * @param file
	 *            - file to send.
	 * @param sock
	 *            - socket to send over.
	 */
	public static void doGet(File file, Socket sock) {
		if (file == null || !file.exists() || sock == null) return;
		String statusLineAndHeaders = "HTTP/1.1 200 OK\r\n" + "Content-Type: " + getMimeType(file)
				+ ";\r\n" + "Content-Length: " + file.length() + ";\r\n\r\n";
		sendStringOverSocket(statusLineAndHeaders, sock);
		sendFileOverSocket(file, sock);
	}

	/**
	 * Sends a given string over a given socket.
	 * 
	 * @param string
	 *            - string to send
	 * @param sock
	 *            - socket to send over
	 */
	public static void sendStringOverSocket(String string, Socket sock) {
		if (string == null || sock == null) return;
		try {
			OutputStream out = new DataOutputStream(sock.getOutputStream());
			out.write(string.getBytes(Charset.forName("UTF-8")));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a given file over a given socket.
	 * 
	 * @param file
	 *            - file to send
	 * @param sock
	 *            - socket to send file over.
	 */
	public static void sendFileOverSocket(File file, Socket sock) {
		if (file == null || !file.exists() || sock == null) return;
		try {
			OutputStream out = new DataOutputStream(sock.getOutputStream());
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
			int read;
			byte[] buff = new byte[8192];
			while ((read = fis.read(buff)) != -1) {
				out.write(buff, 0, read);
			}
			fis.close();
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Returns the mime type of the given file. Or null if the type cannot be
	 * resolved.
	 *
	 * @param file
	 *            - file to check
	 * @return - mime type of the file or null if the file cannot be resolved..
	 */
	public static String getMimeType(File file) {
		String mimeType = null;
		if (file == null || !file.exists()) return mimeType;
		try {
			mimeType = Files.probeContentType(file.toPath());
			if (mimeType == null) {
				BufferedInputStream in = new BufferedInputStream(
						Files.newInputStream(file.toPath()));
				mimeType = URLConnection.guessContentTypeFromStream(in);
				in.close();
			}
			if (mimeType == null) {
				mimeType = URLConnection.guessContentTypeFromName(file.getName());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return mimeType;
	}
}
