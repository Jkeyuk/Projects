package RssFeedReader;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RssFeedReaderMain {

	public static void main(String[] args) {
		String rssFeedAddress = askUserForRssAddress();
		RssFeedReader reader = new RssFeedReader(rssFeedAddress);
		reader.displayFeed();
		int linkNum = askUserForLinkNum();
		reader.goToLink(linkNum);
	}

	private static String askUserForRssAddress() {
		String returnString;
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("");
			System.out.println("Please enter the address of the rss feed you wish to read");
			System.out.println("example: www.somefeed.com");
			returnString = scan.nextLine().trim();
		} while (!checkConnection(returnString));
		return returnString;
	}

	private static int askUserForLinkNum() {
		String input;
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("");
			System.out.println("Please enter the Feed item number of the link you wish to visit:");
			input = scan.nextLine();
		} while (!checkInt(input));
		int returnInt = Integer.parseInt(input);
		return returnInt;
	}

	private static boolean checkConnection(String s) {
		try {
			URL url = new URL("http://" + s);
			URLConnection con = url.openConnection();
			String type = con.getContentType();
			return type.endsWith("xml");
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean checkInt(String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
