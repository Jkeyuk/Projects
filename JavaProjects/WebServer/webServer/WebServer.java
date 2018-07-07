package webServer;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A Server to serve files to a client using HTTP.
 * 
 * @author Jonathan Keyuk
 *
 */
public class WebServer {

	private final File WORKING_DIRECTORY;
	private final ExecutorService EXEC;
	private final int PORT;
	private ServerSocket server;

	/**
	 * WebServer is built with the servers working directory and a port number
	 * to connect to.
	 *
	 * @param directoryPath
	 *            - directory to host server files
	 * @param port
	 *            - port to receive connection through
	 */
	public WebServer(String directoryPath, int port) {
		this.WORKING_DIRECTORY = new File(directoryPath);
		this.PORT = port;
		this.EXEC = Executors.newCachedThreadPool();
	}

	/**
	 * Starts the server as a runnable in its own thread.
	 */
	public void start() {
		Runnable startServer = () -> {
			try {
				server = new ServerSocket(PORT);
				System.out.println("Server started..type !shutdown to shutdown.");
				while (true) {
					Socket clientSock = server.accept();
					EXEC.execute(new RequestHandler(WORKING_DIRECTORY, clientSock));
				}
			} catch (SocketException e) {
				System.out.println("Shutting down...");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		};
		EXEC.execute(startServer);
	}

	/**
	 * Shuts down the server.
	 */
	public void shutDown() {
		try {
			if (server != null) server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		EXEC.shutdownNow();
	}
}
