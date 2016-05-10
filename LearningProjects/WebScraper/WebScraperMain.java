package webscraper;

import java.util.Scanner;

public class WebScraperMain {

    public static void main(String[] args) {
        //sentinel to control flow
        boolean sentinel = true;
        //keeps looping if there are errors from user input
        while (sentinel == true) {
            //prompt user for website to scrape
            String site = promptUserForSite().trim();
            sentinel = handleInput(site);
        }
    }

    public static String promptUserForSite() {
        //create scanner to read terminal input
        Scanner in = new Scanner(System.in);
        //print message
        System.out.println("*************************************************");
        System.out.println("To Exit just enter exit...otherwise");
        System.out.println("Please enter the website to scrape..example: www.example.com");
        //get input from user
        String returnString = in.nextLine();
        //return input as string
        return returnString;
    }

    public static boolean handleInput(String site) {
        //if input is exit, turn off loop by returning false
        if (site.equalsIgnoreCase("exit")) {
            System.out.println("shutting down program...");
            return false;//<--signal sentinel to exit loop
        } else if (site.equals("")) {
            return true;//<--signal sentinel to keep looping
        } else {//else scrape website
            System.out.println("Scrapping site now...");
            Scraper scraper;
            scraper = new Scraper("http://" + site);
            scraper.saveLinks();
            return true;//<--signal sentinel to keep looping
        }
    }
}
