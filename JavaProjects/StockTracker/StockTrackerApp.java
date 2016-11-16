package StockTracker;

import java.util.Scanner;

public class StockTrackerApp {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StockTracker tracker = new StockTracker();
		int choice = getChoice(scan);
		if (choice == 1) {
			tracker.displayQuoteData(getSymbol(scan).replaceAll("\\s", ""));
		} else if (choice == 2) {
			tracker.displayCompanyInfo(getCompanyName(scan).replaceAll("\\s", ""));
		} else {
			System.out.println("you did not enter a valid choice");
		}
		scan.close();
	}

	private static int getChoice(Scanner SCAN) {
		String input;
		do {
			System.out.println("");
			System.out.println("Please select the number corresponding to your choice");
			System.out.println("1)If you already know company stock symbol");
			System.out.println("2)If you need to look up company stock symbol or exchange");
			input = SCAN.nextLine().trim();
		} while (!checkInt(input));
		return Integer.parseInt(input);
	}

	private static String getSymbol(Scanner SCAN) {
		System.out.println("");
		System.out.println("Please enter company stock symbol");
		return SCAN.nextLine().trim();
	}

	private static String getCompanyName(Scanner SCAN) {
		System.out.println("");
		System.out.println("Please enter name of company to search");
		return SCAN.nextLine().trim();
	}

	private static boolean checkInt(String in) {
		try {
			Integer.parseInt(in);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
