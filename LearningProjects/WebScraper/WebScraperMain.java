package webscraper;

import java.util.Scanner;

public class WebScraperMain {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("To Exit just enter exit...otherwise");
        while (true) {
            System.out.println("Please enter the website to scrape..do not include http://");
            String site = scan.nextLine();
            if (site.equals("exit")) {
                System.out.println("shutting down program...");
                break;
            } else {
                System.out.println("Scrapping site now...");
                Scraper scraper = new Scraper("http://" + site);
                scraper.saveLinks();
            }
        }
    }
}
