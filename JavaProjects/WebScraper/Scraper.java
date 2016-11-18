package WebScraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

	// url object to hold url of page to scrape
	private URL url;

	public Scraper(String url) {
		try {
			this.url = new URL("http://" + url);
			;
		} catch (MalformedURLException ex) {
			Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// save links to text file
	public void saveLinks() {
		ArrayList<String> links = getLinks();
		File file = new File(url.getHost() + ".txt");
		try (FileWriter writer = new FileWriter(file)) {
			for (String link : links) {
				writer.write(link + System.lineSeparator());
			}
			writer.flush();
		} catch (IOException ex) {
			Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// return array of links from the html
	private ArrayList<String> getLinks() {
		ArrayList<String> returnArray = new ArrayList<>();
		Document doc = Jsoup.parse(rawHTML());
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
			String linkText = link.attr("href");
			if (linkText.charAt(0) == '/') {
				returnArray.add("http://" + url.getHost() + linkText);
			} else {
				returnArray.add(linkText);
			}
		}
		return returnArray;
	}

	// return raw HTML as string
	private String rawHTML() {
		String returnString = "";
		try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				returnString += " " + inputLine;
			}
		} catch (IOException ex) {
			Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return returnString;
	}

}
