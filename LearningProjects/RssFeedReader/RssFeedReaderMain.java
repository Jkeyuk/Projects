package rssfeedreader;

import java.io.IOException;
import java.util.Scanner;
import org.xml.sax.SAXException;

public class RssFeedReaderMain {

    public static void main(String[] args) {
        //sentinel to control flow
        boolean sentinel = true;
        //keeps looping until there are no errors with user input
        while (sentinel) {
            //get url of rss feed
            String feedToRead = promptUserForFeed();
            //create rss feed reader with url
            RssFeedReader r = new RssFeedReader(feedToRead);
            try {//display feed and turn off loop
                r.displayFeed();
                sentinel = false;
                //prompt user for link they wish to visit
                promptUserForLinkNum(r);
            } catch (IOException | SAXException ex) {//if error print error message and re loop              
                errorM();
            }
        }
    }

    //get url to rss feed from user
    private static String promptUserForFeed() {
        Scanner in = new Scanner(System.in);
        System.out.println("---------------------------------------");
        System.out.println("Please enter the RSS feed, example: www.somefeed.com");
        String feed = in.nextLine();
        return feed;
    }

    //prompt user for link they wish go to
    private static void promptUserForLinkNum(RssFeedReader r) {
        String num;//<--string to hold input
        Scanner in = new Scanner(System.in);
        do {//keeps looping unitl user enters integer
            System.out.println("---------------------------------------");
            System.out.println("Enter feed number of link you wish to visit");
            num = in.nextLine();
        } while (checkInt(num));
        //convert input into int
        int number = Integer.parseInt(num);
        //open browser and go to feed numbers link
        r.goToLink(number);
    }

    //check if num is int
    private static boolean checkInt(String n) {
        try {//if int turn off loop, if not return true to keep loop
            Integer.parseInt(n);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    //error message to display if user input bad value
    private static void errorM() {
        System.out.println("-----------------------------------------");
        System.out.println("There was an error with your input, please try again");
    }
}
