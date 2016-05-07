package rssfeedreader;

import java.util.Scanner;

public class RssFeedReaderMain {

    public static void main(String[] args) {
        //rss feed reader object
        RssFeedReader r = null;
        //sentinel to control flow
        boolean sentinel = true;
        //keeps looping until there are no errors with user input
        while (sentinel) {
            //get url of rss feed
            String feedToRead = promptUserForFeed();
            //create rss feed reader with url
            r = new RssFeedReader(feedToRead);
            //display feed and return false if no user input error  
            sentinel = r.displayFeed();
        }//ask user which link they want to go to
        promptUserForLinkNum(r);
    }

    //get url to rss feed from user
    private static String promptUserForFeed() {
        Scanner in = new Scanner(System.in);
        String feed = null;
        do {//loop until user enters valid input
            System.out.println("---------------------------------------");
            System.out.println("Please enter the RSS feed, example: www.somefeed.com");
            feed = in.nextLine();
        } while (feed.trim().equals(""));
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

}
