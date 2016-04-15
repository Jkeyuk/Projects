package webscraper;

public class WebScraperMain {

    public static void main(String[] args) {
        Scraper scraper = new Scraper("http://www.oracle.com/");
             
        scraper.saveLinks();
    }
}
