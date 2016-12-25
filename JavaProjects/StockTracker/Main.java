package StockTracker;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StockTracker tracker = new StockTracker();
		int choice = getInt(scan);
		if (choice == 1) {
			tracker.displayQuoteData(
					getString("Enter company stock ticker:", scan).replaceAll("\\s", ""));
		} else if (choice == 2) {
			tracker.displayCompanyInfo(
					getString("Enter company name:", scan).replaceAll("\\s", ""));
		}
		scan.close();
	}

	private static int getInt(Scanner SCAN) {
		String input;
		String prompt = "Options:" + System.lineSeparator() + "1)Lookup quote."
				+ System.lineSeparator() + "2)Search for company symbol."
				+ System.lineSeparator();
		do {
			input = getString(prompt, SCAN);
		} while (!checkInt(input));
		return Integer.parseInt(input);
	}

	private static boolean checkInt(String in) {
		try {
			Integer.parseInt(in);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String getString(String message, Scanner scanner) {
		System.out.println(message);
		return scanner.nextLine().trim();
	}
}
