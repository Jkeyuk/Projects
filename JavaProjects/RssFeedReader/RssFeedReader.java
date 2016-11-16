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

    //displays feed in terminal window
    public void displayFeed() {
        //get node list from url stream 
    	NodeList rssItems = getNodeList();
        //make sure RSS items were loaded
        if (rssItems != null) {
            //for each items node display title, description, and link
            for (int i = 0; i < rssItems.getLength(); i++) {
                Node node = rssItems.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //cast node to element
                    Element e = (Element) node;
                    //get title element text
                    String title
                            = e.getElementsByTagName("title").item(0).getTextContent().trim();
                    //get description element text
                    String description
                            = e.getElementsByTagName("description").item(0).getTextContent().trim();
                    //get link element text
                    String link
                            = e.getElementsByTagName("link").item(0).getTextContent().trim();
                    links.add(link);//<--add links to array
                    //display feed number and values
                    displayValues(i, title, description, link);
                }
            }
        }
    }

    //returns node list from RSS URL
    private NodeList getNodeList() {
    	NodeList rssItems = null;
        try {//build document factory
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            //make document builder
            DocumentBuilder build = fac.newDocumentBuilder();
            //build document with builder and url stream
            Document doc = build.parse(url.openStream());
            //normalize document
            doc.getDocumentElement().normalize();
            //extract root element from document
            Element root = doc.getDocumentElement();
            //get node list of items
            rssItems = root.getElementsByTagName("item");
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }
        return rssItems;
    }

    //display title, description, and link from nodes
    private void displayValues(int i, String title, String desc, String link) {
        System.out.println("------------------------------------------");
        System.out.println("Feed Item #" + (i + 1));
        System.out.println();
        System.out.println("Title: " + title);
        System.out.println();
        System.out.println("Description: " + desc);
        System.out.println();
        System.out.println("Link: " + link);
    }

    //open browser for user and direct them to desired link
    public void goToLink(int num) {
        if (num <= links.size()) {//<--check if user input is valid
            if (Desktop.isDesktopSupported()) {//<--check if desktop is supported
                try {//try to open browser with selected link
                    Desktop.getDesktop().browse(new URI(links.get(num - 1)));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            } else {//if not supported print sorry message
                System.out.println("System does not support this...Sorry :(");
            }
        } else {
            System.out.println("That feed link number does not an exist");
        }
    }
}
