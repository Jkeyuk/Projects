package FileTransferProgram.FileTransferClient;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * File Transfer Client allows users to connect to the file transfer server.
 * Once connected to the server, users can view files, upload, download, or
 * remove files from the servers working directory.
 */
public class FileTransferClient {

	public static void main(String[] args) {
		if (args.length != 2) {
			UsageMessage();
		} else {
			Scanner scanner = new Scanner(System.in);
			FTConnection server = new FTConnection(args[0], Integer.parseInt(args[1]));
			System.out.println("connected to server");
			while (true) {
				String userCommand = scanner.nextLine();
				if (userCommand.equalsIgnoreCase("!shutdown")) {
					scanner.close();
					server.close();
					System.exit(0);
				} else if (userCommand.startsWith("!upload")) {
					sendFile(userCommand.replace("!upload ", ""), server);
				} else if (userCommand.startsWith("!download")) {
					server.sendMessage(userCommand);
					server.recieveFile(userCommand.replace("!download ", ""));
					System.out.println("File Received");
				} else if (userCommand.equalsIgnoreCase("!showfiles")) {
					server.sendMessage(userCommand);
					displayStrings(server.receiveMessage());
				} else if (userCommand.startsWith("!openFolder")) {
					server.sendMessage(userCommand);
					displayStrings(server.receiveMessage());
				} else {
					server.sendMessage(userCommand);
				}
			}
		}
	}

	/**
	 * Prints message instructing users on proper usage.
	 */
	private static void UsageMessage() {
		System.out.println("Proper Usage");
		System.out.println("java FileTransferClient [IP] [Port]");
		System.out.println("Example:");
		System.out.println("java FileTransferClient 127.0.0.1 53");
	}

	/**
	 * Sends a given file to a given file transfer server.
	 * 
	 * @param filePath
	 *            - String representing pathname to file
	 * @param server
	 *            - FTConnection of server to send file.
	 */
	private static void sendFile(String filePath, FTConnection server) {
		File file = new File(filePath);
		if (file.isFile()) {
			server.sendMessage("!uploading " + file.getName());
			server.sendFile(file);
			System.out.println("File Sent");
		} else {
			System.out.println("Cannot find file");
		}
	}

	/**
	 * Prints strings from a given array list.
	 */
	private static void displayStrings(ArrayList<String> strings) {
		for (String string : strings) {
			System.out.println(string);
		}
	}

}
