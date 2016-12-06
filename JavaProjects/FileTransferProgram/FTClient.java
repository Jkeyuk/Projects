package FileTransferProgram;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * FTClient represents a user that will connect to and use a FT server.
 * 
 * @author Jonathan Keyuk
 *
 */
public class FTClient {

	private Connection connection;

	public FTClient(String IP, int port) {
		try {
			Socket sock = new Socket();
			sock.connect(new InetSocketAddress(IP, port));
			this.connection = new Connection(sock);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Start method waits for user commands and calls the appropriate method for
	 * the appropriate command.
	 */
	public void start() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String userCommand = scanner.nextLine().trim();
			if (userCommand.equalsIgnoreCase("!shutdown")) {
				scanner.close();
				System.exit(0);
			} else if (userCommand.startsWith("!upload")) {
				sendFile(userCommand);
			} else if (userCommand.startsWith("!download")) {
				receiveFile(userCommand);
			} else if (userCommand.equalsIgnoreCase("!showfiles")) {
				displayResponse(userCommand);
			} else if (userCommand.startsWith("!openFolder")) {
				displayResponse(userCommand);
			} else if (userCommand.startsWith("!delete")) {
				connection.sendMessage(userCommand);
			} else {
				System.out.println("Not a valid command");
			}
		}
	}

	/**
	 * This checks a given command to ensure it references a valid file. if file
	 * is valid, file is sent to server. otherwise an error message is displayed
	 * 
	 * @param userCommand
	 *            - command to validate
	 */
	private void sendFile(String userCommand) {
		try {
			File file = new File(userCommand.replace("!upload ", ""));
			if (file.isFile()) {
				connection.sendMessage("!upload " + file.getName());
				connection.sendFile(file);
				System.out.println("File Sent");
			} else {
				System.out.println("That file does not exist.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This sends a given download command to the server and waits for a
	 * response. If response is OK, then the file is down loaded. otherwise
	 * error message is displayed.
	 * 
	 * @param userCommand
	 *            - command to send
	 */
	private void receiveFile(String userCommand) {
		try {
			connection.sendMessage(userCommand);
			if (connection.getMessage().equals("OK")) {
				File file = new File(userCommand.replace("!download ", ""));
				File parent = file.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}
				file.createNewFile();
				connection.recieveFile(file);
				System.out.println("File Received");
			} else {
				System.out.println("problem with request, please try again");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This file sends a given showFiles or openFolder command to the server. if
	 * response is OK, message from server is displayed to console. otherwise
	 * error message is displayed.
	 * 
	 * @param userCommand
	 *            - command to send to server
	 */
	private void displayResponse(String userCommand) {
		try {
			connection.sendMessage(userCommand);
			if (connection.getMessage().equals("OK")) {
				connection.receiveMessages(System.out::println);
			} else {
				System.out.println("problem with request, please try again");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
