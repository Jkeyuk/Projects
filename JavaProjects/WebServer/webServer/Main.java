package webServer;

import java.io.File;
import java.util.Scanner;

/**
 * Entry point for the web server application.
 * 
 * @author jonathan
 *
 */
public class Main {

	/**
	 * Prompts user for input to start the web server.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String directory = getWorkingDirectory(scan);
		int port = getPort(scan);
		WebServer server = new WebServer(directory, port);
		server.start();
		while (true) {
			if (scan.nextLine().equals("!shutdown")) {
				server.shutDown();
				break;
			}
		}
		scan.close();
	}

	/**
	 * Prompts user for directory unit a valid directory is given
	 * 
	 * @param scan
	 *            - scanner to scan input
	 * @return - directory given from user.
	 */
	private static String getWorkingDirectory(Scanner scan) {
		String directory = "";
		do {
			System.out.println("Please enter path to the working directory.");
			directory = scan.nextLine();
		} while (!new File(directory).isDirectory());
		return directory;
	}

	/*
	 * Prompts user for port nummber.
	 */
	private static int getPort(Scanner scan) {
		System.out.println("Please enter the port number to start server on");
		int port = scan.nextInt();
		return port;
	}
}
