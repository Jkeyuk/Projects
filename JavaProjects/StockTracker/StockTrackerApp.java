package stocktracker;

import java.util.Scanner;

public class StockTrackerApp {

    private final Scanner SCAN = new Scanner(System.in);

    public static void main(String[] args) {
        StockTrackerApp app = new StockTrackerApp();
        StockTracker tracker = new StockTracker();
        int choice = app.getChoice();
        if (choice == 1) {
            String input = app.getSymbol();
            tracker.getStockQuote(input);
        } else if (choice == 2) {
            String input = app.getCompanyName();
            tracker.searchForCompanyInfo(input);
        } else {
            System.out.println("you did not enter a valid choice");
        }
    }

    private int getChoice() {
        String input;
        do {
            System.out.println("");
            System.out.println("Please select the number corresponding to your choice");
            System.out.println("1)If you already know company stock symbol");
            System.out.println("2)If you need to look up company stock symbol or exchange");
            input = SCAN.nextLine().trim();
        } while (!checkInt(input));
        int returnInt = Integer.parseInt(input);
        return returnInt;
    }

    private String getSymbol() {
        System.out.println("");
        System.out.println("Please enter company stock symbol");
        String input = SCAN.nextLine().trim();
        return input;
    }

    private String getCompanyName() {
        System.out.println("");
        System.out.println("Please enter name of company to search");
        String input = SCAN.nextLine().trim();
        return input;
    }

    private boolean checkInt(String in) {
        try {
            int x = Integer.parseInt(in);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
