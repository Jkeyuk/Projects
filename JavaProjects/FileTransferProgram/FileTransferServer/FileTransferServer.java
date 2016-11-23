package FileTransferProgram.FileTransferServer;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * File transfer server accepts connections from clients to upload, download,
 * view, and remove files.
 */
final class FileTransferServer {

	private final ExecutorService EXEC = Executors.newFixedThreadPool(100);
	private final Scanner SCAN = new Scanner(System.in);
	private ServerSocket serverSocket;
	private String workingDirectory;

	public static void main(String[] args) {
		FileTransferServer server = new FileTransferServer();
		server.setWorkingDirectory();
		server.startServer();
		server.listenForShutdown();
		System.out.println("To shutdown server type in: !shutdown");
	}

	/**
	 * Prompts user for working directory for the server.
	 */
	private void setWorkingDirectory() {
		String input;
		do {
			System.out.println("Plese enter the working directory for the server");
			input = SCAN.nextLine().trim();
		} while (!new File(input).isDirectory());
		workingDirectory = input;
	}

	/**
	 * Opens a separate thread to wait for connections to the socket. Each
	 * connection is handled in a separate thread.
	 */
	private void startServer() {
		Runnable serverStart = () -> {
			try {
				serverSocket = new ServerSocket(0);
				System.out.println("Server started on port: " + serverSocket.getLocalPort());
				while (true) {
					Socket clientSock = serverSocket.accept();
					EXEC.execute(new ClientConnection(clientSock, workingDirectory));
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		};
		EXEC.execute(serverStart);
	}

	/**
	 * Starts thread to wait for server shutdown command.
	 */
	private void listenForShutdown() {
		Runnable shutdownListener = () -> {
			while (true) {
				String input = SCAN.nextLine();
				if (input.equalsIgnoreCase("!shutdown")) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						SCAN.close();
						System.exit(0);
					}
				}
			}
		};
		EXEC.execute(shutdownListener);
	}
}
