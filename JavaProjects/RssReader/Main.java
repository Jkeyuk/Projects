package RssReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * This application allows users to read a RSS feed in the terminal window.
 * 
 * @author Jonathan Keyuk
 *
 */
public class Main {

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws MalformedURLException {
		if (args.length == 1) {
			String address = checkAddress(args[0]);
			if (checkConnection(address)) {
				RssFeedReader reader = new RssFeedReader();
				reader.readRSS(new URL(address));
				int linkNum = getInt("Enter feed number to visit link:");
				reader.goToLink(linkNum);
			} else {
				System.out.println("Could not connect");
			}
		} else {
			System.out.println("Usage: RssFeedReader.Main [URL]");
			System.out.println("Example: RssFeedReader.Main www.somefeed.com");
		}
	}

	/**
	 * Returns string with http:// added to the front. if http:// is already
	 * present in the string. the original string is returned.
	 * 
	 * @param s
	 *            - string to add http:// to.
	 * @return - string with http:// added to front if not already present.
	 */
	private static String checkAddress(String s) {
		if (!s.startsWith("http://") && !s.startsWith("https://")) {
			s = "http://" + s;
		}
		return s;
	}

	/**
	 * Returns true if the given string connects to a RSS feed.
	 * 
	 * @param s
	 *            - address to check
	 * @return - true if given string is address to RSS feed.
	 */
	private static boolean checkConnection(String s) {
		System.out.println(s);
		try {
			URL url = new URL(s);
			URLConnection con = url.openConnection();
			String type = con.getContentType();
			return type.endsWith("xml") || type.contains("text/xml");
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Prompts user with a given message until a valid integer is entered.
	 * 
	 * @param message
	 *            - message to display to user
	 * @return - integer inputed from user.
	 */
	private static int getInt(String message) {
		String input;
		do {
			System.out.println(System.lineSeparator() + message);
			input = scan.nextLine();
		} while (!checkInt(input));
		return Integer.parseInt(input);
	}

	/**
	 * Returns true if the given string can be parsed into a integer without
	 * throwing an exception.
	 * 
	 * @param i
	 *            - string to read
	 * @return - True if string can be parsed into integer.
	 */
	private static boolean checkInt(String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
