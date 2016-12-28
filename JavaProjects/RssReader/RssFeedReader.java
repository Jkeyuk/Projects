package RssReader;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RssFeedReader {

	private ArrayList<String> links = new ArrayList<>();

	/**
	 * Displays the RSS feed from a given URL.
	 * 
	 * @param url
	 *            - URL to the RSS feed.
	 */
	public void readRSS(URL url) {
		ArrayList<String[]> data = parseData(getData(url));
		for (String[] strings : data) {
			System.out.println("------------------------------------------");
			System.out.println("Feed Item #" + (data.indexOf(strings) + 1));
			System.out.println();
			System.out.println("Title: " + strings[0]);
			System.out.println();
			System.out.println("Description: " + strings[1]);
			System.out.println();
			System.out.println("Link: " + strings[2]);
		}
	}

	/**
	 * Parsed the data from a given NodeList and returns as ArrayList<String[]>.
	 * 
	 * @param rssItems
	 *            - NodeList of the RSS feed.
	 * @return - ArrayList<String[]> of data from the NodeList.
	 */
	private ArrayList<String[]> parseData(NodeList rssItems) {
		ArrayList<String[]> data = new ArrayList<>();
		if (rssItems != null) {
			for (int i = 0; i < rssItems.getLength(); i++) {
				Node node = rssItems.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String[] values = new String[3];
					Element e = (Element) node;
					values[0] = e.getElementsByTagName("title").item(0).getTextContent()
							.trim();
					values[1] = e.getElementsByTagName("description").item(0)
							.getTextContent().trim();
					values[2] = e.getElementsByTagName("link").item(0).getTextContent()
							.trim();
					links.add(values[2]);
					data.add(values);
				}
			}
		}
		return data;
	}

	/**
	 * Returns a NodeList of data from the given URL.
	 * 
	 * @param url
	 *            - URL to an RSS feed.
	 * @return - NodeList of data from feed.
	 */
	private NodeList getData(URL url) {
		NodeList rssItems = null;
		try {
			DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = fac.newDocumentBuilder();
			Document doc = build.parse(url.openStream());
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			rssItems = root.getElementsByTagName("item");
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			ex.printStackTrace();
		}
		return rssItems;
	}

	/**
	 * Opens a browser to the link to a feed item represented by the given
	 * integer.
	 * 
	 * @param num
	 *            - feed number to visit.
	 */
	public void goToLink(int num) {
		if (num <= links.size()) {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI(links.get(num - 1)));
				} catch (IOException | URISyntaxException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("System does not support this...Sorry :(");
			}
		} else {
			System.out.println("That feed link number does not an exist");
		}
	}
}
