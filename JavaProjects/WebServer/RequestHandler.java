package WebServer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Request Handler object is responsible for handling the request from the
 * client, this class implements runnable and is run in its own thread.
 * 
 * @author jonathan
 *
 */
public class RequestHandler implements Runnable {

	private final File WORKING_DIRECTORY;
	private final Socket CLIENT_SOCKET;

	/**
	 * Request Handler object is given the servers working directory and the
	 * socket the client is connected from.
	 *
	 * @param WORKING_DIRECTORY
	 *            - directory to serve server files from
	 * @param CLIENT_SOCKET
	 *            - socket connecting to the client browser
	 */
	public RequestHandler(File WORKING_DIRECTORY, Socket CLIENT_SOCKET) {
		this.WORKING_DIRECTORY = WORKING_DIRECTORY;
		this.CLIENT_SOCKET = CLIENT_SOCKET;
	}

	/**
	 * Executes request handlers functions by getting the HTTP request, parsing
	 * the request then responding.
	 */
	@Override
	public void run() {
		try {
			String[] requestData = parseHttpRequest(getHttpRequest());
			String reqMethod = requestData[0];
			String reqResource = requestData[1];
			respond(reqMethod, reqResource);
			CLIENT_SOCKET.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Reads the HTTP request from the Client Socket and returns the lines as an
	 * ArrayList of Strings.
	 *
	 * @return Lines from an HTTP request as strings.
	 * @throws IOException
	 */
	protected ArrayList<String> getHttpRequest() throws IOException {
		ArrayList<String> requestData = new ArrayList<>();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(CLIENT_SOCKET.getInputStream()));
		String input;
		while ((input = reader.readLine()) != null) {
			if (input.equals("")) {
				break;
			}
			requestData.add(input);
		}
		return requestData;
	}

	/**
	 * Takes and ArrayList of strings representing the data from the HTTP
	 * request, parses the data and returns an Array of Strings 2 elements in
	 * size. The first element in the returned array is the request method as a
	 * string, the second element is the path to the requested resource as a
	 * string.
	 * 
	 * @param requestData
	 *            - ArrayList<Strings> lines of HTTP request
	 * @return String[2] request method, requested resource
	 */
	protected String[] parseHttpRequest(ArrayList<String> requestData) {
		String[] parsedRequest = new String[2];
		if (requestData.size() > 0) {
			String[] req = requestData.get(0).split(" ");
			parsedRequest[0] = req[0].trim();
			parsedRequest[1] = req[1].trim();
		}
		return parsedRequest;
	}

	/**
	 * Responds to the clients given request method with the given requested
	 * resource.
	 * 
	 * @param method
	 *            - HTTP request method
	 * @param resource
	 *            - Requested resource
	 * @throws IOException
	 */
	private void respond(String method, String resource) throws IOException {
		if (!method.equals(null)) {
			if (method.equalsIgnoreCase("GET")) {
				if (resource.equals("/")) {
					resource = "/index.html";
				}
				File file = new File(WORKING_DIRECTORY + File.separator + resource);
				if (file.isFile()) {
					sendFile(200, "OK", file);
				} else {
					sendHTML(404, "Not Found", "<h1>File not found</h1>");
				}
			} else {
				sendHTML(405, "Method Not Allowed", "<h1>Method Not Allowed</h1>");
			}
		}
	}

	/**
	 * Sends the HTTP response line along with a response body to the client
	 * browser as characters.
	 *
	 * @param code
	 *            - Http numerical status code to send to client
	 * @param status
	 *            - Http status message to send to client
	 * @param body
	 *            - the body of the data to send to the client
	 * @throws IOException
	 */
	protected void sendHTML(int code, String status, String body) throws IOException {
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(CLIENT_SOCKET.getOutputStream()));
		writer.write("HTTP/1.1 " + code + " " + status + "\r\n");
		writer.write("\r\n");
		writer.write(body);
		writer.flush();
	}

	/**
	 * Sends a given file to the client socket.
	 *
	 * @param code
	 *            - Http numerical status code to send
	 * @param status
	 *            - Http status message to send
	 * @param file
	 *            - file to send to client
	 * @throws IOException
	 */
	protected void sendFile(int code, String status, File file) throws IOException {
		OutputStream out = new DataOutputStream(CLIENT_SOCKET.getOutputStream());
		String line = "HTTP/1.1 " + code + " " + status + "\r\n";
		out.write(line.getBytes(Charset.forName("UTF-8")));
		line = "Content-Type:" + getMimeType(file) + ";\r\n";
		out.write(line.getBytes(Charset.forName("UTF-8")));
		line = "Content-Length: " + file.length() + ";\r\n";
		out.write(line.getBytes(Charset.forName("UTF-8")));
		line = "\r\n";
		out.write(line.getBytes(Charset.forName("UTF-8")));
		FileInputStream fis = new FileInputStream(file);
		int read;
		byte[] buff = new byte[1024];
		while ((read = fis.read(buff)) != -1) {
			out.write(buff, 0, read);
		}
		fis.close();
		out.flush();
	}

	/**
	 * Returns the mime type of the given file as a string.
	 *
	 * @param file
	 *            - file
	 * @return - returns string of the name of the mime type for the file.
	 */
	protected String getMimeType(File file) {
		String mimeType = "";
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
