package FileTransferProgram;

import java.util.Scanner;

/**
 * This program allows users to connect to and run their own file transfer
 * servers.
 * 
 * @author Jonathan Keyuk
 *
 */
public class FileTransferProgram {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.println("Please choose the number of the following options");
		System.out.println("1. Connect to a file transfer server");
		System.out.println("2. Start a file transfer server");
		System.out.println("3. Exit program");

		int option = getInt("Enter your choice", s);

		if (option == 1) {
			String ip = getString("Please enter IP to connect to:", s);
			int port = getInt("Please enter port to connect to", s);
			FTClient client = new FTClient(ip, port);
			client.start();
		} else if (option == 2) {
			String workingDirectory = getString("Please enter the path to the working directory", s);
			int port = getInt("Please enter port to open server on.", s);
			FTServer server = new FTServer(workingDirectory);
			server.startServer(port);
			System.out.println("Server Started");
		} else if (option == 3) {
			s.close();
			System.exit(0);
		}
	}

	/**
	 * Prompts user with a message and scans line for an integer. returns
	 * inputed integer.
	 * 
	 * @param message
	 *            - message to prompt user
	 * @param scanner
	 *            - scanner to scan input
	 * @return - integer inputed from user.
	 */
	private static int getInt(String message, Scanner scanner) {
		String num;
		do {
			num = getString(message, scanner);
		} while (!checkInt(num));
		return Integer.parseInt(num);
	}

	/**
	 * Returns true if given string can be turned into a integer.
	 * 
	 * @param i
	 *            - string to check
	 * @return - boolean
	 */
	private static boolean checkInt(String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * prompts user with a given message and scans input for string. inputed
	 * string is returned.
	 * 
	 * @param m
	 *            - message to prompt to user
	 * @param scan
	 *            - scanner to scan input
	 * @return - string inputed from user.
	 */
	private static String getString(String m, Scanner scan) {
		System.out.println(m);
		return scan.nextLine();
	}
}
