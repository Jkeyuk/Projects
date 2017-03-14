package FileTransferProgram;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FTServer represents a file transfer server that a FTClient will connect to
 * and use.
 * 
 * @author Jonathan Keyuk
 *
 */
public class FTServer {
	private final ExecutorService EXEC = Executors.newFixedThreadPool(100);
	private final File dir;
	private final String dirPath;
	private ServerSocket serverSocket;

	public FTServer(String workingDirectory) {
		this.dir = new File(workingDirectory);
		this.dirPath = dir.getAbsolutePath() + File.separator;
	}

	/**
	 * This method starts the server on a given port and waits for connections
	 * over the server socket. connections are then given and handled by
	 * waitForRequests method.
	 * 
	 * @param port
	 *            - port to start server on
	 */
	public void startServer(int port) {
		Runnable serverStart = () -> {
			try {
				serverSocket = new ServerSocket(port);
				while (true) {
					Socket clientSock = serverSocket.accept();
					waitForRequests(clientSock);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		};
		EXEC.execute(serverStart);
	}

	/**
	 * This method starts a thread to listen over the socket for commands from
	 * the client. commands are given to handle response method.
	 * 
	 * @param clientSock
	 *            - socket to listen over
	 */
	private void waitForRequests(Socket clientSock) {
		Runnable r = () -> {
			try {
				Connection clientCon = new Connection(clientSock);
				clientCon.receiveMessages(x -> handleResponse(x, clientCon));
			} catch (IOException e) {
				System.out.println(clientSock.getRemoteSocketAddress().toString());
				System.out.println("has disconnected");
			}
		};
		EXEC.execute(r);
	}

	/**
	 * This method takes a given command from a given connection and calls the
	 * appropriate methods for each command.
	 * 
	 * @param request
	 *            - request to handle
	 * @param c
	 *            - connection that sent the request.
	 */
	private void handleResponse(String request, Connection c) {
		try {
			if (request.equalsIgnoreCase("!showFiles")) {
				sendFileList(dir.listFiles(), c);
			} else if (request.startsWith("!openFolder") && isValidFileReq(request)) {
				sendFileList(new File(dirPath + request.split(" ")[1]).listFiles(), c);
			} else if (request.startsWith("!delete") && isValidFileReq(request)) {
				removeFile(new File(dirPath + request.split(" ")[1]));
			} else if (request.startsWith("!download") && isValidFileReq(request)) {
				c.sendMessage("OK");
				c.sendFile(new File(dirPath + request.split(" ")[1]));
			} else if (request.startsWith("!upload") && directorySafe(request)) {
				c.recieveFile(new File(dirPath + request.split(" ")[1]));
			} else {
				c.sendMessage("BAD");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a string naming each file of a given file list to a given
	 * connection.
	 * 
	 * @param fileList
	 *            - file list to read
	 * @param c
	 *            - connection to send list to
	 * @throws IOException
	 */
	private void sendFileList(File[] fileList, Connection c) throws IOException {
		c.sendMessage("OK");
		if (fileList != null) {
			c.sendMessage("----------------------------------------");
			c.sendMessage("Files:");
			for (File file : fileList) {
				if (file.isDirectory()) {
					c.sendMessage(file.getName() + "(Folder)");
				} else {
					c.sendMessage(file.getName());
				}
			}
		}
		c.sendMessage("@END@");
	}

	/**
	 * Recursively removes a given file or directory.
	 * 
	 * @param file
	 *            - file or directory to delete.
	 */
	private void removeFile(File file) {
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (File f : list) {
				removeFile(f);
			}
			file.delete();
		} else {
			file.delete();
		}
	}

	/**
	 * Returns true if a given string does not try to escape the root directory,
	 * and represents a valid file or directory in the root directory.
	 * 
	 * @param request
	 *            - request to validate
	 * @return - true if string does not escape root directory and represents a
	 *         valid file or directory.
	 */
	private boolean isValidFileReq(String request) {
		String[] requestParts = request.split(" ");
		if (requestParts.length == 2) {
			String filePath = dirPath + requestParts[1];
			return directorySafe(filePath)
					&& (new File(filePath).isFile() || new File(filePath).isDirectory());
		} else {
			return false;
		}
	}

	/**
	 * Returns true if a given string which represents a file path does not try
	 * to escape the root directory.
	 * 
	 * @param filePath
	 *            - string to validate
	 * @return - true if string does not try to escape the directory.
	 */
	private boolean directorySafe(String filePath) {
		return !(filePath.contains("/..") || filePath.contains("\\.."));
	}
}
