package WebServer;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A Server to serve files to a clients browser using HTTP.
 * 
 * @author jonathan
 *
 */
public class WebServer {

	private final File WORKING_DIRECTORY;
	private final ExecutorService EXEC = Executors.newFixedThreadPool(100);
	private final int PORT;
	private ServerSocket server;

	/**
	 * Server object is built with a working directory and a port number
	 *
	 * @param WORKING_DIRECTORY
	 *            - directory to host server files
	 * @param port
	 *            - port to receive connection through
	 */
	public WebServer(String WORKING_DIRECTORY, int port) {
		this.WORKING_DIRECTORY = new File(WORKING_DIRECTORY);
		this.PORT = port;
	}

	/**
	 * start method starts the server on a given port. Each request is handled
	 * in a separate thread by a request handler object.
	 */
	public void start() {
		try {
			server = new ServerSocket(PORT);
			System.out.println("Server started");
			System.out.println("Type !shutdown to shut down server.");
			listenForShutdown();
			while (true) {
				Socket clientSock = server.accept();
				EXEC.execute(new RequestHandler(WORKING_DIRECTORY, clientSock));
			}
		} catch (SocketException e) {
			System.out.println("Server Shutting Down...");
			System.exit(0);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * starts a separate thread to listen for shutdown of the server by the
	 * user.
	 */
	private void listenForShutdown() {
		Runnable listen = () -> {
			Scanner scan = new Scanner(System.in);
			while (true) {
				if (scan.nextLine().equals("!shutdown")) {
					try {
						server.close();
						EXEC.shutdown();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		EXEC.execute(listen);
	}
}
