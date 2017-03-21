package WebServer;

import java.io.File;
import java.util.Scanner;

/**
 * This Program starts a server to serve files to a web browser using HTTP.
 * 
 * @author jonathan
 *
 */
public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String directory = getWorkingDirectory(scan);
		int port = getPort(scan);
		WebServer server = new WebServer(directory, port);
		server.start();
		scan.close();
		System.exit(0);
	}

	private static String getWorkingDirectory(Scanner scan) {
		String directory = "";
		do {
			System.out.println("Please enter path to the working directory.");
			directory = scan.nextLine();
		} while (!new File(directory).isDirectory());
		return directory;
	}

	private static int getPort(Scanner scan) {
		System.out.println("Please enter the port to start server on");
		int port = scan.nextInt();
		return port;
	}
}
