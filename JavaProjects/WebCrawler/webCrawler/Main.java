package webCrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Entry point for the web crawler application
 * @author Jonathan Keyuk
 *
 */
public class Main {

	public static void main(String[] args) {
		try {
			Scanner scan = new Scanner(System.in);
			// get url from user
			System.out.println("\nPlease enter a full URL to crawl");
			System.out.println("example: https://github.com");
			System.out.print("URL: ");
			String url = scan.nextLine();
			// get number of pages to crawl
			System.out.println("Please enter the maximum number of pages to crawl");
			System.out.print("NUM: ");
			int num = scan.nextInt();
			scan.close();
			// construct crawler
			Crawler crawler = new Crawler(new URL(url));
			// Start crawling
			System.out.println("Crawling now, takes half a second per page.....");
			crawler.crawl(num);
			System.out.println("finished links saved in current directory");
		} catch (MalformedURLException e) {
			System.out.println("There was a problem with the given URL");
		} catch (InputMismatchException e) {
			System.out.println("There was a problem with the number of pages entered");
		}
	}

}
