package rssfeedreader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RssFeedReaderMain {

    public static void main(String[] args) {
        //get feed address from user
        String rssFeedAddress = askUserForRssAddress();
        //use address to build reader object
        RssFeedReader reader = new RssFeedReader(rssFeedAddress);
        //display rss feed
        reader.displayFeed();
        //get link number user wishes to visit
        int linkNum = askUserForLinkNum();
        //open browser for user and direct to link
        reader.goToLink(linkNum);
    }

    private static String askUserForRssAddress() {
        //return string 
        String returnString;
        //object to scan terminal input
        Scanner scan = new Scanner(System.in);
        do {//keeps looping untill no errors with input
            System.out.println("");
            System.out.println("Please enter the address of the rss feed you wish to read");
            System.out.println("example: www.somefeed.com");
            returnString = scan.nextLine().trim();
        } while (!checkConnection(returnString));
        return returnString;
    }

    private static int askUserForLinkNum() {
        //string to hold input
        String input;
        //object to scan terminal
        Scanner scan = new Scanner(System.in);
        do {//keeps looping until no errors with user input
            System.out.println("");
            System.out.println("Please enter the Feed item number of the link you wish to visit:");
            input = scan.nextLine();
        } while (!checkInt(input));
        //cast input to int
        int returnInt = Integer.parseInt(input);
        return returnInt;
    }

    private static boolean checkConnection(String s) {
        try {//check user input for valid connection
            URL url = new URL("http://" + s);
            URLConnection con = url.openConnection();
            String type = con.getContentType();
            return type.endsWith("xml");
        } catch (NullPointerException | IllegalArgumentException ex) {
            return false;
        } catch (MalformedURLException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
    }

    private static boolean checkInt(String i) {
        try {//check user input for valid input
            int test = Integer.parseInt(i);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
