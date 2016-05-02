package webscraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebScraperMain {

    public static void main(String[] args) {
        boolean sentinel = true;
        while (sentinel == true) {
            String site = promptUserForSite();
            try {
                sentinel = handleInput(site);
            } catch (MalformedURLException ex) {
                Logger.getLogger(WebScraperMain.class.getName()).log(Level.SEVERE, null, ex);
                errorMessage();
            } catch (IOException ex) {
                Logger.getLogger(WebScraperMain.class.getName()).log(Level.SEVERE, null, ex);
                errorMessage();
            }
        }
    }

    public static String promptUserForSite() {
        //create scanner to read terminal input
        Scanner in = new Scanner(System.in);
        //print message
        System.out.println("*************************************************");
        System.out.println("To Exit just enter exit...otherwise");
        System.out.println("Please enter the website to scrape..do not include http://");
        //get input from user
        String returnString = in.nextLine();
        //return input as string
        return returnString;
    }

    public static boolean handleInput(String site) throws MalformedURLException, IOException {
        //if input is exit, turn off loop
        if (site.equals("exit")) {
            System.out.println("shutting down program...");
            return false;
        } else {//else scrape website
            System.out.println("Scrapping site now...");
            Scraper scraper;
            scraper = new Scraper("http://" + site);
            scraper.saveLinks();
            return true;
        }
    }

    public static void errorMessage() {
        System.out.println("**********************************************");
        System.out.println("There has been an error with the webiste you entered");
        System.out.println("an example of a website would be: www.google.com");
        System.out.println("Please retry your input again");
        System.out.println("**********************************************");
    }
}
