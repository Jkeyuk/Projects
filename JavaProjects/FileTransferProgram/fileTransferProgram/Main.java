package fileTransferProgram;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String workingDirectory = getString("Please enter the path to the working directory", s);
		System.out.println("\nPlease choose the number of the following options");
		System.out.println("1. Connect to a file transfer server");
		System.out.println("2. Start a file transfer server");
		System.out.println("3. Exit program");
		int option = getInt("Enter your choice", s);
		if (option == 1) {
			String ip = getString("Please enter IP to connect to:", s);
			int port = getInt("Please enter port to connect to", s);
			FTclient client = new FTclient(workingDirectory);
			client.start(ip, port);
		} else if (option == 2) {
			int port = getInt("Please enter port to open server on.", s);
			FTserver server = new FTserver(workingDirectory);
			server.start(port);
			System.out.println("Server Started");
		} else if (option == 3) {
			s.close();
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
	 * prompts user with a given message and returns user input.
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
