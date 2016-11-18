package RssFeedReader;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
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

	private URL url;
	private ArrayList<String> links = new ArrayList<>();

	public RssFeedReader(String url) {
		try {
			this.url = new URL("http://" + url);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
	}

	// display title, description, and link for each data item.
	public void displayValues() {
		ArrayList<String[]> data = parseData(getData());
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

	// parses data nodes and returns array of string arrays
	private ArrayList<String[]> parseData(NodeList rssItems) {
		ArrayList<String[]> data = new ArrayList<>();
		if (rssItems != null) {
			for (int i = 0; i < rssItems.getLength(); i++) {
				Node node = rssItems.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String[] values = new String[3];
					Element e = (Element) node;
					values[0] = e.getElementsByTagName("title").item(0).getTextContent().trim();
					values[1] = e.getElementsByTagName("description").item(0).getTextContent().trim();
					values[2] = e.getElementsByTagName("link").item(0).getTextContent().trim();
					links.add(values[2]);
					data.add(values);
				}
			}
		}
		return data;
	}

	// returns data nodes from RSS URL
	private NodeList getData() {
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

	// open browser for user and direct them to desired link
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
