package webCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import htmlParser.ElementParser;
import htmlParser.HtmlParser;

/**
 * This class represents a web crawler utility that crawls through a given URL
 * and saves the pages visited to a txt file. the crawler will only crawl pages
 * within the given URLs host and for politeness will not request more then one
 * page every half a seond.
 * 
 * @author Jonathan Keyuk
 *
 */
public class Crawler {
	private final URL URL;
	private final Queue<URL> QUE;
	private final HashSet<String> visited;

	/**
	 * Constructs the crawler with a given url.
	 * 
	 * @param url
	 *            - url to crawl.
	 */
	public Crawler(URL url) {
		this.URL = url;
		this.QUE = new LinkedList<>();
		this.visited = new HashSet<>();
	}

	/**
	 * Crawls through this crawlers URL staying within the URLs host. when
	 * finished, visited pages are saved to a txt file with the URL host name,
	 * the txt file is saved to the current working directory. Crawler will not
	 * visit more then the given number of pages. For politeness the crawler
	 * will not request more then on page every half a second, this causes large
	 * websites to take a long time depending on the given number of pages to
	 * visit.
	 * 
	 * @param numPages
	 */
	public void crawl(int numPages) {
		getQue().add(URL);
		while (!getQue().isEmpty() && visited.size() != numPages) {
			URL current = getQue().poll();
			getVisited().add(current.toString());
			ArrayList<String> links = HtmlParser.getElements("a", sendGet(current));
			links.forEach(link -> {
				URL newLink = getNewLink(current, link);
				if (newLink != null && !visited.contains(newLink.toString())
						&& getUrl().getHost().equals(newLink.getHost())
						&& !getQue().contains(newLink)) {
					getQue().offer(newLink);
				}
			});
		}
		saveLinks();
	}

	/**
	 * sends a Http get request to the given url and returns the response from
	 * the server. This method delays half a second to be polite to the server
	 * when requesting multiple pages. This method only returns the response if
	 * the server responds with a 200 (ok) status code.
	 * 
	 * @param url
	 *            - url to send request to
	 * @return - response from url
	 */
	private static String sendGet(URL url) {
		try {
			Thread.sleep(500);
			StringBuffer response = new StringBuffer();
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "JK_Test_Crawler (Jonkero2@gmail.com)");
			if (con.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();
			}
			return response.toString();
		} catch (IOException | InterruptedException e) {
			return "";
		}
	}

	/**
	 * Parses the href address of the given html element and returns a new URL
	 * to the address.
	 * 
	 * @param current
	 *            - url the element was found on
	 * @param element
	 *            - the link element to parse.
	 * @return - url to the parsed address
	 */
	private static URL getNewLink(URL current, String element) {
		try {
			HashMap<String, String> attributes = ElementParser.getAttributes(element);
			String linkAddress = attributes.get("href");
			if (linkAddress == null) {
				linkAddress = "";
			}
			return new URL(current, linkAddress);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * Writes the links this crawler has visited to the currnet working
	 * directory
	 */
	private void saveLinks() {
		File file = new File(getUrl().getHost() + ".txt");
		try (FileWriter writer = new FileWriter(file)) {
			for (String link : getVisited()) {
				writer.write(link + System.lineSeparator());
			}
			writer.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * returns the url of this crawler
	 * 
	 * @return - url of the crawler
	 */
	private URL getUrl() {
		return URL;
	}

	/**
	 * returns the queue of this crawler
	 * 
	 * @return - queue of the crawler
	 */
	private Queue<URL> getQue() {
		return QUE;
	}

	/**
	 * returns the hashset of visited links of this crawler
	 * 
	 * @return - hashset of visited links
	 */
	private HashSet<String> getVisited() {
		return visited;
	}
}
