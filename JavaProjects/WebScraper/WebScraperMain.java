package webscraper;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class WebScraperMain {

    public static void main(String[] args) {
        //get website to scrape from user
        String website = askUserForWebsite();
        //build scraper object with input
        Scraper scraper = new Scraper(website);
        //scrape site and save links
        scraper.saveLinks();
        System.out.println("Website has been scraped, program shutting down");
    }

    private static String askUserForWebsite() {
        String returnString;
        Scanner scan = new Scanner(System.in);
        do {//keeps looping until no errors with user input
            System.out.println("");
            System.out.println("Please enter the address of the website to scrape");
            System.out.println("example: www.google.com");
            returnString = scan.nextLine();
        } while (!checkUserInput(returnString));
        return returnString;
    }

    private static boolean checkUserInput(String i) {
        try {//validate user input
            URL url = new URL("http://" + i);
            URLConnection con = url.openConnection();
            return con.getContentType().startsWith("text/html");
        } catch (Exception e) {
            return false;
        }
    }
}
